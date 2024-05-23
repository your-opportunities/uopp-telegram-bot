package ed.uopp.uopptelegrambot.handler.impl;

import ed.uopp.uopptelegrambot.handler.AbstractInputMessageHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public class IsAsapMessageHandler extends AbstractInputMessageHandler {
    @Override
    public String messageCode() {
        return "isAsap";
    }

    @Override
    public SendMessage handle(Message message) {
        return null;
    }
}
