package grepp.shop.settlement.batch;

import grepp.shop.settlement.domain.SellerSettlement;
import grepp.shop.settlement.domain.SellerSettlementRepository;
import grepp.shop.settlement.domain.SellerSettlementStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableBatchProcessing
public class SellerSettlementConfig {
    @Bean
    public Job sellerSettlementJob(JobRepository jobRepository, Step settlementStep) {
        return new JobBuilder("sellerSettlementJob", jobRepository)
                .start(settlementStep)
                .build();
    }

    @Bean
    public Step settlementStep(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               SellerSettlementRepository sellerSettlementRepository) {
        return new StepBuilder("settlementStep", jobRepository)
                .tasklet(settleSellers(sellerSettlementRepository), transactionManager)
                .build();
    }

    private static Tasklet settleSellers(SellerSettlementRepository sellerSettlementRepository) {
        return (contribution, chunkContext) -> {
            String sellerParam = (String) chunkContext.getStepContext()
                    .getJobParameters()
                    .get("sellerId");

            List<SellerSettlement> pendingSettlements = getSellerSettlements(sellerSettlementRepository, sellerParam);
            if (pendingSettlements.isEmpty()) {
                log.info("[Batch] No pending settlements to process");
                return RepeatStatus.FINISHED;
            }

            Map<UUID, BigDecimal> totals = calculateTotals(pendingSettlements);
            markAsCompletedAndSave(sellerSettlementRepository, pendingSettlements, totals);
            return RepeatStatus.FINISHED;
        };
    }

    private static List<SellerSettlement> getSellerSettlements(SellerSettlementRepository sellerSettlementRepository, String sellerParam) {
        List<SellerSettlement> pending;
        if (sellerParam != null && !sellerParam.isBlank()) {
            pending = sellerSettlementRepository.findByStatusAndSellerId(
                    SellerSettlementStatus.PENDING,
                    UUID.fromString(sellerParam)
            );
        } else {
            pending = sellerSettlementRepository.findByStatus(SellerSettlementStatus.PENDING);
        }
        return pending;
    }

    private static Map<UUID, BigDecimal> calculateTotals(List<SellerSettlement> pendingSettlements) {
        return pendingSettlements.stream()
                .collect(Collectors.groupingBy(
                        SellerSettlement::getSellerId,
                        Collectors.reducing(BigDecimal.ZERO, SellerSettlement::getAmount, BigDecimal::add)
                ));
    }

    private static void markAsCompletedAndSave(SellerSettlementRepository sellerSettlementRepository, List<SellerSettlement> pendingSettlements, Map<UUID, BigDecimal> totals) {
        pendingSettlements.forEach(SellerSettlement::markCompleted);
        sellerSettlementRepository.saveAll(pendingSettlements);

        totals.forEach((sellerId, total) ->
                log.info("[Batch] Settled seller {} amount {}", sellerId, total));
    }
}
