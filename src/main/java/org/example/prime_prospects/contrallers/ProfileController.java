package org.example.prime_prospects.contrallers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.example.prime_prospects.AuthManager;
import org.example.prime_prospects.JavaFxApplication;
import org.example.prime_prospects.children.ResumeVBox;
import org.example.prime_prospects.children.VacancyVBox;
import org.example.prime_prospects.dataFormats.Response;
import org.example.prime_prospects.dataFormats.Resume;
import org.example.prime_prospects.dataFormats.Vacancy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ProfileController {
    private ScrollPane scrollPane = new ScrollPane();
    private JavaFxApplication app;
    private boolean editFaktor = true;
    private boolean is_employee;
    private int page = 0;
    private String name;
    private String date;
    private String maile;
    private String number;
    private String cite;
    private String description;
    private int size = 5;

    public void setInfo(String name, String date, String maile, String number, String cite, String description){
        this.name = name;
        this.date = date;
        this.maile = maile;
        this.number = number;
        this.cite = cite;
        this.description = description;

        nameLabel.setText(this.name);

        dateLabel.setText(this.date);

        emileLabel.setText(this.maile);
        emileLabel.setDisable(true);;

        numberLabel.setText(this.number);
        numberLabel.setDisable(true);

        citeLabel.setText(this.cite);
        citeLabel.setDisable(true);

        descriptionLabel.setText(this.description);
        descriptionLabel.setDisable(true);
    }

    @FXML
    private VBox main;
    @FXML
    private VBox rightList;

    @FXML
    private Label nameLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private TextField emileLabel;
    @FXML
    private TextField numberLabel;
    @FXML
    private TextField citeLabel;
    @FXML
    private HBox sikret;
    @FXML
    private TextArea descriptionLabel;

    @FXML
    private VBox all;

    public void setApp(JavaFxApplication mainApp){
        this.app = mainApp;
    }

    @FXML
    public void backClick(){
        this.app.showMainView();
    }

    @FXML
    private void logOutClick() throws IOException {
        this.app.showLoginScreen();
    }

    @FXML
    private void editClick(){
        if (editFaktor) {
            editFaktor = false;
            numberLabel.setDisable(false);
            emileLabel.setDisable(false);
            citeLabel.setDisable(false);
            descriptionLabel.setDisable(false);

            Button save = new Button();
            save.setText("Сохранить");
            save.setStyle("-fx-background-color: #1E90FF;");
            save.setMnemonicParsing(false);
            save.setOnAction(actionEvent -> saveClick());
            main.getChildren().add(main.getChildren().size() - 1, save);
        }
    }

    private void saveClick(){
        try {
            HttpClient client = HttpClient.newHttpClient();

            String authToken = AuthManager.getAuthToken();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("city", citeLabel.getText());
            jsonBody.put("phone_number", numberLabel.getText());
            jsonBody.put("email", emileLabel.getText());
            jsonBody.put("description", descriptionLabel.getText());
            String jsonString = jsonBody.toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8082/update/user"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + authToken)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonString, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                editFaktor = true;
                numberLabel.setDisable(true);
                emileLabel.setDisable(true);
                citeLabel.setDisable(true);
                descriptionLabel.setDisable(true);
                main.getChildren().remove(main.getChildren().size() - 2);
            }
            else {
                numberLabel.setText("");
                emileLabel.setText("");
                citeLabel.setText("");
                descriptionLabel.setText("");
            }
        } catch (JSONException | InterruptedException | java.io.IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }


    }

    @FXML
    public void nextClick() throws Exception {
        if (size == 5){
            page++;
        }
        else {
            page = 0;
            size = 5;
        }
        String authToken = AuthManager.getAuthToken();
        HttpClient client = HttpClient.newHttpClient();
        all.getChildren().remove(0);
        if (is_employee){
            String url = "http://localhost:8082/resume/private" + "?page=" + page + "&size=" + size;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + authToken)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(response.body());
            JSONArray contentArray = jsonObject.getJSONArray("content");
            size = jsonObject.getInt("totalElements");
            ArrayList<Resume> privetResumes = new ArrayList<>();
            String name = app.getUserField("name", authToken);
            String sity = app.getUserField("city", authToken);
            String contacts = app.getUserField("phone_number", authToken) + app.getUserField("email", authToken);
            System.out.println(contentArray);
            for (int i = 0; i < size; i++){
                JSONObject ounResume = contentArray.getJSONObject(i);
                privetResumes.add(new Resume(ounResume.getString("id"), name, ounResume.getString("position"),
                        ounResume.getString("workYears"), ounResume.getString("expectedSalary"),
                        ounResume.getString("skills"),  ounResume.getString("aboutMe"),
                        ounResume.getString("additionalSkills"), sity, contacts));
            }
            inetR(privetResumes);
        }
        else {
            String url = "http://localhost:8082/vacansy/private" + "?page=" + page + "&size=" + size;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + authToken)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(response.body());
            JSONArray contentArray = jsonObject.getJSONArray("content");
            size = jsonObject.getInt("totalElements");
            ArrayList<Vacancy> privetVacancy = new ArrayList<>();
            String owner = app.getUserField("name", authToken);
            String description = app.getUserField("description", authToken);
            String contacts = app.getUserField("phone_number", authToken) + app.getUserField("email", authToken);
            System.out.println(contentArray);
            for (int i = 0; i < size; i++){
                JSONObject ounResume = contentArray.getJSONObject(i);
                privetVacancy.add(new Vacancy(ounResume.getString("id"),
                        ounResume.getString("position"),
                        ounResume.getString("workExperienceYears"),
                        ounResume.getString("salary"),
                        ounResume.getString("requirements"),
                        ounResume.getString("whatWeOffer"),
                        ounResume.getString("additionalSkills"),
                        ounResume.getString("employmentType"),
                        ounResume.getString("schedule"),
                        ounResume.getString("workingHours"),
                        ounResume.getString("address"),
                        owner,
                        description,
                        contacts));
            }
            inetV(privetVacancy);
        }

    }

    public void roolFaktor(Boolean is_employee) throws Exception {
        String authToken = AuthManager.getAuthToken();
        HttpClient client = HttpClient.newHttpClient();
        Hyperlink ovners = new Hyperlink();
        ovners.setTextFill(Paint.valueOf("DODGERBLUE"));
        ovners.setPadding(new Insets(5, 10, 5, 10));
        ovners.setFont(new Font(13.0));
        this.is_employee = is_employee;
        if (is_employee){
            sikret.setVisible(false);

            String url = "http://localhost:8082/resume/private" + "?page=" + page + "&size=" + size;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + authToken)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(response.body());
            JSONArray contentArray = jsonObject.getJSONArray("content");
            size = jsonObject.getInt("totalElements");
            ArrayList<Resume> privetResumes = new ArrayList<>();
            String name = app.getUserField("name", authToken);
            String sity = app.getUserField("city", authToken);
            String contacts = app.getUserField("phone_number", authToken) + app.getUserField("email", authToken);
            System.out.println(contentArray);
            for (int i = 0; i < size; i++){
                JSONObject ounResume = contentArray.getJSONObject(i);
                privetResumes.add(new Resume(ounResume.getString("id"), name, ounResume.getString("position"),
                        ounResume.getString("workYears"), ounResume.getString("expectedSalary"),
                        ounResume.getString("skills"),  ounResume.getString("aboutMe"),
                        ounResume.getString("additionalSkills"), sity, contacts));
            }
            inetR(privetResumes);
        }
        else {
            String url = "http://localhost:8082/vacansy/private" + "?page=" + page + "&size=" + size;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + authToken)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(response.body());
            JSONArray contentArray = jsonObject.getJSONArray("content");
            size = jsonObject.getInt("totalElements");
            ArrayList<Vacancy> privetVacancy = new ArrayList<>();
            String owner = app.getUserField("name", authToken);
            String description = app.getUserField("description", authToken);
            String contacts = app.getUserField("phone_number", authToken) + app.getUserField("email", authToken);
            System.out.println(contentArray);
            for (int i = 0; i < size; i++){
                JSONObject ounResume = contentArray.getJSONObject(i);
                privetVacancy.add(new Vacancy(ounResume.getString("id"),
                        ounResume.getString("position"),
                        ounResume.getString("workExperienceYears"),
                        ounResume.getString("salary"),
                        ounResume.getString("requirements"),
                        ounResume.getString("whatWeOffer"),
                        ounResume.getString("additionalSkills"),
                        ounResume.getString("employmentType"),
                        ounResume.getString("schedule"),
                        ounResume.getString("workingHours"),
                        ounResume.getString("address"),
                        owner,
                        description,
                        contacts));
            }
            inetV(privetVacancy);
        }
        rightList.getChildren().add(ovners);
    }

    @FXML
    private void newClick() throws IOException {
        if (is_employee){
            app.showCreateResune();
        }
        else {
            app.showCreateVacancy();
        }
    }

    public void inetR(ArrayList<Resume> items){
        ResumeVBox resumeVBox = new ResumeVBox("me");
        resumeVBox.addApp(app);
        for (Resume item: items) {
            resumeVBox.addResume(item);
        }
        all.getChildren().add(scrollPane);
        scrollPane.setStyle("-fx-border-width: 0;\n" + "-fx-padding: 0;");
        scrollPane.setFitToWidth(true);
        resumeVBox.populateVBox();
        scrollPane.setContent(resumeVBox.resume);
    }

    public void inetV(ArrayList<Vacancy> items){
        VacancyVBox vacancyVBox = new VacancyVBox("my");
        vacancyVBox.addApp(app);
        for (Vacancy item: items) {
            vacancyVBox.addVacancy(item);
        }
        all.getChildren().add(scrollPane);
        scrollPane.setStyle("-fx-border-width: 0;\n" + "-fx-padding: 0;");
        scrollPane.setFitToWidth(true);
        vacancyVBox.populateVBox();
        scrollPane.setContent(vacancyVBox.vacancy);

    }

}
