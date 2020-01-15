package cn.benbenedu.sundial.mouse.service;

import cn.benbenedu.sundial.mouse.model.Mouse;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@CacheConfig(cacheNames = "mice")
public class MouseService {

    @Cacheable
    public Mouse getMouse(String name) throws Exception {

        TimeUnit.SECONDS.sleep(5);

        return Mouse.of(name);
    }
}
