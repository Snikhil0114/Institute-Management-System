package com.institute.config;


import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration

@ConditionalOnMissingBean(DataSource.class)
public class MultiTenantDataSourceConfig {
@Value("${spring.datasource.username}")
private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.configurl}")
    private String configurl;
    @Value("${spring.datasource.driver-class-name}")
    private String driverclassname;
    private final Map<String, DataSource> tenantDataSources = new HashMap<>();

    public DataSource resolveDataSource(String dbName) {
        return tenantDataSources.computeIfAbsent(dbName, this::createDataSource);
    }
 //DataSource Configuration
    private DataSource createDataSource(String dbName) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(configurl+dbName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverclassname);
        return dataSource;
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        emf.setPackagesToScan("com.institute.model");
//        return emf;
//    }
//
//    @Bean
//    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
//        return properties -> properties.put("hibernate.multiTenancy", "SCHEMA");
//    }
}

