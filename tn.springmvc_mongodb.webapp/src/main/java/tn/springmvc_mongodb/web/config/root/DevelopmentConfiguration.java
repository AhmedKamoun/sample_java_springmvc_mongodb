package tn.springmvc_mongodb.web.config.root;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import tn.springmvc_mongodb.bl.init.TestDataInitializer;

import java.util.Arrays;

/**
 * Development specific configuration - creates a localhost mysql datasource,
 * sets hibernate on create drop mode and inserts some test data on the
 * database.
 * <p/>
 * Set -Dspring.profiles.active=development to activate this config.
 */

//@Profile("development")
@EnableMongoRepositories("tn.springmvc_mongodb.dal")
@Configuration
public class DevelopmentConfiguration {

    private String DATBASE_NAME = "database_test";

    @Bean(initMethod = "init")
    public TestDataInitializer initTestData() {
        return new TestDataInitializer();
    }

    @Bean(name = "mongoClient")
    public MongoClient mongoClient() {
        // SEE MongoClient class details if you want to use multiple
        // ServerAddress
        MongoClient mongoClient = new MongoClient(new ServerAddress("localhost:27017"),
                Arrays.asList(MongoCredential.createCredential("root", DATBASE_NAME, "root".toCharArray())));
        return mongoClient;
    }

    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), DATBASE_NAME);

    }


}