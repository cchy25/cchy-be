package hackerthon.cchy.cchy25.global.config;

import hackerthon.cchy.cchy25.domain.policy.entity.*;
import hackerthon.cchy.cchy25.domain.policy.repository.PolicyRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PolicyBatchConfiguration {

    private final EntityManagerFactory entityManagerFactory;
    private final PolicyRepository policyRepository;

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
        return sourcePolicy -> {
            if (policyRepository.existsBySourceId(sourcePolicy.getId())) {
                log.info("Policy with sourceId={} already exists. Skipping.", sourcePolicy.getId());
                return null;
            }
            try {
                var regions = Arrays.stream(sourcePolicy.getRegions().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(RegionCode::fromString)
                        .collect(Collectors.toSet());

                var supportFields = Arrays.stream(sourcePolicy.getSupportFields().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(SupportField::fromString)
                        .collect(Collectors.toSet());

                var applyMethods = Arrays.stream(sourcePolicy.getApplyMethods().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(ApplyMethod::fromString)
                        .collect(Collectors.toSet());

                var evaluationMethods = Arrays.stream(sourcePolicy.getEvaluationMethods().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(EvaluationMethod::fromString)
                        .collect(Collectors.toSet());

                var supportTargets = Arrays.stream(sourcePolicy.getTargets().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(SupportTarget::fromString)
                        .collect(Collectors.toSet());

                var supportCategories = Arrays.stream(sourcePolicy.getSupportCategory().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(SupportCategory::fromString)
                        .collect(Collectors.toSet());

                var supportTypes = Arrays.stream(sourcePolicy.getSupportTypes().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(SupportType::fromString)
                        .collect(Collectors.toSet());

                return Policy.builder()
                        .sourceId(sourcePolicy.getId())
                        .title(sourcePolicy.getTitle())
                        .summary(sourcePolicy.getSummary())
                        .url(sourcePolicy.getUrl())
                        .organization(sourcePolicy.getOrganization())
                        .targetDetail(sourcePolicy.getTargetDetail())
                        .exTargetDetail(sourcePolicy.getExTargetDetail())
                        .conditionDetail(sourcePolicy.getConditionDetail())
                        .startAt(sourcePolicy.getStartAt())
                        .endAt(sourcePolicy.getEndAt())
                        .applyStartAt(sourcePolicy.getApplyStartAt())
                        .applyEndAt(sourcePolicy.getApplyEndAt())
                        .years(sourcePolicy.getYears())
                        .minAmount(sourcePolicy.getMinAmount())
                        .maxAmount(sourcePolicy.getMaxAmount())
                        .supportCategories(supportCategories)
                        .applyMethods(applyMethods)
                        .evaluationMethods(evaluationMethods)
                        .supportTypes(supportTypes)
                        .targets(supportTargets)
                        .regions(regions)
                        .supportFields(supportFields)
                        .build();
            } catch (Exception e) {
                log.error("Failed to process id={} due to {}", sourcePolicy.getId(), e.getMessage());
                return null; // null 반환하면 이 아이템은 skip
            }
        };
    }



    @Bean
    public JpaItemWriter<Policy> policyWriter() {
        JpaItemWriter<Policy> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
