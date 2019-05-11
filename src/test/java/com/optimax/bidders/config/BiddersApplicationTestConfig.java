package com.optimax.bidders.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Viktar Lebedzeu
 */
@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "application-test", ignoreUnknownFields = true)
public class BiddersApplicationTestConfig {
    /** application name */
    private String name;
}
