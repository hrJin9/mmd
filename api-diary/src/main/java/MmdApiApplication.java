import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.mmd")
//@EnableJpaAuditing
//@EnableJpaRepositories(basePackages = "com.mmd.repository")
public class MmdApiApplication {
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application-api-diary,application-auth,application-auth-oauth2,application-aws,application-core-mysql,application-core-redis");
		SpringApplication.run(MmdApiApplication.class, args);
	}
}
