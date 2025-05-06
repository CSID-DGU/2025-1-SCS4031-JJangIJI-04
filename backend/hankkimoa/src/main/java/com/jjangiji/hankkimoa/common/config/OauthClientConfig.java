package com.jjangiji.hankkimoa.common.config;

import com.jjangiji.hankkimoa.common.handler.OauthClientExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class OauthClientConfig {

    @Bean
    RestClient restClient(OauthClientExceptionHandler handler) {
        return RestClient.builder()
                .defaultStatusHandler(handler)
                .build();
    }
}
