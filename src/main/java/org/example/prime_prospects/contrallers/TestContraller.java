package org.example.prime_prospects.contrallers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class TestContraller {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
