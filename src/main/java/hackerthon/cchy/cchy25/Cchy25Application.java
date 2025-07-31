package hackerthon.cchy.cchy25;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableBatchProcessing
@EnableScheduling
public class Cchy25Application {

	public static void main(String[] args) {
		SpringApplication.run(Cchy25Application.class, args);
	}

}
