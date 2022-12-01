package webapp;

import domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
