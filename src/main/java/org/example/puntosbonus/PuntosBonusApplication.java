package org.example.puntosbonus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PuntosBonusApplication {

    public static void main(String[] args) {
        SpringApplication.run(PuntosBonusApplication.class, args);
    }

}
