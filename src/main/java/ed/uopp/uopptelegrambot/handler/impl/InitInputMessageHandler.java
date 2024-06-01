package ed.uopp.uopptelegrambot.handler.impl;

import ed.uopp.uopptelegrambot.data.BotState;
import ed.uopp.uopptelegrambot.handler.AbstractInputMessageHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Set;

@Component
public class InitInputMessageHandler extends AbstractInputMessageHandler {

    @Override
    public Set<BotState> getHandlerBotStates() {
        return Set.of(BotState.INIT_STATE);
    }

    @Override
    public SendMessage handleMessage(Message message) {
        Long chatId = message.getChatId();

        SendMessage sendMessage = new SendMessage(chatId.toString(), "Привіт! За допомогою цього бота ти зможеш " +
                "створити підписку та отримувати сповіщення про освітні можливості. Використовуй команди з закріпленого меню.");

        return sendMessage;
    }

}
