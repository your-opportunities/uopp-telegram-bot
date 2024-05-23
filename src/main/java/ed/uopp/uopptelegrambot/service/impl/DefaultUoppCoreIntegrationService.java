package ed.uopp.uopptelegrambot.service.impl;

import ed.uopp.uopptelegrambot.client.UoppCoreClient;
import ed.uopp.uopptelegrambot.data.SubscriptionData;
import ed.uopp.uopptelegrambot.service.UoppCoreIntegrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultUoppCoreIntegrationService implements UoppCoreIntegrationService {

    private final UoppCoreClient uoppCoreClient;

    @Override
    public void saveSubscriptionData(Object object) {
        try {
            // process input data
            // create subscription data
            uoppCoreClient.saveSubscriptionData(new SubscriptionData());
        } catch (Exception e) {
            log.info("Some exception occurred");
        }
    }

}
