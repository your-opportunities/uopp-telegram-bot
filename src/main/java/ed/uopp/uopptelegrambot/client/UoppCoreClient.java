package ed.uopp.uopptelegrambot.client;

import ed.uopp.uopptelegrambot.data.SubscriptionData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient
public interface UoppCoreClient {

    @PostMapping("/subscriptions/")
    void saveSubscriptionData(SubscriptionData subscriptionData);

}
