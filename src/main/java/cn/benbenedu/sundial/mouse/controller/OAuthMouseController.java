package cn.benbenedu.sundial.mouse.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genesis/oauth")
@Slf4j
public class OAuthMouseController {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    @GetMapping("/hello")
    public String hello() {

        log.info("Assess Token: {}", oAuth2RestTemplate.getAccessToken());

        return "Hello, OAuth...";
    }
}
