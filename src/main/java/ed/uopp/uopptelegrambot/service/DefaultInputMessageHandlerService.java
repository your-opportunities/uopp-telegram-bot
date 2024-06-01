package ed.uopp.uopptelegrambot.service;

import ed.uopp.uopptelegrambot.data.BotState;
import ed.uopp.uopptelegrambot.handler.AbstractInputMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultInputMessageHandlerService implements InputMessageHandlerService {

    private final List<AbstractInputMessageHandler> messageHandlers;

    @Override
    public SendMessage processMessage(BotState botState, Message message) {
        return messageHandlers.stream()
                .filter(messageHandler -> messageHandler.isApplicable(botState))
                .findFirst()
                .map(messageHandler -> messageHandler.handleMessage(message))
                .orElse(null);
//                .orElse(getFallbackMessage(message));
    }

}
