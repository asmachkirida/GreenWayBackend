package ma.internship.greenway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ma.internship.greenway.repository")
@EntityScan(basePackages = "ma.internship.greenway.entity")
public class GreenwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenwayApplication.class, args);
    }

}
