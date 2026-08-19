package com.nitesh.unique;

import com.nitesh.unique.dto.Quote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }
    @Bean
    public PlatformTransactionManager  add (MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    @Profile("!test")
    public ApplicationRunner run(){
        RestClient restClient =RestClient.create("http://localhost:8080");
        return args -> {
            try {
                Quote quote = restClient
                        .get().uri("/api/random")
                        .retrieve()
                        .body(Quote.class);
                log.info( quote.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        };
    }
}
