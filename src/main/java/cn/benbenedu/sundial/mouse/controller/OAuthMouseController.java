package cn.benbenedu.sundial.mouse.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/genesis/oauth")
@Slf4j
public class OAuthMouseController {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    @GetMapping("/hello")
    public String hello(OAuth2Authentication auth) {

        log.info("Assess Token: {}", oAuth2RestTemplate.getAccessToken());

        final var userPrincipal = (Map) auth.getPrincipal();
        log.info("User ID: {}", userPrincipal.get("id"));
        log.info("User Name: {}", userPrincipal.get("name"));
        log.info("User Nickname: {}", userPrincipal.get("nickname"));

        return "Hello, OAuth...";
    }
}
