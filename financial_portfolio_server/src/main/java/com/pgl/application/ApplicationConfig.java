package com.pgl.application;

import com.pgl.utils.DataBaseProperties;
import com.pgl.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableScheduling
@EnableAutoConfiguration
@EnableWebMvc
@EnableTransactionManagement
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.pgl"})
@EntityScan(basePackages = {"com.pgl.models"})
@EnableJpaRepositories(basePackages = {"com.pgl.repositories"})
@Profile(value= {"prod","dev"})
public class ApplicationConfig {

    @Autowired
    DataBaseProperties dataBaseProperties;

    @Autowired
    private Environment environmentProperties;


    @Bean(name = DataBaseProperties.PGL_DATA_SOURCE)
    @Primary
    public DataSource datasource() {
        String url = this.dataBaseProperties.getUrl().trim();
        String username = this.dataBaseProperties.getUsername().trim();
        String password = this.dataBaseProperties.getPassword().trim();
        return new DriverManagerDataSource(url, username, password);
    }


    @Primary
    @Bean(name = DataBaseProperties.ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.DERBY);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.pgl.models");
        factory.setPersistenceUnitName("pgl_unit");
        factory.setDataSource(datasource());
        factory.setJpaProperties(additionalProperties());

        return factory;
    }

    @Primary
    @Bean(name = DataBaseProperties.TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManager() throws IOException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        JettyServletWebServerFactory jetty = new JettyServletWebServerFactory();
        return jetty;
    }


    Properties additionalProperties() {
        Properties properties = new Properties();

        String[] propNames = {"hibernate.hql.bulk_id_strategy","properties.hibernate.dialect",
                "show-sql","hibernate.ddl-auto"};
        for(String propName : propNames ) {
            String propValue = environmentProperties.getProperty("spring.jpa."+propName);
            if(!StringUtil.isNullOrEmpty(propValue)) {
                properties.put(propName, propValue);
            }
        }

        return properties;
    }

    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "This is the test email template for your email:\n%s\n");
        message.setFrom("projetglgrp9@gmail.com");
        return message;
    }

}
