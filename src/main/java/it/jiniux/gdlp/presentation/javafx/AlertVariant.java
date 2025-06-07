package it.jiniux.gdlp.presentation.javafx;

import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.scene.control.Alert;

public enum AlertVariant {
    GENERIC_ERROR {
        @Override
        public LocalizationString getContentString() {
            return LocalizationString.GENERIC_ERROR_ALERT_CONTENT;
        }

        @Override
        public LocalizationString getTitleString() {
            return LocalizationString.GENERIC_ERROR_ALERT_TITLE;
        }
    },
    INVALID_ADD_BOOK_FORM {
        @Override
        public LocalizationString getContentString() {
            return LocalizationString.INVALID_ADD_BOOK_FORM_ALERT_CONTENT;
        }

        @Override
        public LocalizationString getTitleString() {
            return LocalizationString.INVALID_ADD_BOOK_FORM_ALERT_TITLE;
        }
    },
    GENERIC_BOOK_NOT_ADDED_ERROR {
        @Override
        public LocalizationString getContentString() {
            return LocalizationString.GENERIC_BOOK_NOT_ADDED_ERROR_ALERT_CONTENT;
        }

        @Override
        public LocalizationString getTitleString() {
            return LocalizationString.GENERIC_BOOK_NOT_ADDED_ERROR_ALERT_TITLE;
        }
    },
    BOOK_ADDED_SUCCESSFULLY {;
        @Override
        public LocalizationString getContentString() {
            return LocalizationString.BOOK_ADDED_SUCCESSFULLY_ALERT_CONTENT;
        }

        @Override
        public LocalizationString getTitleString() {
            return LocalizationString.BOOK_ADDED_SUCCESSFULLY_ALERT_TITLE;
        }

        @Override
        public Alert.AlertType getAlertType() {
            return Alert.AlertType.INFORMATION;
        }
    };

    public LocalizationString getContentString() {
        throw new UnsupportedOperationException("This method should be implemented in subclasses");
    }

    public LocalizationString getTitleString() {
        throw new UnsupportedOperationException("This method should be implemented in subclasses");
    }


    public Alert.AlertType getAlertType() {
        return Alert.AlertType.ERROR; // default type
    }
}
