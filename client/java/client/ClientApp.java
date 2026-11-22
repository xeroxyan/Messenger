package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class ClientApp extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("Клиент мессенджера работает!");
        stage.setScene(new Scene(new StackPane(label), 400, 300));
        stage.setTitle("Messenger");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}