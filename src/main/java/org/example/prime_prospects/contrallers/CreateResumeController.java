package org.example.prime_prospects.contrallers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.prime_prospects.AuthManager;
import org.example.prime_prospects.JavaFxApplication;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class CreateResumeController {
    private JavaFxApplication app;

    public void setApp(JavaFxApplication app){
        this.app = app;
    }

    @FXML
    private TextField positionField;

    @FXML
    private TextField workYearsField;

    @FXML
    private TextField expectedSalaryField;

    @FXML
    private TextArea skillsArea;

    @FXML
    private TextArea aboutMeArea;

    @FXML
    private TextArea additionalSkillsArea;

    @FXML
    private Button saveButton;

    @FXML
    private void logOutClick() throws IOException {
        this.app.showLoginScreen();
    }

    @FXML
    public void backClick() throws IOException{
        this.app.showProfileView();
    }

    @FXML
    public void save() {
        String position = positionField.getText();
        String workYears = workYearsField.getText();
        String expectedSalary = expectedSalaryField.getText();
        String skills = skillsArea.getText();
        String aboutMe = aboutMeArea.getText();
        String additionalSkills = additionalSkillsArea.getText();

        if (skills.length() > 250) {
            skills = skills.substring(0, 250);
            skillsArea.setText(skills);
        }
        if (aboutMe.length() > 1000) {
            aboutMe = aboutMe.substring(0, 1000);
            aboutMeArea.setText(aboutMe);
        }
        if (additionalSkills.length() > 250) {
            additionalSkills = additionalSkills.substring(0, 250);
            additionalSkillsArea.setText(additionalSkills);
        }


        try {
            HttpClient client = HttpClient.newHttpClient();
            String authToken = AuthManager.getAuthToken();
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("position", position);
            jsonBody.put("workYears", workYears);
            jsonBody.put("expectedSalary", expectedSalary);
            jsonBody.put("skills", skills);
            jsonBody.put("aboutMe", aboutMe);
            jsonBody.put("additionalSkills", additionalSkills);
            String jsonString = jsonBody.toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8082/update/resume"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + authToken)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonString, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                app.showProfileView();
            }
            else {
                positionField.setText("");
                workYearsField.setText("");
                expectedSalaryField.setText("");
                skillsArea.setText("");
                aboutMeArea.setText("");
                additionalSkillsArea.setText("");
            }
        } catch (JSONException | InterruptedException | java.io.IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

    }
}
