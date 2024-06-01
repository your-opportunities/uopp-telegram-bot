package ed.uopp.uopptelegrambot.bot;

import ed.uopp.uopptelegrambot.facade.TelegramFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static ed.uopp.uopptelegrambot.facade.impl.DefaultTelegramFacade.*;

@Slf4j
@Component
public class UoppTelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final TelegramFacade telegramFacade;

    public UoppTelegramBot(TelegramFacade telegramFacade) throws TelegramApiException {
        this.telegramFacade = telegramFacade;
        telegramClient = new OkHttpTelegramClient(getBotToken());

        SetMyCommands setMyCommandsRequest = SetMyCommands.builder()
                .command(new BotCommand(START_CMD, "Start"))
                .command(new BotCommand(CREATE_SUBSCRIPTION_CMD, "Create subscription"))
                .command(new BotCommand(VIEW_SUBSCRIPTION_CMD, "View subscription"))
                .command(new BotCommand(DELETE_SUBSCRIPTION_CMD, "Delete subscription"))
                .build();

        telegramClient.execute(setMyCommandsRequest);
    }

    @Override
    public String getBotToken() {
        return "7046482354:AAF52EKU3Uf9WP94mgmi98-r6org5z40KeU";
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        SendMessage message = telegramFacade.handleUpdate(update);

        sendMessage(message);
    }

    public void sendMessage(SendMessage message) {
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            log.error("error: " + e.getMessage());
        }
    }

}
