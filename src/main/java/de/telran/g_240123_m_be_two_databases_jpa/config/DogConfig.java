package de.telran.g_240123_m_be_two_databases_jpa.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableJpaRepositories(
        entityManagerFactoryRef = DogConfig.ENTITY_MANAGER_FACTORY,
        transactionManagerRef = DogConfig.TRANSACTION_MANAGER,
        basePackages = DogConfig.JPA_REPOSITORY_PACKAGE
)
@Configuration
public class DogConfig {

    public static final String PROPERTY_PREFIX = "app.dogdb.datasource";
    public static final String JPA_REPOSITORY_PACKAGE = "de.telran.g_240123_m_be_two_databases_jpa.repository.dog";
    public static final String ENTITY_PACKAGE = "de.telran.g_240123_m_be_two_databases_jpa.domain.entity.dog";
    public static final String ENTITY_MANAGER_FACTORY = "dogEntityManagerFactory";
    public static final String DATA_SOURCE = "dogDataSource";
    public static final String DATABASE_PROPERTY = "dogDatabaseProperty";
    public static final String TRANSACTION_MANAGER = "dogTransactionManager";

    @Bean(DATABASE_PROPERTY)
    @ConfigurationProperties(prefix = PROPERTY_PREFIX)
    public DatabaseProperty appDatabaseProperty() {
        return new DatabaseProperty();
    }

    @Bean(DATA_SOURCE)
    public DataSource appDataSource(
            @Qualifier(DATABASE_PROPERTY) DatabaseProperty databaseProperty
    ) {
        return DataSourceBuilder
                .create()
                .username(databaseProperty.getUsername())
                .password(databaseProperty.getPassword())
                .url(databaseProperty.getUrl())
                .driverClassName(databaseProperty.getClassDriver())
                .build();
    }

    @Bean(ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean appEntityManager(
            @Qualifier(DATA_SOURCE) DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceUnitName(ENTITY_MANAGER_FACTORY);
        em.setPackagesToScan(ENTITY_PACKAGE);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.validation.mode", "none");
        properties.put("hibernate.hbm2ddl.auto", "none");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean(TRANSACTION_MANAGER)
    public PlatformTransactionManager appTransactionManager(
            @Qualifier(ENTITY_MANAGER_FACTORY) LocalContainerEntityManagerFactoryBean em,
            @Qualifier(DATA_SOURCE) DataSource dataSource
    ) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(em.getObject());
        tm.setDataSource(dataSource);
        return tm;
    }
}