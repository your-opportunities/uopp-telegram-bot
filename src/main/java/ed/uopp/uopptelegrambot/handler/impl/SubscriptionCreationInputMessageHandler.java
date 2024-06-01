package ed.uopp.uopptelegrambot.handler.impl;

import ed.uopp.uopptelegrambot.data.BotState;
import ed.uopp.uopptelegrambot.data.SubscriptionData;
import ed.uopp.uopptelegrambot.handler.AbstractInputMessageHandler;
import ed.uopp.uopptelegrambot.service.UoppCoreIntegrationService;
import ed.uopp.uopptelegrambot.service.UserDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static ed.uopp.uopptelegrambot.data.BotState.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class SubscriptionCreationInputMessageHandler extends AbstractInputMessageHandler {

    public static final String NEXT_FILTER_FORMAT = ">>> Натупний фільтр ʼФорматʼ >>>";
    public static final String NEXT_FILTER_ASAP = ">>> Натупний фільтр ʼТерміновістьʼ >>>";
    public static final List<String> CATEGORIES = List.of("вебінар", "волонтерство", "грант", "конкурс", "конференція", "курс", "лекція",
            "майстер-клас", "хакатон", "обмін", "вакансія", "проєкт", "стажування",
            "стипендія", "табір", "турнір", "тренінг");
    public static final List<String> FORMATS = List.of("онлайн", "офлайн");
    public static final List<String> ASAP = List.of("термінові", "будь-які");
    public static final List<String> CATEGORIES_BUTTONS = Stream.concat(CATEGORIES.stream(), Stream.of(NEXT_FILTER_FORMAT)).toList();
    public static final List<String> FORMATS_BUTTONS = Stream.concat(FORMATS.stream(), Stream.of(NEXT_FILTER_ASAP)).toList();
    public static final List<String> ASAP_BUTTONS = ASAP;

    private final UserDataService userDataService;
    private final UoppCoreIntegrationService uoppCoreIntegrationService;

    @Override
    public Set<BotState> getHandlerBotStates() {
        return Set.of(CREATE_SUBSCRIPTION_STATE, CHOOSE_CATEGORY_STATE, CHOOSE_FORMAT_STATE, CHOOSE_ASAP_STATE);
    }

    @Override
    public SendMessage handleMessage(Message message) {
        String messageText = message.getText();
        Long userId = message.getFrom().getId();
        Long chatId = message.getChatId();
        SendMessage sendMessage = new SendMessage(chatId.toString(), "Помилка! Не можемо обробити дане повідомлення!");

        if (userDataService.getUserInternalSubscriptionId(userId) != null) {
            return new SendMessage(chatId.toString(), "У вас уже є підписка на сповіщення! Видаліть існуюючу, аби створити нову: /delete_subscription, /create_subscription");
        }

        if (userDataService.getUserBotState(userId).equals(CREATE_SUBSCRIPTION_STATE)) {
            SubscriptionData subscriptionData = new SubscriptionData();
            subscriptionData.setUserId(chatId.toString());
            userDataService.saveUserSubscriptionDataForm(userId, subscriptionData);
            userDataService.saveUserBotState(userId, CHOOSE_CATEGORY_STATE);
        }

        if (userDataService.getUserBotState(userId).equals(CHOOSE_CATEGORY_STATE)) {
            if (CATEGORIES.contains(messageText)) {
                SubscriptionData subscriptionData = userDataService.getUserSubscriptionDataForm(userId);
                Set<String> categories = subscriptionData.getCategories();
                categories.add(messageText);
                userDataService.saveUserSubscriptionDataForm(userId, subscriptionData);

                sendMessage = new SendMessage(userId.toString(),
                        String.format("Категорія '%s' додана. \n Список обраних категорій: %s", messageText, categories));

                sendMessage.setReplyMarkup(getKeyboard(CollectionUtils.subtract(CATEGORIES_BUTTONS, categories)));
            } else if (messageText.equals(NEXT_FILTER_FORMAT)) {
                userDataService.saveUserBotState(userId, CHOOSE_FORMAT_STATE);
            } else {
                sendMessage = new SendMessage(userId.toString(), "Оберіть категорію:");
                sendMessage.setReplyMarkup(getKeyboard(CATEGORIES_BUTTONS));
            }
        }


        if (userDataService.getUserBotState(userId).equals(CHOOSE_FORMAT_STATE)) {
            if (FORMATS.contains(messageText)) {
                SubscriptionData subscriptionData = userDataService.getUserSubscriptionDataForm(userId);
                Set<String> formats = subscriptionData.getFormats();
                formats.add(messageText);
                userDataService.saveUserSubscriptionDataForm(userId, subscriptionData);

                sendMessage = new SendMessage(userId.toString(), String.format("Формат '%s' доданий. \n Список обраних форматів: %s",
                        messageText, formats));
                sendMessage.setReplyMarkup(getKeyboard(CollectionUtils.subtract(FORMATS_BUTTONS, formats)));
            } else if (messageText.equals(NEXT_FILTER_ASAP)) {
                userDataService.saveUserBotState(userId, CHOOSE_ASAP_STATE);
            } else {
                sendMessage = new SendMessage(userId.toString(), "Оберіть формат:");
                sendMessage.setReplyMarkup(getKeyboard(FORMATS_BUTTONS));
            }
        }

        if (userDataService.getUserBotState(userId).equals(CHOOSE_ASAP_STATE)) {
            if (ASAP.contains(messageText)) {
                SubscriptionData subscriptionData = userDataService.getUserSubscriptionDataForm(userId);
                subscriptionData.setAsap(messageText.equals("термінові"));
                userDataService.saveUserSubscriptionDataForm(userId, subscriptionData);

                userDataService.saveUserBotState(userId, PERSIST_SUBSCRIPTION_STATE);
            } else {
                sendMessage = new SendMessage(userId.toString(), "Оберіть терміновість:");
                sendMessage.setReplyMarkup(getKeyboard(ASAP_BUTTONS));
            }
        }

        if (userDataService.getUserBotState(userId).equals(PERSIST_SUBSCRIPTION_STATE)) {
            var subscriptionData = userDataService.getUserSubscriptionDataForm(userId);
            var saveSubscriptionResult = uoppCoreIntegrationService.saveSubscriptionData(userDataService.getUserSubscriptionDataForm(userId));

            if (saveSubscriptionResult.isEmpty()) {
                log.error(String.format("Failed to save subscription for userId = %s, chatId = %s", userId, chatId));
                sendMessage = new SendMessage(userId.toString(),
                        "Сталася помилка при збереженні підписки! Спробуйте створити підписку знову: /create_subscription");
            } else {
                userDataService.saveUserInternalSubscriptionId(userId, saveSubscriptionResult.get());
                sendMessage = new SendMessage(userId.toString(), String.format("Підписка створена: %s", subscriptionData));
                sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
            }
        }

        return sendMessage;
    }

    private ReplyKeyboardMarkup getKeyboard(Collection<String> values) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        values.forEach(value -> keyboard.add(new KeyboardRow(value)));
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }

}
