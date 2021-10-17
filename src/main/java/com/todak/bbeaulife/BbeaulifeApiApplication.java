package com.todak.bbeaulife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BbeaulifeApiApplication {

    public static void main(String[] args) {
//        SpringApplication springApplication = new SpringApplication(BbeaulifeApiApplication.class);
//        springApplication.addInitializers(new DotenvConfig());
//        springApplication.run(args);
////                .addInitializers(new DotenvConfig());

        SpringApplication.run(BbeaulifeApiApplication.class, args);

    }

}
