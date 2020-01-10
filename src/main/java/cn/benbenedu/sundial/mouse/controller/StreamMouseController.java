package cn.benbenedu.sundial.mouse.controller;

import cn.benbenedu.sundial.mouse.event.model.AnswerSheetFinishedEvent;
import cn.benbenedu.sundial.mouse.event.publisher.AnswerSheetEventsPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genesis/stream")
@Slf4j
public class StreamMouseController {

    private AnswerSheetEventsPublisher answerSheetEventsPublisher;

    public StreamMouseController(
            AnswerSheetEventsPublisher answerSheetEventsPublisher) {

        this.answerSheetEventsPublisher = answerSheetEventsPublisher;
    }

    @GetMapping("/hello")
    public String hello() {

        final var answerSheetFinishedEvent = new AnswerSheetFinishedEvent();
        answerSheetFinishedEvent.setAticket("5daec77559e93a770c30079f");

        answerSheetEventsPublisher.answerSheetFinished(answerSheetFinishedEvent);

        return "Hello, Stream...";
    }
}
