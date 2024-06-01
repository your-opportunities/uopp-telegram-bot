package ed.uopp.uopptelegrambot.facade.impl;

import ed.uopp.uopptelegrambot.data.BotState;
import ed.uopp.uopptelegrambot.facade.TelegramFacade;
import ed.uopp.uopptelegrambot.service.InputMessageHandlerService;
import ed.uopp.uopptelegrambot.service.UserDataService;
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

    public static final String START_CMD = "/start";
    public static final String CREATE_SUBSCRIPTION_CMD = "/create_subscription";
    public static final String VIEW_SUBSCRIPTION_CMD = "/view_subscription";
    public static final String DELETE_SUBSCRIPTION_CMD = "/delete_subscription";

    private final InputMessageHandlerService inputMessageHandlerService;
    private final UserDataService userDataService;

    @Override
    public SendMessage handleUpdate(Update update) {
        Message message = update.getMessage();

        if (message == null || !message.hasText()) {
            log.error("wrong error message");
            throw new RuntimeException();
        }

        return processMessage(message);
    }

    private SendMessage processMessage(Message message) {
        String messageText = message.getText();
        Long userId = message.getFrom().getId();

        BotState botState = switch (messageText) {
            case START_CMD -> BotState.INIT_STATE;
            case CREATE_SUBSCRIPTION_CMD -> BotState.CREATE_SUBSCRIPTION_STATE;
            case VIEW_SUBSCRIPTION_CMD -> BotState.VIEW_SUBSCRIPTION_STATE;
            case DELETE_SUBSCRIPTION_CMD -> BotState.DELETE_SUBSCRIPTION_STATE;
            default -> userDataService.getUserBotState(userId);
        };

        userDataService.saveUserBotState(userId, botState);

        return inputMessageHandlerService.processMessage(botState, message);
    }

}
