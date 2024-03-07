package be.kuleuven.candycrushjavafxproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
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
    @FXML
    public Button StartButton;
    @FXML
    public TextField NameTextBox;
    private String PlayerName;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 632, 435);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("test");
        stage.show();
    }

    public void StartButtonHandle(ActionEvent event) throws IOException {
        if (!Objects.equals(NameTextBox.getText(), ""))
        {
            PlayerName = NameTextBox.getText();
        }

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        CandyCrushGameWindow gameWindow = new CandyCrushGameWindow(stage);
        gameWindow.CreateWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}