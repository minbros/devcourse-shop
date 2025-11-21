package grepp.shop.settlement.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}/settlements")
public class SettlementJobController {
    private final JobLauncher jobLauncher;
    private final Job sellerSettlementJob;

    @PostMapping("/run/all")
    public ResponseEntity<String> runAll() throws JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {

        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(sellerSettlementJob, params);
        return ResponseEntity.ok("Settlement job (all sellers) started");
    }

    @PostMapping("/run/seller")
    public ResponseEntity<String> runSeller(@RequestParam("sellerId") String sellerId) throws JobExecutionAlreadyRunningException,
            JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {

        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .addString("sellerId", sellerId)
                .toJobParameters();
        jobLauncher.run(sellerSettlementJob, params);
        return ResponseEntity.ok("Settlement job started for seller " + sellerId);
    }
}
