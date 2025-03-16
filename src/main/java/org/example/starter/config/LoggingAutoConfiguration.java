package org.example.starter.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.example.starter.aspect.LoggingAspect;

@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingAutoConfiguration {

    @Bean
    @ConditionalOnProperty(value = "logging.enabled", matchIfMissing = true)
    public LoggingAspect LoggingAspect(LoggingProperties properties) {

        return new LoggingAspect(properties);
    }
}

