package ed.uopp.uopptelegrambot.service.impl;

import ed.uopp.uopptelegrambot.data.BotState;
import ed.uopp.uopptelegrambot.data.SubscriptionData;
import ed.uopp.uopptelegrambot.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "application.telegram-bot.state-persistence-type",
        havingValue = "redis",
        matchIfMissing = false)
@Service
public class RedisUserDataService implements UserDataService {

    public static final String USER_BOT_STATE = "USER_BOT_STATE";
    public static final String USER_SUBSCRIPTIONS = "USER_SUBSCRIPTIONS";
    public static final String USER_INTERNAL_SUB_ID = "USER_INTERNAL_SUB_ID_";
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveUserBotState(Long userId, BotState botState) {
        redisTemplate.opsForHash().put(USER_BOT_STATE, userId.toString(), botState);
    }

    @Override
    public BotState getUserBotState(Long userId) {
        return BotState.valueOf((String) redisTemplate.opsForHash().get(USER_BOT_STATE, userId.toString()));
    }

    @Override
    public void saveUserSubscriptionDataForm(Long userId, SubscriptionData subscriptionData) {
        redisTemplate.opsForHash().put(USER_SUBSCRIPTIONS, userId.toString(), subscriptionData);
    }

    @Override
    public SubscriptionData getUserSubscriptionDataForm(Long userId) {
        return (SubscriptionData) redisTemplate.opsForHash().get(USER_SUBSCRIPTIONS, userId.toString());
    }

    @Override
    public void saveUserInternalSubscriptionId(Long userId, UUID uuid) {
        redisTemplate.opsForValue().set(USER_INTERNAL_SUB_ID + userId, uuid.toString());
    }

    @Override
    public UUID getUserInternalSubscriptionId(Long userId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(USER_INTERNAL_SUB_ID + userId))
                .map(Object::toString)
                .map(UUID::fromString)
                .orElse(null);
    }

    @Override
    public void removeUserInternalSubscriptionId(Long userId) {
        redisTemplate.delete(USER_INTERNAL_SUB_ID + userId);
    }
}
