package com.springexample.webapp;

import com.springexample.adapters.JdbcUserRepository;
import com.springexample.domain.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(
    {TestConfiguration.class, DbConfiguration.class}
)
public class AppConfiguration {

    @Bean
    public RetrieveUserUseCase retrieveUserUseCase(UserRepository userRepository) {
        return new DefaultRetrieveUserUseCase(userRepository);
    }

    @Bean
    public SaveUserUseCase saveUserUseCase(TestConfiguration testConfiguration, UserRepository userRepository) {
        return new DefaultSaveUserUseCase(userRepository);
    }

    @Bean
    @Primary
    public DataSource appDataSource(
        DbConfiguration dbConfiguration
    ) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(dbConfiguration.url);
        dataSourceBuilder.username(dbConfiguration.username);
        dataSourceBuilder.password(dbConfiguration.password);
        return dataSourceBuilder.build();
    }

    @Bean
    public JdbcTemplate appJdbcTemplate(DataSource appDataSource) {
        return new JdbcTemplate(appDataSource);
    }

    @Bean
    public UserRepository userRepository(JdbcTemplate appJdbcTemplate) {
        return new JdbcUserRepository(appJdbcTemplate);
    }
}
