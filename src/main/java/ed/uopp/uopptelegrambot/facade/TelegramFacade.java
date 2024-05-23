package ed.uopp.uopptelegrambot.facade;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramFacade {

    SendMessage handleUpdate(Update update);

}
