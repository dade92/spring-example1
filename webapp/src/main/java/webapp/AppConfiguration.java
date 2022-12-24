package webapp;

import adapters.KafkaProducer;
import domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

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

    @Bean
    public KafkaProducer kafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        return new KafkaProducer(kafkaTemplate);
    }
}
