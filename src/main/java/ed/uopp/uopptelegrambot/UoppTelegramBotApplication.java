package ed.uopp.uopptelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class UoppTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(UoppTelegramBotApplication.class, args);
    }

}
