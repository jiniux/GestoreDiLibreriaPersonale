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
    INVALID_EDIT_BOOK_FORM {
        @Override
        public LocalizationString getContentString() {
            return LocalizationString.INVALID_EDIT_BOOK_FORM_ALERT_CONTENT;
        }

        @Override
        public LocalizationString getTitleString() {
            return LocalizationString.INVALID_EDIT_BOOK_FORM_ALERT_TITLE;
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
    },
    CONFIRM_REMOVE_BOOK {
        @Override
        public LocalizationString getContentString() {
            return LocalizationString.CONFIRM_REMOVE_BOOK_ALERT_CONTENT;
        }

        @Override
        public LocalizationString getTitleString() {
            return LocalizationString.CONFIRM_REMOVE_BOOK_ALERT_TITLE;
        }

        @Override
        public Alert.AlertType getAlertType() {
            return Alert.AlertType.CONFIRMATION;
        }
    }, GENERIC_BOOK_NOT_REMOVED_ERROR {
        @Override
        public LocalizationString getTitleString() {
            return LocalizationString.GENERIC_BOOK_NOT_REMOVED_ERROR_ALERT_TITLE;
        }

        @Override
        public LocalizationString getContentString() {
            return LocalizationString.GENERIC_BOOK_NOT_REMOVED_ERROR_ALERT_CONTENT;
        }
    }, BOOK_REMOVED_SUCCESSFULLY {
        @Override
        public LocalizationString getContentString() {
            return LocalizationString.BOOK_REMOVED_SUCCESSFULLY_ALERT_CONTENT;
        }

        @Override
        public LocalizationString getTitleString() {
            return LocalizationString.BOOK_REMOVED_SUCCESSFULLY_ALERT_TITLE;
        }

        @Override
        public Alert.AlertType getAlertType() {
            return Alert.AlertType.INFORMATION;
        }
    }, BOOK_EDITED_SUCCESSFULLY {
        @Override
        public LocalizationString getContentString() {
            return LocalizationString.BOOK_EDITED_SUCCESSFULLY_ALERT_CONTENT;
        }

        @Override
        public LocalizationString getTitleString() {
            return LocalizationString.BOOK_EDITED_SUCCESSFULLY_ALERT_TITLE;
        }

        @Override
        public Alert.AlertType getAlertType() {
            return Alert.AlertType.INFORMATION;
        }
    }, GENERIC_BOOK_NOT_UPDATED_ERROR {
        @Override
        public LocalizationString getContentString() {
            return LocalizationString.GENERIC_BOOK_NOT_EDITED_ERROR_ALERT_CONTENT;
        }

        @Override
        public LocalizationString getTitleString() {
            return LocalizationString.GENERIC_BOOK_NOT_EDITED_ERROR_ALERT_TITLE;
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
