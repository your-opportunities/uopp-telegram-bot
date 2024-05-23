package ed.uopp.uopptelegrambot.processor.impl;

import ed.uopp.uopptelegrambot.processor.NotificationMessageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultNotificationMessageProcessor implements NotificationMessageProcessor {
    @Override
    public void processNotification(Object object) {
        log.info("Processing something {}", object);


    }
}
