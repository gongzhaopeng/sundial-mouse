package cn.benbenedu.sundial.mouse.controller;

import cn.benbenedu.sundial.mouse.service.AsyncInvoker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

    private OAuth2RestTemplate oAuth2RestTemplate;
    private AsyncInvoker asyncInvoker;

    @Autowired
    public OAuthMouseController(
            OAuth2RestTemplate oAuth2RestTemplate,
            AsyncInvoker asyncInvoker) {

        this.oAuth2RestTemplate = oAuth2RestTemplate;
        this.asyncInvoker = asyncInvoker;
    }

    @GetMapping("/hello")
//    @Secured("ROLE_Spitter")
    @Secured("ROLE_God")
    public String hello(OAuth2Authentication auth) throws Exception {

        log.info("Client ID: {}", auth.getOAuth2Request().getClientId());
        log.info("Client Owner: {}", auth.getOAuth2Request().getExtensions().get("owner"));

        log.info("User ID: {}", auth.getUserAuthentication().getPrincipal());
        final var userDetails = (Map<String, ?>) auth.getUserAuthentication().getDetails();
        log.info("User Type: {}", userDetails.get("type"));
        log.info("User Name: {}", userDetails.get("name"));
        log.info("User Nickname: {}", userDetails.get("nickname"));

        log.info("Assess Token: {}", oAuth2RestTemplate.getAccessToken());

        final var waitressResp = oAuth2RestTemplate.getForObject(
                "http://mouse/genesis/oauth/waitress", String.class);
        log.info("/mouse/genesis/oauth/waitress Resp: {}", waitressResp);

        asyncInvoker.invokeApiOauthWaitressAsync();

        return "Hello, OAuth...";
    }

    @GetMapping("/waitress")
    public String waitress() {

        return "I am the pretty waitress!";
    }

    @GetMapping("/waitress/async")
    public String asyncWaitress() {

        return "I am the async waitress!";
    }
}
