package hackerthon.cchy.cchy25.global.config;

import hackerthon.cchy.cchy25.domain.policy.entity.Policy;
import hackerthon.cchy.cchy25.domain.policy.entity.SourcePolicy;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class PolicyBatchConfiguration {

    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job transferSourceToPolicyJob(JobRepository jobRepository, Step transferStep) {
        return new JobBuilder("transferSourceToPolicyJob", jobRepository)
                .start(transferStep)
                .build();
    }

    @Bean
    public Step transferStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("transferStep", jobRepository)
                .<SourcePolicy, Policy>chunk(100, transactionManager)
                .reader(sourcePolicyReader())
                .processor(sourcePolicyProcessor())
                .writer(policyWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<SourcePolicy> sourcePolicyReader() {
        return new JpaPagingItemReaderBuilder<SourcePolicy>()
                .name("sourcePolicyReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(100)
                .queryString("SELECT s FROM SourcePolicy s ORDER BY s.id")
                .build();
    }

    @Bean
    public ItemProcessor<SourcePolicy, Policy> sourcePolicyProcessor() {
        return sourcePolicy -> Policy.builder()
                .title(sourcePolicy.getTitle() + "!")
                .build();
    }

    @Bean
    public JpaItemWriter<Policy> policyWriter() {
        JpaItemWriter<Policy> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
