package ed.uopp.uopptelegrambot.data;

import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@ToString
@Data
public class SubscriptionData {

    private String userId;
    private Set<String> categories = new HashSet<>();
    private Set<String> formats = new HashSet<>();
    private Boolean asap;
    private String subscriptionChannel = "TELEGRAM";

}
