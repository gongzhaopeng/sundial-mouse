package cn.benbenedu.sundial.mouse.service;

import cn.benbenedu.sundial.mouse.configuration.RestTemplateConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AsyncInvoker {

    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    public AsyncInvoker(
            @RestTemplateConfiguration.IndependentOauth
                    OAuth2RestTemplate oAuth2RestTemplate) {

        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @Async
    public void invokeApiOauthWaitressAsync() throws InterruptedException {

        TimeUnit.SECONDS.sleep(5);

        final var waitressAsyncResp = oAuth2RestTemplate.getForObject(
                "http://mouse/genesis/oauth/waitress/async", String.class);
        log.info("/mouse/genesis/oauth/waitress/async Resp: {}", waitressAsyncResp);
    }
}
