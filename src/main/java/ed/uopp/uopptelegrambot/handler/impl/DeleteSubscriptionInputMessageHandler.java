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
public class DeleteSubscriptionInputMessageHandler extends AbstractInputMessageHandler {

    private final UoppCoreIntegrationService uoppCoreIntegrationService;
    private final UserDataService userDataService;

    @Override
    public Set<BotState> getHandlerBotStates() {
        return Set.of(BotState.DELETE_SUBSCRIPTION_STATE);
    }

    @Override
    public SendMessage handleMessage(Message message) {
        SendMessage sendMessage = null;
        Long userId = message.getFrom().getId();

        UUID internalSubscriptionUuid = userDataService.getUserInternalSubscriptionId(userId);
        if (internalSubscriptionUuid == null) {
            return new SendMessage(message.getChatId().toString(),
                    "Видалення недоступне! У вас немає підписки! Створити підписку можна запустивши команду: /create_subscription");
        }

        var deletionResult = uoppCoreIntegrationService.deleteSubscription(internalSubscriptionUuid);
        if (deletionResult) {
            sendMessage = new SendMessage(message.getChatId().toString(),
                    "Підписка на сповіщення була видалена! Пропонуємо створити нову підписку запустивши команду: /create_subscription");
            userDataService.removeUserInternalSubscriptionId(userId);
        } else {
            sendMessage = new SendMessage(message.getChatId().toString(),
                    "Видалення не спрацювало! Спробуйте ще раз: /delete_subscription");
        }
        return sendMessage;
    }
}
