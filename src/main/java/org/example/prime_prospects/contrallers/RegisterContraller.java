package org.example.prime_prospects.contrallers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.example.prime_prospects.JavaFxApplication;
import org.json.JSONObject;
import org.json.JSONException;


public class RegisterContraller {

    @FXML
    private TextField nameField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox isEmployeeCheckbox;
    @FXML
    private Button registerButton;
    @FXML
    private Label messageLabel;

    private JavaFxApplication app;

    public void setApp(JavaFxApplication mainApp){
        this.app = mainApp;
    }


    @FXML
    protected void handleRegisterButtonAction() {
        String name = nameField.getText();
        String login = loginField.getText();
        String password = passwordField.getText();
        boolean isEmployee = !isEmployeeCheckbox.isSelected();

        try {
            HttpClient client = HttpClient.newHttpClient();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", name);
            jsonBody.put("login", login);
            jsonBody.put("password", password);
            jsonBody.put("isEmployee", isEmployee);

            String jsonString = jsonBody.toString();
            System.out.println("JSON Payload: " + jsonString);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8082/register"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                messageLabel.setText("Регистрация прошла успешно!" + response.body());
                this.app.showLoginScreen();
            } else {
                messageLabel.setText("Ошибка регистрации: " + response.statusCode());
            }
        } catch (JSONException | InterruptedException | java.io.IOException e) {
            messageLabel.setText("Ошибка: " + e.getMessage());
        }
    }
}