package com.mycompany.mavenproject11;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * JavaFX App
 */
public class App extends Application {

   @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Post News App");

        // Create a TextField for entering short text with increased height
        TextField newsTextField = new TextField();
        newsTextField.setPromptText("Enter your short text here");
        newsTextField.setPrefHeight(100); // Set the preferred height

        // Create a "Post News" button with customized color
        Button postNewsButton = new Button("Post News");
        postNewsButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");

        // Define the action to be performed when the button is clicked
        postNewsButton.setOnAction(event -> {
            String newsText = newsTextField.getText();
            if (!newsText.isEmpty()) {
                System.out.println("News Posted: " + newsText);
                sendNewsToServer(newsText);
            } else {
                System.out.println("Please enter a short text before posting news.");
            }
        });

        // Create a VBox layout to stack components vertically
        VBox root = new VBox(10); // Set the spacing between components
        root.getChildren().addAll(newsTextField, postNewsButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Set the scene with a specified width and height
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void sendNewsToServer(String newsText) {
        try (Socket socket = new Socket("localhost", 5555)) {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(newsText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}