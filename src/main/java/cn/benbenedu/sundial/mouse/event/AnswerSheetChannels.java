package cn.benbenedu.sundial.mouse.event;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface AnswerSheetChannels {

    @Output("outboundAnswerSheetFinished")
    MessageChannel answerSheetFinished();
}
