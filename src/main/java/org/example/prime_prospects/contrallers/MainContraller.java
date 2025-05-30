package org.example.prime_prospects.contrallers;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.example.prime_prospects.JavaFxApplication;
import org.example.prime_prospects.children.VacancyVBox;
import org.example.prime_prospects.dataFormats.Vacancy;

import java.io.IOException;

public class MainContraller {
    private JavaFxApplication app;
    private VacancyVBox showVacancy = new VacancyVBox("serch");
    private ScrollPane scrollPane = new ScrollPane();

    public void setApp(JavaFxApplication mainApp){
        this.app = mainApp;
        scrollPane.setStyle("-fx-border-width: 0;\n" + "-fx-padding: 0;");
        scrollPane.setFitToWidth(true);
        vacancy.getChildren().add(scrollPane);

        showVacancy.addApp(app);
    }


    @FXML
    private void logOutClick() throws IOException {
        this.app.showLoginScreen();
    }

    @FXML
    private void profileClick() throws IOException {
        this.app.showProfileView();
    }

    public void roolFaktor(boolean is_employee) {
        if (!is_employee){
            Hyperlink leftLink = new Hyperlink();
            leftLink.setText("Найти сотрудника");
            leftLink.setTextFill(Paint.valueOf("DODGERBLUE"));
            leftLink.setPadding(new Insets(5, 10, 5, 10));
            leftLink.setFont(new Font(13.0));
            leftLink.setOnAction(event -> {
                try {
                    serchEmploerClick();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            leftList.getChildren().add(leftLink);
        }
    }

    public void serchEmploerClick() throws IOException {
        app.showResumeSerchView();
    }

    @FXML
    private TextField mainSearch;
    @FXML
    private void searchClick(){
        for (int i = 0; i<5; i++){
            Vacancy el = new Vacancy("1", "1", "1", "1", "1",
                    "1", "1", "1", "1",
                    "1", "1", "1", "1", "1");
            showVacancy.addVacancy(el);
        }
        showVacancy.populateVBox();
        scrollPane.setContent(showVacancy.vacancy);
    }

    @FXML
    private VBox filters;
    @FXML
    private TextField selariFilter;
    @FXML
    private TextField levalFilter;
    @FXML
    private TextField syteFilter;
    @FXML
    private Slider workDayFilter;
    @FXML
    private CheckBox checkFilter1;
    @FXML
    private CheckBox checkFilter2;
    @FXML
    private ChoiceBox WorkWickFilter;
    @FXML
    private void filtersClick() {
        Vacancy el1 = new Vacancy("1", "С++ разработчик", "1", "12000", "1",
                "1", "1", "1", "1",
                "1", "1", "Хебци и галера", "1", "1");
        Vacancy el2 = new Vacancy("1", "С/C++ developer", "6", "1", "1",
                "1", "1", "1", "123456",
                "1", "1", "Хугол", "1", "1");
        Vacancy el3 = new Vacancy("1", "WEB Разрабочик C++", "3", "60600.6",
                "1",
                "1", "1", "1", "1",
                "1", "1", "NewIT", "1", "1");
        Vacancy el4 = new Vacancy("1", "Desktop Разрабочик C++", "3", "120000",
                "Хорошее знание C++ 17",
                "Перспективы роста, комфортный офис, кофепоинт", "Stl",
                "Трудовой догавор", "5/2",
                "8", "Париж, ул.Краснадарская, ст.31, к.2", "NewIT",
                "Высокие технологии и успех", "+7 878 787 7878 newITcompany@gmail.com");

        showVacancy.addVacancy(el4);
        showVacancy.addVacancy(el3);
        showVacancy.addVacancy(el2);
        showVacancy.addVacancy(el1);
        showVacancy.populateVBox();
        scrollPane.setContent(showVacancy.vacancy);
    }

    @FXML
    private VBox vacancy;

    @FXML
    private VBox leftList;


}
