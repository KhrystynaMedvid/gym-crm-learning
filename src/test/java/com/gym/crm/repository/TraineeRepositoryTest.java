package com.gym.crm.repository;

import com.gym.crm.entity.Trainee;
import com.gym.crm.entity.User;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(TraineeRepositoryTest.JpaTestConfig.class)
class TraineeRepositoryTest {

    @Autowired
    private TraineeRepository traineeRepository;

    @Test
    @Transactional
    void savesAndFindsTraineeById() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .username("JohnDoe")
                .password("ystgxsy")
                .active(true)
                .build();

        Trainee trainee = Trainee.builder().
                user(user)
                .build();

        Long id = traineeRepository.save(trainee).getId();

        assertThat(traineeRepository.findById(id)).isPresent();
    }

    @Configuration
    @EnableTransactionManagement
    @EnableJpaRepositories(basePackages = "com.gym.crm.repository")
    static class JpaTestConfig{
        @Bean
        DataSource dataSource(){
            return new DriverManagerDataSource(
                    "jdbc:h2:mem:gym_crm;MODE=PostgreSQL;DB_CLOSE_DELAY=-1", "sa", "");
        }

        @Bean
        LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
            LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
            factory.setDataSource(dataSource);
            factory.setPackagesToScan("com.gym.crm.entity");
            factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

            Properties properties = new Properties();
            properties.put("hibernate.hbm2ddl.auto", "create-drop");
            properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            factory.setJpaProperties(properties);
            return factory;
        }

        @Bean
        PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }
    }
}
