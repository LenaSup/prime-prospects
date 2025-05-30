package org.example.prime_prospects.children;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.prime_prospects.JavaFxApplication;
import org.example.prime_prospects.dataFormats.Response;

import java.util.ArrayList;
import java.util.List;

public class ResponseVBox {
    private JavaFxApplication app;
    public VBox finalList = new VBox();

    private List<Response> responsesList = new ArrayList<>();

    public void addResume(Response el) {
        responsesList.add(el);
    }

    public void addApp(JavaFxApplication app){
        this.app = app;
    }

    public void populateVBox() {
        finalList.setSpacing(10);
        for (Response item : responsesList) {
            VBox container = createVacancyContainer(item.id, item.getStartInfo(), item.applicationDate);
            finalList.getChildren().add(container);
        }
    }

    private VBox createVacancyContainer(Long id, ArrayList<String> info, boolean color) {
        VBox container = new VBox();
        container.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
        container.setPrefHeight(30);
        if (color){
            container.setStyle("-fx-background-color: #88c0f7;");
        }
        else {
            container.setStyle("-fx-background-color: #d5d5d5;");
        }
        container.setSpacing(10);

        Label titleLabel = new Label(info.get(0));
        titleLabel.setFont(new Font(20.0));
        Label expireansLabel = new Label(info.get(1));

        Button openButton = new Button("Подробнее");
        openButton.setOnAction(event -> openClick(id));

        Button yesButton = new Button("+");
        yesButton.setOnAction(event -> yesClick(id, container));
        Button noButton = new Button("-");
        noButton.setOnAction(event -> noClick(id, container));

        container.getChildren().addAll(titleLabel, expireansLabel, yesButton, noButton, openButton);
        return container;
    }

    public void yesClick(Long id, VBox it) {
        it.setStyle("-fx-background-color: #88c0f7;");
        it.getChildren().remove(2);
        it.getChildren().remove(2);
        System.out.println("Принято с ID: " + id);
    }

    public void noClick(Long id, VBox it) {
        finalList.getChildren().remove(it);
        System.out.println("Откланено с ID: " + id);
    }

    public void openClick(Long id) {
        System.out.println("Открыто с ID: " + id);
    }

}
