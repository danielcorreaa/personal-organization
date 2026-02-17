package com.personal_organization;

import com.personal_organization.config.MongoConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;



@ComponentScan(basePackages = "com.personal_organization")
//@EnableMongoRepositories
public class MongoTestConfig {

   /* @Bean
    MongoConfig mongoConfig(){
        MongoConfig mongoConfig = new MongoConfig();
        return mongoConfig;
    }*/

}
