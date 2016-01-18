package tn.springmvc_mongodb.web.config.root;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import tn.springmvc_mongodb.bl.init.TestDataInitializer;

/**
 * Integration testing specific configuration - creates a in-memory datasource,
 * sets hibernate on create drop mode and inserts some test data on the
 * database.
 * <p/>
 * This allows to clone the project repository and start a running application
 * with the command
 * <p/>
 * mvn clean install tomcat7:run-war -Dspring.profiles.active=test
 * <p/>
 * Access http://localhost:8080/ and login with test123 / Password2, in order to
 * see some test data, or create a new user.
 */

@Profile("test")
@Configuration
@EnableMongoRepositories("tn.springmvc_mongodb.dal")
public class TestConfiguration {

    @Bean(initMethod = "init")
    public TestDataInitializer initTestData() {
        return new TestDataInitializer();
    }

}
