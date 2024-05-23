package ed.uopp.uopptelegrambot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface InputMessageHandlerService {

    SendMessage handleMessage(Message message);

}
