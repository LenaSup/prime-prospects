package org.example.prime_prospects.contrallers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.prime_prospects.AuthManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ChoiceController {
    private Item it = new Item();
    private ScrollPane scrollPane = new ScrollPane();
    private Long to_id;
    @FXML
    private VBox mainVB;

    public void inet(String atrebut, long id) throws IOException, InterruptedException {
        String authToken = AuthManager.getAuthToken();
        HttpClient client = HttpClient.newHttpClient();

            String url = "http://localhost:8082/" + atrebut +"/private/short";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + authToken)
                    .GET()
                    .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObject = new JSONObject(response.body());
        JSONArray contentArray = jsonObject.getJSONArray("content");
        System.out.println(contentArray);
        int size = jsonObject.getInt("size");
        for (int i = 0; i < size; i++) {
            JSONObject el = new JSONObject(contentArray.get(i).toString());
            it.add(el.getString("position"), el.getLong("id"));
        }
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-border-width: 0; -fx-padding: 0;");
        scrollPane.setContent(it.get());
        mainVB.getChildren().add(scrollPane);
    }


    private class Item{
        private ArrayList<HBox> list = new ArrayList<>();


        public void add(String name, Long id){
            HBox item = new HBox();
            item.setPrefHeight(10);
            item.setStyle("-fx-background-color: e3e3e3;");

            Label titleLabel = new Label(name);
            titleLabel.setFont(new Font(13.0));

            Button openButton = new Button("+");
            openButton.setStyle("-fx-border-width: 0; -fx-padding: 0; -fx-cursor: hand;");
            openButton.setAlignment(Pos.BASELINE_RIGHT);
            openButton.setOnAction(event -> openClick(id));

            item.getChildren().addAll(titleLabel, openButton);
            list.add(item);
        }

        public VBox get(){
            VBox bax = new VBox();
            bax.setSpacing(5);
            for (HBox item : list) {
                bax.getChildren().add(item);
            }
            return bax;
        }

        public void openClick(Long id) {
            HttpClient client = HttpClient.newHttpClient();
            String authToken = AuthManager.getAuthToken();
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("in_id", to_id);
            jsonBody.put("out_id", id);

            String jsonString = jsonBody.toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8082/response/create"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + authToken)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonString, StandardCharsets.UTF_8))
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
