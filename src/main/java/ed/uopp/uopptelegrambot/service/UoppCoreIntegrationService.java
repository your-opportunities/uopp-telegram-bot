package ed.uopp.uopptelegrambot.service;

import ed.uopp.uopptelegrambot.data.SubscriptionData;

import java.util.Optional;
import java.util.UUID;

public interface UoppCoreIntegrationService {

    Optional<UUID> saveSubscriptionData(SubscriptionData subscriptionData);

    Optional<SubscriptionData> getSubscriptionData(UUID uuid);

    boolean deleteSubscription(UUID uuid);

}
