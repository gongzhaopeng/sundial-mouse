package cn.benbenedu.sundial.mouse.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(
            UserInfoRestTemplateFactory factory) {

        return factory.getUserInfoRestTemplate();
    }
}
