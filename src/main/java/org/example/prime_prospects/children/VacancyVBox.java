package org.example.prime_prospects.children;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.prime_prospects.JavaFxApplication;
import org.example.prime_prospects.dataFormats.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class VacancyVBox {
    private JavaFxApplication app;
    public VBox vacancy = new VBox();
    private String for_;

    private List<Vacancy> vacancyItems = new ArrayList<>();


    public VacancyVBox(String for_){
       this.for_ = for_;
    }


    public void addVacancy(Vacancy el) {
        vacancyItems.add(el);
    }

    public void addApp(JavaFxApplication app){
        this.app = app;
    }

    public void populateVBox() {
        vacancy.setSpacing(5);
        for (Vacancy item : vacancyItems) {
            VBox container = createVacancyContainer(item.id, item);
            vacancy.getChildren().add(container);
        }
    }

    private VBox createVacancyContainer(Long id, Vacancy vacancy) {
        VBox container = new VBox();
        container.setPrefHeight(30);
        container.setStyle("-fx-background-color: #ede2e1;");
        container.setSpacing(10);
        ArrayList<String> info = vacancy.getStartInfo();
        Label titleLabel = new Label(info.get(0));
        titleLabel.setFont(new Font(20.0));
        Label companyLabel = new Label("Компания: " + info.get(1));
        Label expireansLabel = new Label("Опыт: " + info.get(2));
        Label salaryLabel = new Label("Зарплата: " + info.get(3));

        Button openButton = new Button("Подробнее");
        openButton.setOnAction(event -> openClick(vacancy));

        HBox hBox = new HBox();
        hBox.getChildren().add(openButton);
        if (for_.equals("serch")){
            hBox.getChildren().add(creatPikBt(id));}
        else {
            hBox.getChildren().add(creatDelBt(id));
        }

        container.getChildren().addAll(titleLabel, companyLabel, salaryLabel, expireansLabel, hBox);
        return container;
    }

    public void openClick(Vacancy vacancy){
        try {
            app.showVacancy(vacancy);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Button creatDelBt(long id){
        Button delBt = new Button("Удалить");
        delBt.setOnAction(actionEvent -> {
        });
        return  delBt;
    }

    private Button creatPikBt(long id){
        Button pikBt = new Button("Откликнуться");
        pikBt.setOnAction(actionEvent -> {
            try {
                app.showChoiceView("resume", id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return  pikBt;
    }


}
