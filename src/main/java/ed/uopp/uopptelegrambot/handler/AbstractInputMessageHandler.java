package ed.uopp.uopptelegrambot.handler;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@RequiredArgsConstructor
public abstract class AbstractInputMessageHandler {

//    private final PropertyMessageService propertyMessageService;

    public boolean isApplicable(Message message) {
        return message.getText().equals(messageCode());
//                message.getText().equals(propertyMessageService.getMessage(messageCode()));
    }

    public abstract String messageCode();

    public abstract SendMessage handle(Message message);

}

