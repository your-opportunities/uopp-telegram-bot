package ed.uopp.uopptelegrambot.facade;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryFacade {

    SendMessage processCallbackQuery(CallbackQuery callbackQuery);

}
