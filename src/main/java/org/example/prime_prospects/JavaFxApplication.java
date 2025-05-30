package org.example.prime_prospects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.prime_prospects.contrallers.*;
import org.example.prime_prospects.dataFormats.Response;
import org.example.prime_prospects.dataFormats.Resume;
import org.example.prime_prospects.dataFormats.Vacancy;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class JavaFxApplication extends Application {
    private static final String API_URL = "http://localhost:8082";

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.showLoginScreen();
    }

    public void showLoginScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.example.prime_prospects/authorization/pp-login-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();


        LoginController loginController = loader.getController();
        loginController.setMainViewCallback(this::showMainView);
        loginController.setApp(this);
    }

    public void showRegisterScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.example.prime_prospects/authorization/pp-register-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register");
        primaryStage.show();

        RegisterContraller registerContraller = loader.getController();
        registerContraller.setApp(this);
    }

    public void showMainView()  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.example.prime_prospects/pp-main-view.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("PP.ru");
            primaryStage.show();

            MainContraller mainContraller = loader.getController();
            mainContraller.setApp(this);

            boolean is_employee = getUserField("is_employee", AuthManager.getAuthToken()).equals("true");
            mainContraller.roolFaktor(is_employee);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        catch (java.lang.Exception lang_e){
            System.err.println(lang_e.getMessage());
        }

    }

    public void showResumeSerchView() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.example.prime_prospects/pp-search-for-companies-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("PP.ru resumes");
            primaryStage.show();

            SerchResumeController serchResumeController = loader.getController();
            serchResumeController.setApp(this);

    }

    public void showArchiveView() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.example.prime_prospects/pp-archive-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PP.ru resumes");
        primaryStage.show();

        ArchiveController archiveController = loader.getController();
        archiveController.setApp(this);

        ArrayList<Response> test = new ArrayList<>();
        test.add(new Response("1", "Desktop Разрабочик C++", "Мария Несоевая", "false", "-"));
        test.add(new Response("1", "WEB Разрабочик C++", "Дритмий Пепегин", "false", "-"));

        archiveController.inet(test);
    }

    public void showChoiceView(String atrebut, long id) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.example.prime_prospects/side/mini-choice.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage chldrenStage = new Stage();
        chldrenStage.setTitle("choice");
        chldrenStage.setScene(scene);
        chldrenStage.initModality(Modality.WINDOW_MODAL);
        chldrenStage.initOwner(primaryStage);
        ChoiceController choiceController = loader.getController();
        try {
            choiceController.inet(atrebut, id);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        chldrenStage.showAndWait();
    }

    public void showProfileView()  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.example.prime_prospects/pp-profile-view.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("PP.ru");
            primaryStage.show();

            ProfileController profileController = loader.getController();
            profileController.setApp(this);

            profileController.setInfo(
                    getUserField("name", AuthManager.getAuthToken()),
                    getUserField("birth_date", AuthManager.getAuthToken()),
                    getUserField("email", AuthManager.getAuthToken()),
                    getUserField("phone_number", AuthManager.getAuthToken()),
                    getUserField("city", AuthManager.getAuthToken()),
                    getUserField("description", AuthManager.getAuthToken())
            );

            boolean is_employee = getUserField("is_employee", AuthManager.getAuthToken()).equals("true");
            profileController.roolFaktor(is_employee);

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        catch (java.lang.Exception lang_e){
            System.err.println(lang_e.getMessage());
        }

    }

    public void showVacancy(Vacancy item) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.example.prime_prospects/side/pp-vacancy.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage chldrenStage = new Stage();
        chldrenStage.setTitle("Вакансия");
        chldrenStage.setScene(scene);
        chldrenStage.initModality(Modality.WINDOW_MODAL);
        chldrenStage.initOwner(primaryStage);

        VacancyController vacancyController = loader.getController();
        vacancyController.setApp(this);
        vacancyController.setVacancy(item);

        chldrenStage.showAndWait();
    }

    public void showCreateVacancy() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.example.prime_prospects/side/pp-create-vacancy.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Новая вакансия");
        primaryStage.show();

        CreateVacansyController createVacansyController = loader.getController();
        createVacansyController.setApp(this);
    }

    public void showResune(Resume item) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.example.prime_prospects/side/pp-resume.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage chldrenStage = new Stage();
        chldrenStage.setTitle("Резюмэ");
        chldrenStage.setScene(scene);
        chldrenStage.initModality(Modality.WINDOW_MODAL);
        chldrenStage.initOwner(primaryStage);

        ResumeController resumeController = loader.getController();
        resumeController.setApp(this);
        resumeController.setResume(item);

        chldrenStage.showAndWait();
    }

    public void showCreateResune() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org.example.prime_prospects/side/pp-create-resume.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Новое Резюмэ");
        primaryStage.show();

        CreateResumeController createResumeController = loader.getController();
        createResumeController.setApp(this);
    }

    public String getUserField(String field, String authToken) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/user/field/" + field))
                .header("Authorization", "Bearer " + authToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new Exception("Failed to get user field: " + response.body());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

