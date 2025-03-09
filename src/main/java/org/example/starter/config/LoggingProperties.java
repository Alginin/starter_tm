package org.example.starter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;

@Getter
@Setter
@ConfigurationProperties(prefix = "logging.http")
public class LoggingProperties {

    private boolean enabled = true;

    private LogLevel level = LogLevel.INFO;
}
