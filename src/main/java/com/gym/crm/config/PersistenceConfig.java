package com.gym.crm.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.gym.crm.repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
public class PersistenceConfig {
    private final Environment enviroment;

    public PersistenceConfig(Environment enviroment) {
        this.enviroment = enviroment;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(enviroment.getRequiredProperty("db.driver"));
        dataSource.setUrl(enviroment.getRequiredProperty("db.url"));
        dataSource.setUsername(enviroment.getRequiredProperty("db.username"));
        dataSource.setPassword(enviroment.getRequiredProperty("db.password"));

        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.dialect",
                enviroment.getRequiredProperty("hibernate.dialect"));

        properties.setProperty("hibernate.hbm2ddl.auto",
                enviroment.getRequiredProperty("hibernate.hbm2ddl.auto"));

        properties.setProperty("hibernate.show_sql",
                enviroment.getRequiredProperty("hibernate.show_sql"));

        properties.setProperty("hibernate.format_sql",
                enviroment.getRequiredProperty("hibernate.format_sql"));

        return properties;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        factory.setDataSource(dataSource());
        factory.setPackagesToScan(enviroment.getRequiredProperty("hibernate.packagesToScan"));
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(hibernateProperties());

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
