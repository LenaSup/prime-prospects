package org.example.prime_prospects.children;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.prime_prospects.JavaFxApplication;
import org.example.prime_prospects.dataFormats.Resume;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumeVBox {
    private JavaFxApplication app;
    public VBox resume = new VBox();

    private String for_;

    private List<Resume> resumes = new ArrayList<>();

    public void addApp(JavaFxApplication app){
        this.app = app;
    }

    public void addResume(Resume el) {
        resumes.add(el);
    }

    public ResumeVBox(String for_){
        this.for_ = for_;
    }

    public void populateVBox() {
        resume.setSpacing(5);
        for (Resume item : resumes) {
            VBox container = createVacancyContainer(item.id, item);
            resume.getChildren().add(container);
        }
    }

    private VBox createVacancyContainer(Long id, Resume resume) {
        VBox container = new VBox();
        container.setPrefHeight(30);
        container.setStyle("-fx-background-color: #e6eaed;");
        container.setSpacing(10);
        ArrayList<String> info = resume.getStartInfo();
        Label titleLabel = new Label(info.get(0));
        titleLabel.setFont(new Font(20.0));
        Label expireansLabel = new Label("Опыт: " + info.get(1));
        Label salaryLabel = new Label("Зарплата: " + info.get(2));

        Button openButton = new Button("Подробнее");

        openButton.setOnAction(event -> openClick(resume));
        HBox hBox = new HBox();
        hBox.getChildren().add(openButton);
        if (for_.equals("serch")){
            hBox.getChildren().add(creatPikBt(id));}
        else {
            hBox.getChildren().add(creatDelBt(id));
        }
        container.getChildren().addAll(titleLabel, salaryLabel, expireansLabel, hBox);
        return container;
    }

    public void openClick(Resume resume) {
        try {
            app.showResune(resume);
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
                app.showChoiceView("vacansy", id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return  pikBt;
    }


}
