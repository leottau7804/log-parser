package com.ef;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Application spring configuration class
 *
 * Created by sergio.leottau on 30/09/17.
 */

@ComponentScan(basePackages = "com.ef")
@EnableJpaRepositories(basePackages = "com.ef.repositories")
@PropertySource("classpath:database.properties")
@Configuration
public class AppConfigurator {

    /**
     * Logger configuration.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(AppConfigurator.class);


    /**
     * Property place holder to allow @Value placeholder {} use
     *
     * @return the property place holder built
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    /**
     * Datasource configuration
     *
     * @param driver   driver class
     * @param url      url with connection information.
     * @param username database username
     * @param password database password
     * @return the datasource built
     */
    @Bean
    public DataSource dataSource(@Value("${database.driver}") String driver,
                                 @Value("${database.url}") String url,
                                 @Value("${database.username}") String username,
                                 @Value("${database.password}") String password) {


        LOGGER.debug("You are trying to connect to [{}] with user [{}]", url, username);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }
}
