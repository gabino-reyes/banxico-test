package com.grc.banxico.efirma;

/**
 * Clase principal que inicializa valores y configuración de la aplicación restfull
 * @author Gabino Reyes
 * @version 1.0
 * @since   2022-06-24
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;

@SpringBootApplication
public class EfirmaApplication {

    private static final Logger log = LoggerFactory.getLogger(EfirmaApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(EfirmaApplication.class, args);
        log.info("Inicio de validaciones EFirma Banxico = " + new Date());
        System.out.println("Inicio de validaciones EFirma Banxico = " + new Date());
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

}
