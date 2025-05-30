package org.example.prime_prospects.contrallers;

import javafx.fxml.FXML;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.prime_prospects.AuthManager;
import org.example.prime_prospects.JavaFxApplication;
import org.example.prime_prospects.children.ResumeVBox;
import org.example.prime_prospects.dataFormats.Resume;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class SerchResumeController {
    private JavaFxApplication app;
    private ResumeVBox showResume = new ResumeVBox("serch");
    private ScrollPane scrollPane = new ScrollPane();
    private int size = 3;
    private int page;
    private boolean filterOrserch;

    public void setApp(JavaFxApplication mainApp){
        this.app = mainApp;
        scrollPane.setStyle("-fx-border-width: 0;\n" + "-fx-padding: 0;");
        scrollPane.setFitToWidth(true);
        resumeList.getChildren().add(scrollPane);
        showResume.addApp(app);
    }


    @FXML
    private TextField mainSearch;
    @FXML
    private void searchClick(){
        page = 0;
        filterOrserch = false;
        serch();
    }

    @FXML
    private void lastClick(){
        if (page > 0){
            page--;
            showResume = new ResumeVBox("serch");
            if (filterOrserch){
                filter();
            }
            else {
                serch();
            }

        }
    }

    @FXML
    private void nextClick(){
        if (size == 3){
            page++;
            showResume = new ResumeVBox("serch");
            if (filterOrserch){
                filter();
            }
            else {
                serch();
            }
        }
    }

    @FXML
    private void filtersClick() {
        filterOrserch = true;
        page = 0 ;
        filter();
    }

    @FXML
    private VBox resumeList;
    @FXML
    private TextField mainSerch;
    @FXML
    private VBox filters;
    @FXML
    private TextField syteFilter;
    @FXML
    private TextField selariFilter;
    @FXML
    private TextField levalFilter;
    @FXML
    private TextField skilsFilter;

    @FXML
    private void logOutClick() throws IOException {
        this.app.showLoginScreen();
    }

    @FXML
    public void backClick(){
        this.app.showMainView();
    }

    private void filter(){
        filterOrserch = true;
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("position", mainSerch.getText());
        jsonBody.put("skills", skilsFilter.getText());
        jsonBody.put("cite", syteFilter.getText());
        if (levalFilter.getText().equals("")){
            jsonBody.put("workYears", "0");
        }
        else {
            jsonBody.put("workYears", levalFilter.getText());
        }
        if (  selariFilter.getText().equals("")){
            jsonBody.put("expectedSalary", "1000000");
        }
        else {
            jsonBody.put("expectedSalary", selariFilter.getText());
        }
        System.out.println(jsonBody);
        HttpClient client = HttpClient.newHttpClient();
        size = 3;
        String url = "http://localhost:8082/search/filter/resume" + "?page=" + page + "&size=" + size;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + AuthManager.getAuthToken())
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString(), StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = new JSONObject(response.body());
        size = jsonObject.getInt("totalElements");
        JSONArray content = jsonObject.getJSONArray("content");
        System.out.println(content);
        String name, sity, contacts;
        try {
            name = app.getUserField("name", AuthManager.getAuthToken());
            sity = app.getUserField("city", AuthManager.getAuthToken());
            contacts = app.getUserField("phone_number", AuthManager.getAuthToken()) +
                    app.getUserField("email", AuthManager.getAuthToken());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i<size; i++){
            JSONObject ounResume = content.getJSONObject(i);
            Resume el = new Resume(ounResume.getString("id"), name, ounResume.getString("position"),
                    ounResume.getString("workYears"), ounResume.getString("expectedSalary"),
                    ounResume.getString("skills"),  ounResume.getString("aboutMe"),
                    ounResume.getString("additionalSkills"), sity, contacts);
            showResume.addResume(el);
        }
        showResume.populateVBox();
        scrollPane.setContent(showResume.resume);
    }

    private void serch(){
        HttpClient client = HttpClient.newHttpClient();
        size = 3;
        String url = "http://localhost:8082/search/position/resume" +
                "?position=" + mainSerch.getText() + "&page=" + page + "&size=" + size;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + AuthManager.getAuthToken())
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = new JSONObject(response.body());
        size = jsonObject.getInt("totalElements");
        JSONArray content = jsonObject.getJSONArray("content");
        String name, sity, contacts;
        try {
            name = app.getUserField("name", AuthManager.getAuthToken());
            sity = app.getUserField("city", AuthManager.getAuthToken());
            contacts = app.getUserField("phone_number", AuthManager.getAuthToken()) +
                    app.getUserField("email", AuthManager.getAuthToken());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i<size; i++){
            JSONObject ounResume = content.getJSONObject(i);
            Resume el = new Resume(ounResume.getString("id"), name, ounResume.getString("position"),
                    ounResume.getString("workYears"), ounResume.getString("expectedSalary"),
                    ounResume.getString("skills"),  ounResume.getString("aboutMe"),
                    ounResume.getString("additionalSkills"), sity, contacts);
            showResume.addResume(el);
        }
        showResume.populateVBox();
        scrollPane.setContent(showResume.resume);
    }

}
