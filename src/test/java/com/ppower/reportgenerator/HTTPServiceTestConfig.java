package com.ppower.reportgenerator;

import com.ppower.reportgenerator.service.HTTPService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("test")
public class HTTPServiceTestConfig {
    @Bean
    @Primary
    public HTTPService createHttpService() {
        return Mockito.mock(HTTPService.class);
    }
}
