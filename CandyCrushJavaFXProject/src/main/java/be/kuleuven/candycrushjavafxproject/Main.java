package be.kuleuven.candycrushjavafxproject;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.jar.Attributes;

public class Main extends Application {
    public Button StartButton;
    public TextField NameTextBox;
    private String PlayerName;

    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 632, 435);

        this.stage = stage;

        this.stage.setScene(scene);
        this.stage.setResizable(false);

        this.stage.show();
    }

    public void StartButtonHandle() throws IOException {
        if (!Objects.equals(NameTextBox.getText(), ""))
        {
            PlayerName = NameTextBox.getText();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}