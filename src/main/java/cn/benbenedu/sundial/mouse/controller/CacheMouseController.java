package cn.benbenedu.sundial.mouse.controller;

import cn.benbenedu.sundial.mouse.model.Mouse;
import cn.benbenedu.sundial.mouse.service.MouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genesis/cache")
@Slf4j
public class CacheMouseController {

    private MouseService mouseService;

    public CacheMouseController(MouseService mouseService) {

        this.mouseService = mouseService;
    }

    @GetMapping("/hello")
    public Mouse hello() throws Exception {

        return mouseService.getMouse("Adam");
    }
}
