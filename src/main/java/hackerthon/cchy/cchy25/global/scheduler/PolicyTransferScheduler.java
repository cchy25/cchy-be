package hackerthon.cchy.cchy25.global.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyTransferScheduler {

    private final JobLauncher jobLauncher;
    private final Job transferSourceToPolicyJob;

    @Scheduled(cron = "0 0 0 * * ?")
    public void runJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()) // 고유한 파라미터
                .toJobParameters();

        try {
            jobLauncher.run(transferSourceToPolicyJob, jobParameters);
            System.out.println("Spring Batch Job 'transferSourceToPolicyJob'이(가) 성공적으로 완료되었습니다. (스케줄링 실행)");
        } catch (Exception e) {
            System.err.println("Spring Batch Job 'transferSourceToPolicyJob'이(가) 실패했습니다: " + e.getMessage());
            e.printStackTrace();
        }    }

}
