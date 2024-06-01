package ed.uopp.uopptelegrambot.service;

import ed.uopp.uopptelegrambot.data.BotState;
import ed.uopp.uopptelegrambot.data.SubscriptionData;

import java.util.UUID;

public interface UserDataService {

    void saveUserBotState(Long userId, BotState botState);

    BotState getUserBotState(Long userId);

    void saveUserSubscriptionDataForm(Long userId, SubscriptionData subscriptionData);

    SubscriptionData getUserSubscriptionDataForm(Long userId);

    void saveUserInternalSubscriptionId(Long userId, UUID uuid);
    UUID getUserInternalSubscriptionId(Long userId);

    void removeUserInternalSubscriptionId(Long userId);
}
