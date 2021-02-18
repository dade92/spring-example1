package com.springexample.webapp;

import com.springexample.domain.MyUseCase;
import com.springexample.domain.MyUseCaseImpl;
import com.springexample.domain.PathUseCase;
import com.springexample.domain.PathUseCaseImpl;
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
