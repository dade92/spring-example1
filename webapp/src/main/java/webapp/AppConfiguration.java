package webapp;

import adapters.JdbcUserRepository;
import domain.*;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class AppConfiguration {

    @Bean
    public RetrieveUserUseCase retrieveUserUseCase(UserRepository userRepository) {
        return new DefaultRetrieveUserUseCase(userRepository);
    }

    @Bean
    public SaveUserUseCase saveUserUseCase(UserRepository userRepository) {
        return new DefaultSaveUserUseCase(userRepository);
    }
}
