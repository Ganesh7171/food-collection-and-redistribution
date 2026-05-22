
package config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {
	    org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration.class
	})

@EnableJpaRepositories(basePackages = "repositories")
@EntityScan(basePackages = "entities")

@ComponentScan(basePackages = {"controller", "repositories","entities","services"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	

}
