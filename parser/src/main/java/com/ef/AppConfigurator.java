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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Application spring configuration class
 * <p>
 * Created by sergio.leottau on 30/09/17.
 */

@ComponentScan(basePackages = "com.ef")
@EnableJpaRepositories(basePackages = "com.ef.repositories")
@PropertySource("classpath:parser.properties")
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
     * JPA vendor adapter
     *
     * @return the vendor adapter built
     */
    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        adapter.setShowSql(false);
        adapter.setDatabase(Database.MYSQL);

        return adapter;
    }

    /**
     * The entity manager defined for communication with pol_v4 database
     *
     * @param dataSource the data source
     * @return a LocalContainerEntityManagerFactoryBean
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lef
                = new LocalContainerEntityManagerFactoryBean();

        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan("com.ef");

        return lef;
    }


    /**
     * Transaction manager
     * @param emf entity manager factory
     * @param dataSource datasource
     * @return the JPA transaction manager built
     */
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf,
                                                    DataSource dataSource) {

        JpaTransactionManager txManager = new JpaTransactionManager(emf);
        txManager.setDataSource(dataSource);

        return txManager;
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
