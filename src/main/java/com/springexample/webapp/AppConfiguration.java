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
    TestConfiguration.class
)
public class AppConfiguration {

    @Bean
    public RetrieveUserUseCase retrieveUserUseCase(UserRepository userRepository) {
        return new DefaultRetrieveUserUseCase(userRepository);
    }

    @Bean
    public SaveUserUseCase myUseCase(TestConfiguration testConfiguration, UserRepository userRepository) {
        return new DefaultSaveUserUseCase(userRepository);
    }

    @Bean
    @Primary
    public DataSource appDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/spring-example");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("1191992Da");
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
