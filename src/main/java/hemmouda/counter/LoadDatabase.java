package hemmouda.counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CountRepo repo) {

        return args -> {
            log.info("Preloading " + repo.save(new Count("Unfortunately", 3)));
            log.info("Preloading " + repo.save(new Count("Yo mama jokes", 5)));
        };
    }

}
