package cn.benbenedu.sundial.mouse;

import lombok.Data;

@Data
public class Mouse {

    public static Mouse of(String name) {

        final var mouse = new Mouse();

        mouse.setName(name);

        return mouse;
    }

    private String name;
}
