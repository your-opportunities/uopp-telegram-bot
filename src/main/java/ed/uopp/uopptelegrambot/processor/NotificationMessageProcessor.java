package ed.uopp.uopptelegrambot.processor;

import ed.uopp.uopptelegrambot.data.mq.NotificationDTO;

public interface NotificationMessageProcessor {

    void processNotification(NotificationDTO notificationDTO);

}
