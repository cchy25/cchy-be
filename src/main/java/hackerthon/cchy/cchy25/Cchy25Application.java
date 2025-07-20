package hackerthon.cchy.cchy25;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Cchy25Application {

	public static void main(String[] args) {
		SpringApplication.run(Cchy25Application.class, args);
	}

}
