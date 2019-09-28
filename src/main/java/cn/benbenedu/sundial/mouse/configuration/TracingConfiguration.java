package cn.benbenedu.sundial.mouse.configuration;

import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingConfiguration {

    @Bean
    public Sampler defaultSampler() {

        return Sampler.create(0.3f);
    }
}
