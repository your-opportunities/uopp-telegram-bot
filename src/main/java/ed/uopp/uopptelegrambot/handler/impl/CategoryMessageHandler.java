package ed.uopp.uopptelegrambot.handler.impl;

import ed.uopp.uopptelegrambot.handler.AbstractInputMessageHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public class CategoryMessageHandler extends AbstractInputMessageHandler {

    @Override
    public String messageCode() {
        return "Category";
    }

    @Override
    public SendMessage handle(Message message) {
        return null;
    }

}
