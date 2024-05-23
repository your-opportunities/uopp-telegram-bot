package ed.uopp.uopptelegrambot.facade.impl;

import ed.uopp.uopptelegrambot.facade.TelegramFacade;
import ed.uopp.uopptelegrambot.service.InputMessageHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@RequiredArgsConstructor
@Slf4j
@Service
public class DefaultTelegramFacade implements TelegramFacade {

    //    private final CallbackQueryFacade callbackQueryFacade;
    private final InputMessageHandlerService inputMessageHandlerService;

    @Override
    public SendMessage handleUpdate(Update update) {
        SendMessage replyMessage = null;

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            replyMessage = inputMessageHandlerService.handleMessage(message);
        }

        return replyMessage;
    }


//    private SendMessage getMainMenuMessage2(Long chatId, String textMessage) {
//        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard(new Object());
//        final SendMessage mainMenuMessage =
//                createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
//
//        return mainMenuMessage;
//    }
//
//    private ReplyKeyboardMarkup getMainMenuKeyboard(Object o) {
//
//
//
//        List<KeyboardRow> keyboard = new ArrayList<>();
//
//        KeyboardRow row1 = new KeyboardRow();
//        KeyboardRow row2 = new KeyboardRow();
//        KeyboardRow row3 = new KeyboardRow();
//        row1.add(new KeyboardButton("2Перейти до наступного фільтра"));
//        row1.add(new KeyboardButton("2One"));
//        row2.add(new KeyboardButton("2Two"));
//        row3.add(new KeyboardButton("2Three"));
//        keyboard.add(row1);
//        keyboard.add(row2);
//        keyboard.add(row3);
//        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboard);
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//        return replyKeyboardMarkup;
//    }
//
//    public SendMessage getMainMenuMessage(final long chatId, final String textMessage) {
//        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
//        final SendMessage mainMenuMessage =
//                createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
//
//        return mainMenuMessage;
//    }
//
//    private ReplyKeyboardMarkup getMainMenuKeyboard() {
//
//
//
//        List<KeyboardRow> keyboard = new ArrayList<>();
//
////        KeyboardRow row1 = new KeyboardRow();
////        KeyboardRow row2 = new KeyboardRow();
////        KeyboardRow row3 = new KeyboardRow();
////        row1.add(new KeyboardButton("Перейти до наступного фільтра"));
////        row1.add(new KeyboardButton("One"));
////        row2.add(new KeyboardButton("Two"));
////        row3.add(new KeyboardButton("Three"));
////        keyboard.add(row1);
////        keyboard.add(row2);
////        keyboard.add(row3);
//
//        for (int i = 0; i < 20; i++) {
//            keyboard.add(new KeyboardRow("This is " + i));
//        }
//
//        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboard);
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//        return replyKeyboardMarkup;
//    }
//
//    private SendMessage createMessageWithKeyboard(Long chatId,
//                                                  String textMessage,
//                                                  final ReplyKeyboardMarkup replyKeyboardMarkup) {
//        final SendMessage sendMessage = new SendMessage(chatId.toString(), textMessage);
//        sendMessage.enableMarkdown(true);
//        if (replyKeyboardMarkup != null) {
//            sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        }
//        return sendMessage;
//    }

}
