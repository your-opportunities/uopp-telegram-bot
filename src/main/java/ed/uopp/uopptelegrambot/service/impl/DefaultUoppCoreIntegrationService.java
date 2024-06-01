package ed.uopp.uopptelegrambot.service.impl;

import ed.uopp.uopptelegrambot.client.UoppCoreClient;
import ed.uopp.uopptelegrambot.data.SubscriptionData;
import ed.uopp.uopptelegrambot.service.UoppCoreIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultUoppCoreIntegrationService implements UoppCoreIntegrationService {

    private final UoppCoreClient uoppCoreClient;

    @Override
    public Optional<UUID> saveSubscriptionData(SubscriptionData subscriptionData) {
        try {
            return Optional.of(uoppCoreClient.saveSubscriptionData(subscriptionData));
        } catch (Exception e) {
            log.info("Some exception occurred" + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<SubscriptionData> getSubscriptionData(UUID uuid) {
        try {
            return Optional.of(uoppCoreClient.getSubscriptionData(uuid));
        } catch (Exception e) {
            log.info("Some exception occurred" + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteSubscription(UUID uuid) {
        try {
            uoppCoreClient.deleteSubscriptionData(uuid);
            return true;
        } catch (Exception e) {
            log.info("Some exception occurred" + e.getMessage());
        }
        return false;
    }
}
