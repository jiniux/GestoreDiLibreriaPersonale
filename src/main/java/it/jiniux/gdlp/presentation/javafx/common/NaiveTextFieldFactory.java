package it.jiniux.gdlp.presentation.javafx.common;

import javafx.scene.control.TextField;

public class NaiveTextFieldFactory implements TextFieldFactory{
    @Override
    public TextField createTextField() {
        return new TextField();
    }
}
