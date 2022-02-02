package hr.riteh.fanzonef1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FanZoneF1Application {

    public static void main(String[] args) {
        SpringApplication.run(FanZoneF1Application.class, args);
    }

}
