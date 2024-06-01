package ed.uopp.uopptelegrambot.handler;

import ed.uopp.uopptelegrambot.data.BotState;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Set;

@RequiredArgsConstructor
public abstract class AbstractInputMessageHandler {

    public boolean isApplicable(BotState botState) {
        return getHandlerBotStates().contains(botState);
    }

    public abstract Set<BotState> getHandlerBotStates();

    public abstract SendMessage handleMessage(Message message);

}
