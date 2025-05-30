package org.example.prime_prospects.contrallers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.prime_prospects.AuthManager;
import org.example.prime_prospects.JavaFxApplication;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    private Runnable mainViewCallback;
    private JavaFxApplication app;

    public void setApp(JavaFxApplication mainApp){
        this.app = mainApp;
    }

    public void setMainViewCallback(Runnable callback){
        this.mainViewCallback = callback;
    }

    @FXML
    protected void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (AuthManager.login(username, password)) {
            if (mainViewCallback != null){
                mainViewCallback.run();
            }
        } else {
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    @FXML
    protected void regClick(ActionEvent event) throws IOException {
        this.app.showRegisterScreen();
    }
}
