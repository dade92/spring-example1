package webapp;

import domain.*;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableConfigurationProperties(
    {DbConfiguration.class}
)
public class AppConfiguration {

    @Bean
    public RetrieveUserUseCase retrieveUserUseCase(UserRepository userRepository) {
        return new DefaultRetrieveUserUseCase(userRepository);
    }

    @Bean
    public SaveUserUseCase saveUserUseCase(UserRepository userRepository) {
        return new DefaultSaveUserUseCase(userRepository);
    }

    @Bean
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
    //TODO move this in adapters configuration
//    @Bean
//    public UserRepository userRepository(JdbcTemplate appJdbcTemplate) {
//        return new JdbcUserRepository(appJdbcTemplate);
//    }
}
