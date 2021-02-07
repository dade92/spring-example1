package com.myprojects;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(
        TestConfiguration.class
)
public class AppConfiguration {

    @Bean
    public MyUseCase myUseCase(TestConfiguration testConfiguration) {
        return new MyUseCaseImpl(testConfiguration);
    }

}
