package org.example.prime_prospects.contrallers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.example.prime_prospects.JavaFxApplication;
import org.example.prime_prospects.children.ResponseVBox;
import org.example.prime_prospects.dataFormats.Response;

import java.util.ArrayList;

public class ArchiveController {
    private JavaFxApplication app;
    public void setApp(JavaFxApplication app){
        this.app = app;
    }

    private ResponseVBox responseVBox = new ResponseVBox();
    private ScrollPane scrollPane = new ScrollPane();
    @FXML
    private VBox mainVB;

    public void inet(ArrayList<Response> items){
        for (Response item: items) {
            responseVBox.addResume(item);
        }
        scrollPane.setStyle("-fx-border-width: 0;\n" + "-fx-padding: 0;");
        scrollPane.setFitToWidth(true);
        responseVBox.populateVBox();
        scrollPane.setContent(responseVBox.finalList);
        mainVB.getChildren().add(scrollPane);
    }

    @FXML
    public void backClick(){
        this.app.showMainView();
    }

}
