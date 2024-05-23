package ed.uopp.uopptelegrambot.service.impl;

import ed.uopp.uopptelegrambot.service.PropertyMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class DefaultPropertyMessageService implements PropertyMessageService {

    private final Locale locale;
    private final MessageSource messageSource;

    public DefaultPropertyMessageService(@Value("${localeTag}") String localeTag, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(localeTag);
    }

    @Override
    public String getMessage(String message) {
        return messageSource.getMessage(message, null, locale);
    }

}
