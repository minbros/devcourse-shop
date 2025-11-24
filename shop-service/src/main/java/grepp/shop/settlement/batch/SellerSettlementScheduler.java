package grepp.shop.settlement.batch;

import grepp.shop.seller.domain.Seller;
import grepp.shop.seller.domain.SellerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SellerSettlementScheduler {
    private final SellerRepository sellerRepository;
    private final JobLauncher jobLauncher;
    private final Job sellerSettlementJob;
    private final ThreadPoolTaskExecutor settlementTaskExecutor;

    @Scheduled(cron = "${spring.task.scheduling.cron.settlement}")
    public void runMidnightSettlements() {
        Pageable pageable = Pageable.ofSize(100);
        Page<UUID> page;
        do {
            page = sellerRepository.findAll(pageable).map(Seller::getId);
            List<UUID> sellerIds = page.getContent();
            if (sellerIds.isEmpty()) {
                break;
            }

            log.info("[Batch] Settlement batch chunk for {} sellers (page {}/{})",
                    sellerIds.size(), page.getNumber() + 1, page.getTotalPages());
            sellerIds.forEach(this::runJobForSellers);
            pageable = page.nextPageable();
        } while (page.hasNext());
    }

    private void runJobForSellers(UUID sellerId) {
        try {
            Runnable task = generateSettlementTask(sellerId);
            settlementTaskExecutor.execute(task);
        } catch (Exception e) {
            log.error("[Batch] Failed to run settlement job for seller {}", sellerId, e);
        }
    }

    private Runnable generateSettlementTask(UUID sellerId) {
        return () -> {
            try {
                JobParameters parameters = new JobParametersBuilder()
                        .addLong("timestamp", System.currentTimeMillis())
                        .addString("sellerId", sellerId.toString())
                        .toJobParameters();
                jobLauncher.run(sellerSettlementJob, parameters);
                log.info("[Batch] Settlement job triggered for seller {}", sellerId);
            } catch (Exception e) {
                log.error("[Batch] Failed to run settlement job for seller {}", sellerId);
            }
        };
    }
}
