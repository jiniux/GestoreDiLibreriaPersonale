package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.edition;

import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CoverInputController implements Initializable {
    private final Localization localization;
    
    @FXML private Label coverPathLabel;
    @FXML private ImageView coverImageView;
    
    private File selectedImageFile;
    
    public CoverInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
    }
    
    @FXML
    public void handleSelectCover(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(localization.get(LocalizationString.COVER_IMAGE_CHOOSER_TITLE));
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter(
                localization.get(LocalizationString.IMAGES_FILTER), 
                "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"
            )
        );
        
        Stage stage = (Stage) coverImageView.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            selectedImageFile = file;
            coverPathLabel.setText(file.getName());
            
            Image image = new Image(file.toURI().toString());
            coverImageView.setImage(image);
        }
    }
    
    public String getCoverPath() {
        return selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;
    }
    
    public File getSelectedImageFile() {
        return selectedImageFile;
    }
    
    public void setSelectedImageFile(File imageFile) {
        if (imageFile != null && imageFile.exists()) {
            selectedImageFile = imageFile;
            coverPathLabel.setText(imageFile.getName());
            
            Image image = new Image(imageFile.toURI().toString());
            coverImageView.setImage(image);
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        coverPathLabel.setText(localization.get(LocalizationString.NO_IMAGE_SELECTED));
    }
}
