package tn.springmvc_mongodb.web.config.root;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * The root context configuration of the application - the beans in this context
 * will be globally visible in all servlet contexts.
 */

//@EnableTransactionManagement
@Configuration
@PropertySource("classpath:/system_config.properties")
@ComponentScan(basePackages = {"tn.springmvc_mongodb"})
public class RootContextConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }


}
