package com.example.awesomitychallenge;

import com.example.awesomitychallenge.confugirations.KafkaTopicConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class AwesomityChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwesomityChallengeApplication.class, args);
    }
}
