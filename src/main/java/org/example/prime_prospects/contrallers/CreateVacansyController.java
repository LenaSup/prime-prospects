package org.example.prime_prospects.contrallers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
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

public class CreateVacansyController {
    private JavaFxApplication app;

    public void setApp(JavaFxApplication app){
        this.app = app;
    }

    @FXML
    private void logOutClick() throws IOException {
        this.app.showLoginScreen();
    }

    @FXML
    public void backClick() throws IOException{
        this.app.showProfileView();
    }

    @FXML
    private TextField positionField;

    @FXML
    private TextField workExperienceYearsField;

    @FXML
    private TextField salaryField;

    @FXML
    private TextArea requirementsArea;

    @FXML
    private TextArea whatWeOfferArea;

    @FXML
    private TextArea additionalSkillsArea;

    @FXML
    private ChoiceBox<String> employmentTypeChoiceBox;

    @FXML
    private ChoiceBox<String> scheduleChoiceBox;

    @FXML
    private TextField workingHoursField;

    @FXML
    private TextField addressField;

    @FXML
    public void save(){
        String position = positionField.getText();
        String workExperienceYears = workExperienceYearsField.getText();
        String salary = salaryField.getText();
        String requirements = requirementsArea.getText();
        String whatWeOffer = whatWeOfferArea.getText();
        String additionalSkills = additionalSkillsArea.getText();
        String employmentType = employmentTypeChoiceBox.getValue();
        String schedule = scheduleChoiceBox.getValue();
        String workingHours = workingHoursField.getText();
        String address = addressField.getText();

        try {
            HttpClient client = HttpClient.newHttpClient();
            String authToken = AuthManager.getAuthToken();
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("position", position);
            jsonBody.put("workExperienceYears", workExperienceYears);
            jsonBody.put("salary", salary);
            jsonBody.put("requirements", requirements);
            jsonBody.put("whatWeOffer", whatWeOffer);
            jsonBody.put("additional_skill", additionalSkills);
            jsonBody.put("employmentType", employmentType);
            jsonBody.put("schedule", schedule);
            jsonBody.put("workingHours", workingHours);
            jsonBody.put("address", address);
            String jsonString = jsonBody.toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8082/update/vacansy"))
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
                workExperienceYearsField.setText("");
                salaryField.setText("");
                requirementsArea.setText("");
                whatWeOfferArea.setText("");
                additionalSkillsArea.setText("");
                workingHoursField.setText("");
                addressField.setText("");
            }
        } catch (JSONException | InterruptedException | java.io.IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

}
