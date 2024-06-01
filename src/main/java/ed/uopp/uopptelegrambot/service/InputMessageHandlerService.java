package ed.uopp.uopptelegrambot.service;

import ed.uopp.uopptelegrambot.data.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface InputMessageHandlerService {

    SendMessage processMessage(BotState botState, Message message);

}
