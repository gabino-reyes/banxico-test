package com.grc.banxico.efirma;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class EfirmaApplication {

    private static final Logger log = LoggerFactory.getLogger(EfirmaApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(EfirmaApplication.class, args);
        log.info("Inicio de validaciones EFirma Banxico = " + new Date());
        System.out.println("Inicio de validaciones EFirma Banxico = " + new Date());
    }

}
