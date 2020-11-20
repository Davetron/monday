package co.databeast.monday;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MondayApplication implements CommandLineRunner {

    @Autowired
    private MondayQueryService service;

    public static void main(String[] args) {
        SpringApplication.run(MondayApplication.class, args).close();
    }

    @Override
    public void run(String... args) {
        service.call();
    }

}
