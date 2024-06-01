package ed.uopp.uopptelegrambot.handler.impl;

import ed.uopp.uopptelegrambot.data.BotState;
import ed.uopp.uopptelegrambot.handler.AbstractInputMessageHandler;
import ed.uopp.uopptelegrambot.service.UoppCoreIntegrationService;
import ed.uopp.uopptelegrambot.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ViewSubscriptionInputMessageHandler extends AbstractInputMessageHandler {

    private final UoppCoreIntegrationService uoppCoreIntegrationService;
    private final UserDataService userDataService;

    @Override
    public Set<BotState> getHandlerBotStates() {
        return Set.of(BotState.VIEW_SUBSCRIPTION_STATE);
    }

    @Override
    public SendMessage handleMessage(Message message) {
        SendMessage sendMessage = null;

        UUID internalSubscriptionUuid = userDataService.getUserInternalSubscriptionId(message.getChatId());
        if (internalSubscriptionUuid == null) {
            return new SendMessage(message.getChatId().toString(),
                    "У вас немає підписки! Створити підписку можна запустивши команду: /create_subscription");
        }

        var subscriptionDataResult = uoppCoreIntegrationService.getSubscriptionData(internalSubscriptionUuid);
        sendMessage = subscriptionDataResult
                .map(subscriptionData -> new SendMessage(message.getChatId().toString(), String.format("Підписка на сповіщення: %s", subscriptionData)))
                .orElseGet(() -> new SendMessage(message.getChatId().toString(),
                        "Виникла помилка! Спробуйте ще разґ: /view_subscription"));

        return sendMessage;
    }
}
