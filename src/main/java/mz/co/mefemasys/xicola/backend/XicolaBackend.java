package mz.co.mefemasys.xicola.backend;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class XicolaBackend {

    private static final Logger LOG = Logger.getLogger(XicolaBackend.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(XicolaBackend.class, args);

    }

}
