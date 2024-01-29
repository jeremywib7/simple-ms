package com.thefuture.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class KafkaProperties {

    private Topics topics = new Topics();

    @Getter
    @Setter
    public static class Topics {
        private String transactionListener;
    }
}

