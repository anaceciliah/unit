package unit.br.unitnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class UnitApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnitApplication.class, args);
    }

}
