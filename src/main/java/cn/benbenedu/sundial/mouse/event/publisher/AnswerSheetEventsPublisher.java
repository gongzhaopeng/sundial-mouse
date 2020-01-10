package cn.benbenedu.sundial.mouse.event.publisher;

import cn.benbenedu.sundial.mouse.event.AnswerSheetChannels;
import cn.benbenedu.sundial.mouse.event.model.AnswerSheetFinishedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableBinding(AnswerSheetChannels.class)
public class AnswerSheetEventsPublisher {

    private MessageChannel answerSheetFinishedChannel;


    public AnswerSheetEventsPublisher(
            @Qualifier("outboundAnswerSheetFinished") MessageChannel answerSheetFinishedChannel) {

        this.answerSheetFinishedChannel = answerSheetFinishedChannel;
    }

    public void answerSheetFinished(
            AnswerSheetFinishedEvent answerSheetFinishedEvent) {

        log.info("Sending AnswerSheet-Finished event: {}",
                answerSheetFinishedEvent);

        answerSheetFinishedChannel.send(
                MessageBuilder.withPayload(answerSheetFinishedEvent).build());
    }
}
