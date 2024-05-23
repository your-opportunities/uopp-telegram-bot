package ed.uopp.uopptelegrambot.service.impl;

import ed.uopp.uopptelegrambot.handler.AbstractInputMessageHandler;
import ed.uopp.uopptelegrambot.handler.impl.CategoryMessageHandler;
import ed.uopp.uopptelegrambot.handler.impl.FormatMessageHandler;
import ed.uopp.uopptelegrambot.handler.impl.IsAsapMessageHandler;
import ed.uopp.uopptelegrambot.handler.impl.StartMessageHandler;
import ed.uopp.uopptelegrambot.service.InputMessageHandlerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

@Service
public class DefaultInputMessageHandlerService implements InputMessageHandlerService {

    private final List<AbstractInputMessageHandler> messageHandlers;

    public DefaultInputMessageHandlerService() {
        messageHandlers = List.of(
                new StartMessageHandler(),
                new CategoryMessageHandler(),
                new FormatMessageHandler(),
                new IsAsapMessageHandler()
        );
    }

    @Override
    public SendMessage handleMessage(Message message) {
        return messageHandlers.stream()
                .filter(messageHandler -> messageHandler.isApplicable(message))
                .findFirst()
                .map(messageHandler -> messageHandler.handle(message))
                .orElse(getFallbackMessage(message));
    }

    private SendMessage getFallbackMessage(Message message) {
        return new SendMessage(message.getChatId().toString(), "Wrong command, try again!");
    }

}
