package cn.benbenedu.sundial.mouse.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genesis/oauth")
public class OAuthMouseController {

    @GetMapping("/hello")
    public String hello() {

        return "Hello, OAuth...";
    }
}
