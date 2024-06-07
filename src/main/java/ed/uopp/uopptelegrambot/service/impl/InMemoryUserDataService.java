package ed.uopp.uopptelegrambot.service.impl;

import ed.uopp.uopptelegrambot.data.BotState;
import ed.uopp.uopptelegrambot.data.SubscriptionData;
import ed.uopp.uopptelegrambot.service.UserDataService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@ConditionalOnProperty(
        name = "application.telegram-bot.state-persistence-type",
        havingValue = "inMemory",
        matchIfMissing = true)
@Service
public class InMemoryUserDataService implements UserDataService {

    private final Map<Long, BotState> usersBotStates = new ConcurrentHashMap<>();
    private final Map<Long, SubscriptionData> userSubscriptions = new ConcurrentHashMap<>();
    private final Map<Long, UUID> usersInternalSubscriptionsIds = new ConcurrentHashMap<>();

    @Override
    public void saveUserBotState(Long userId, BotState botState) {
        usersBotStates.put(userId, botState);
    }

    @Override
    public BotState getUserBotState(Long userId) {
        return usersBotStates.get(userId);
    }

    @Override
    public void saveUserSubscriptionDataForm(Long userId, SubscriptionData subscriptionData) {
        userSubscriptions.put(userId, subscriptionData);
    }

    @Override
    public SubscriptionData getUserSubscriptionDataForm(Long userId) {
        return userSubscriptions.get(userId);
    }

    @Override
    public void saveUserInternalSubscriptionId(Long userId, UUID uuid) {
        usersInternalSubscriptionsIds.put(userId, uuid);
    }

    @Override
    public UUID getUserInternalSubscriptionId(Long userId) {
        return usersInternalSubscriptionsIds.get(userId);
    }

    @Override
    public void removeUserInternalSubscriptionId(Long userId) {
        usersInternalSubscriptionsIds.remove(userId);
    }

}
