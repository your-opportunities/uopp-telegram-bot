package ed.uopp.uopptelegrambot.processor.impl;

import ed.uopp.uopptelegrambot.bot.UoppTelegramBot;
import ed.uopp.uopptelegrambot.data.mq.NotificationDTO;
import ed.uopp.uopptelegrambot.processor.NotificationMessageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class DefaultNotificationMessageProcessor implements NotificationMessageProcessor {

    private final UoppTelegramBot uoppTelegramBot;

    @Override
    public void processNotification(NotificationDTO notificationDTO) {
        log.info("Processing notification for user '{}'", notificationDTO.userId());

        InlineKeyboardButton linkBtn = InlineKeyboardButton.builder()
                .text("Переглянути через вебсайт + "+ notificationDTO.opportunityLink())
                .callbackData("dummy")
//                .url(notificationDTO.opportunityLink()) // localhost does not work with telegram,
                .build();
        InlineKeyboardButton sourceLinkBtn = InlineKeyboardButton.builder()
                .text("Переглянути в першоджерелі")
                .url(notificationDTO.opportunitySourceLink())
                .build();
        InlineKeyboardRow topRow = new InlineKeyboardRow(linkBtn);
        InlineKeyboardRow bottomRow = new InlineKeyboardRow(sourceLinkBtn);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(List.of(topRow, bottomRow));

        SendMessage message = new SendMessage(notificationDTO.userId(), notificationDTO.opportunityText());
        message.setReplyMarkup(inlineKeyboardMarkup);
        message.enableMarkdown(true);

        uoppTelegramBot.sendMessage(message);
    }

}
