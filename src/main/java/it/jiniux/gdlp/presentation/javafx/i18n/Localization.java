package it.jiniux.gdlp.presentation.javafx.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localization {
    private final ResourceBundle resourceBundle;

    public Localization() {
        resourceBundle = ResourceBundle.getBundle("it.jiniux.gdlp.presentation.javafx.i18n.messages_" +
               Locale.getDefault().getLanguage());
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public String get(LocalizationString key, Object... args) {
        String format = resourceBundle.getString(key.name().toLowerCase());

        MessageFormat messageFormat = new MessageFormat(format, Locale.getDefault());
        return messageFormat.format(args);
    }
}
