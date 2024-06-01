package ed.uopp.uopptelegrambot.client;

import ed.uopp.uopptelegrambot.data.SubscriptionData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "${application.uopp-core.name}")
public interface UoppCoreClient {

    @PostMapping("/subscriptions")
    UUID saveSubscriptionData(SubscriptionData subscriptionData);

    @GetMapping("/subscriptions/{uuid}")
    SubscriptionData getSubscriptionData(@PathVariable("uuid") UUID uuid);

    @DeleteMapping("/subscriptions/{uuid}")
    void deleteSubscriptionData(@PathVariable("uuid") UUID uuid);

}
