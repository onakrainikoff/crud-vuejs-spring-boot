package ru.on8off.crud.backend.configuration;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.info.MigrationInfoDumper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.on8off.crud.backend.repository")
@Slf4j
public class DataBaseConfiguration {
    @Value("${database.hibernate.dialect}")
    private String hibernateDialect;
    @Value("${database.flyway.migration}")
    private String flywayMigration;

    @Bean
    @ConfigurationProperties(prefix="database")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource(dataSource())
                .locations("migration/database")
                .cleanDisabled(!"clean-migrate".equals(flywayMigration))
                .load();
    }

    @Bean
    public FlywayMigrationInitializer flywayMigrationInitializer() {
        return new FlywayMigrationInitializer(flyway(), (flyway) -> {
            if("clean-migrate".equals(flywayMigration)) {
                flyway.clean();
                flyway.migrate();
            } else if("migrate".equals(flywayMigration)) {
                flyway.migrate();
            }
            log.info("Flyway info:\n {}", MigrationInfoDumper.dumpToAsciiTable(flyway.info().all()));
        });
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("ru.on8off.crud.backend.repository.entity");
        emf.setPersistenceUnitName("database");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.hbm2ddl.auto", "none");
        emf.setJpaPropertyMap(properties);
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager(entityManagerFactory().getObject());
        manager.setNestedTransactionAllowed(true);
        return manager;
    }
}
