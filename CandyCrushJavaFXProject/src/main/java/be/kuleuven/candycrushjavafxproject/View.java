package be.kuleuven.candycrushjavafxproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class View extends Application {
    @FXML
    public Button StartButton;
    @FXML
    public TextField NameTextBox;
    @FXML
    public Label NameLabel;
    public String PlayerName;

    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        // Create start window
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("MainScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 632, 435);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("test");
        stage.show();
    }

    public void StartButtonHandle(ActionEvent event) throws IOException {
        //Handle start button if there is text
        if (!Objects.equals(NameTextBox.getText(), ""))
        {
            //Read name
            PlayerName = NameTextBox.getText();

            //Read stage, otherwise its null
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            //Create game window
            CreateWindow();;
        }
    }

    public void CreateWindow() throws IOException {
        //Loads new FXML thats the game window
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("GameScreen.fxml"));
        fxmlLoader.setController(this); // Set controller to current instance

        Scene scene = new Scene(fxmlLoader.load(), 632, 435);

        stage.setScene(scene);
        stage.setResizable(false);

        updateNameLabel();

        stage.show();
    }

    private void updateNameLabel() {
        //Update label in game window to player name
        NameLabel.setText(PlayerName);
    }

    public static void main(String[] args) {
        launch();
    }
}
