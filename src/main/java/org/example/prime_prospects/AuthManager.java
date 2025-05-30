package org.example.prime_prospects;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.json.JSONException;


public class AuthManager {

    private static final String API_URL = "http://localhost:8082";
    private static String authToken;

    public static boolean login(String username, String password) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("usernameOrEmail", username);
            jsonBody.put("password", password);

            String jsonString = jsonBody.toString();
            System.out.println("JSON Payload: " + jsonString);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                authToken = response.body();
                System.out.println(authToken);
                return true;
            } else {
                System.err.println("Login failed: " + response.statusCode());
                return false;
            }
        } catch (JSONException | InterruptedException | java.net.http.HttpTimeoutException e) {
        System.err.println("Error during login: " + e.getMessage());
        return false;
    } catch (java.io.IOException e) {
        System.err.println("IO Error during login: " + e.getMessage());
        return false;
    }

}

    public static String getAuthToken() {
        return authToken;
    }
}


