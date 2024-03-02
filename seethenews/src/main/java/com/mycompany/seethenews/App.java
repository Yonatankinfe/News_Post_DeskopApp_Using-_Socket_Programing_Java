
package com.mycompany.seethenews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * JavaFX App
 */
public class App extends Application {

 private ListView<String> newsListView;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("View News App");

        // Create a "View News" button
        Button viewNewsButton = new Button("View News");
        viewNewsButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");

        // Create a ListView with some sample data
        newsListView = new ListView<>(FXCollections.observableArrayList());

        // Define the action to be performed when the "View News" button is clicked
        viewNewsButton.setOnAction(event -> showListView());

        // Create a VBox layout to stack components vertically
        VBox root = new VBox(10); // Set the spacing between components
        root.getChildren().addAll(viewNewsButton, newsListView);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Set the scene with a specified width and height
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Start the server to receive news
        startServer();
    }

    private void showListView() {
        // Fetch news from the server (if any)
    }

    private void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(5555)) {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    handleClientSocket(clientSocket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClientSocket(Socket clientSocket) {
        new Thread(() -> {
            try (Scanner scanner = new Scanner(clientSocket.getInputStream())) {
                if (scanner.hasNextLine()) {
                    String newsText = scanner.nextLine();
                    System.out.println("Received News: " + newsText);
                    updateNewsListView(newsText);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateNewsListView(String newsText) {
        // Update the ListView with the received news
        newsListView.getItems().add(newsText);
    }

    public static void main(String[] args) {
        launch();
    }

}