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
    },
    
    INVALID_FILTER_VALUE_TYPE {
        @Override public LocalizationString getContentString() { return LocalizationString.INVALID_FILTER_VALUE_FORMAT_ALERT_CONTENT; }
        @Override public LocalizationString getTitleString() { return LocalizationString.INVALID_FILTER_VALUE_FORMAT_ALERT_TITLE; }
    },
    INVALID_FILTER_VALUE_FORMAT {
        @Override public LocalizationString getContentString() { return LocalizationString.INVALID_FILTER_VALUE_FORMAT_ALERT_CONTENT; }
        @Override public LocalizationString getTitleString() { return LocalizationString.INVALID_FILTER_VALUE_FORMAT_ALERT_TITLE; }
    },
    INVALID_FILTER_FIELD_EMPTY {
        @Override public LocalizationString getContentString() { return LocalizationString.INVALID_FILTER_FIELD_EMPTY_ALERT_CONTENT; }
        @Override public LocalizationString getTitleString() { return LocalizationString.INVALID_FILTER_FIELD_EMPTY_ALERT_TITLE; }
    },
    INVALID_FILTER_OPERATOR_EMPTY {
        @Override public LocalizationString getContentString() { return LocalizationString.INVALID_FILTER_OPERATOR_EMPTY_ALERT_CONTENT; }
        @Override public LocalizationString getTitleString() { return LocalizationString.INVALID_FILTER_OPERATOR_EMPTY_ALERT_TITLE; }
    },
    INVALID_FILTER_VALUE_EMPTY {
        @Override public LocalizationString getContentString() { return LocalizationString.INVALID_FILTER_VALUE_EMPTY_ALERT_CONTENT; }
        @Override public LocalizationString getTitleString() { return LocalizationString.INVALID_FILTER_VALUE_EMPTY_ALERT_TITLE; }
    },
    INVALID_FILTER_OPERATOR_FOR_FIELD {
        @Override public LocalizationString getContentString() { return LocalizationString.INVALID_FILTER_OPERATOR_FOR_FIELD_ALERT_CONTENT; }
        @Override public LocalizationString getTitleString() { return LocalizationString.INVALID_FILTER_OPERATOR_FOR_FIELD_ALERT_TITLE; }
    },
    NO_FILTER_SELECTED_FOR_EDIT {
        @Override public LocalizationString getContentString() { return LocalizationString.NO_FILTER_SELECTED_FOR_EDIT_ALERT_CONTENT; }
        @Override public LocalizationString getTitleString() { return LocalizationString.NO_FILTER_SELECTED_FOR_EDIT_ALERT_TITLE; }
        @Override public Alert.AlertType getAlertType() { return Alert.AlertType.WARNING; }
    },
    NO_FILTER_SELECTED_FOR_REMOVE {
        @Override public LocalizationString getContentString() { return LocalizationString.NO_FILTER_SELECTED_FOR_REMOVE_ALERT_CONTENT; }
        @Override public LocalizationString getTitleString() { return LocalizationString.NO_FILTER_SELECTED_FOR_REMOVE_ALERT_TITLE; }
        @Override public Alert.AlertType getAlertType() { return Alert.AlertType.WARNING; }
    },
    CONFIRM_REMOVE_FILTER {
        @Override public LocalizationString getContentString() { return LocalizationString.CONFIRM_REMOVE_FILTER_ALERT_CONTENT; }
        @Override public LocalizationString getTitleString() { return LocalizationString.CONFIRM_REMOVE_FILTER_ALERT_TITLE; }
        @Override public Alert.AlertType getAlertType() { return Alert.AlertType.CONFIRMATION; }
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
