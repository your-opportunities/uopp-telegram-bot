package ed.uopp.uopptelegrambot.consumer;

import ed.uopp.uopptelegrambot.processor.NotificationMessageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NotificationMessageConsumer {

//    private final NotificationMessageProcessor notificationMessageProcessor;
//
//    @Retryable(retryFor = Exception.class, maxAttempts = 2, backoff = @Backoff(delay = 1000))
//    @RabbitListener(queues = "${application.rabbitmq.queue}")
//    public void consume(Message<?> message) {
//        var payload = message.getPayload();
//        log.info("Received payload {}", payload);
//
//        notificationMessageProcessor.processNotification(payload);
//    }
//
//    @Recover
//    public void recover(Exception ex) {
//        log.error("Failed to process");
//    }

}
