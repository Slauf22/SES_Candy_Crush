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
import java.util.ArrayList;
import java.util.Objects;

public class Controller extends Application {
    @FXML
    public Button StartButton;
    @FXML
    public TextField NameTextBox;
    @FXML
    public Label NameLabel;
    @FXML
    private Label   lbl1x1,lbl1x2,lbl1x3,lbl1x4,
                    lbl2x1,lbl2x2,lbl2x3,lbl2x4,
                    lbl3x1, lbl3x2, lbl3x3, lbl3x4,
                    lbl4x1, lbl4x2, lbl4x3, lbl4x4;
    @FXML
    public ArrayList<Label> LabelGridList = new ArrayList<>();
    public String PlayerName;
    private Stage stage;
    private Model model;

    @Override
    public void start(Stage stage) throws IOException {
        // Create start window
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("MainScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 632, 435);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void StartButtonHandle(ActionEvent event) throws IOException {
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

    public void RandomizeButtonHandler(ActionEvent event) throws IOException{
        model = new Model();
        model.RandomizeGrid(LabelGridList,4*4);
    }

    public void CreateWindow() throws IOException {
        //Loads new FXML thats the game window
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("GameScreen.fxml"));
        fxmlLoader.setController(this);

        Scene scene = new Scene(fxmlLoader.load(), 632, 435);

        stage.setScene(scene);
        stage.setResizable(false);

        updateNameLabel();
        loadGridLabelIds();

        model = new Model();
        model.RandomizeGrid(LabelGridList,4*4);

        stage.show();
    }

    public void loadGridLabelIds() {
        LabelGridList.add(lbl1x1); LabelGridList.add(lbl1x2); LabelGridList.add(lbl1x3); LabelGridList.add(lbl1x4);
        LabelGridList.add(lbl2x1); LabelGridList.add(lbl2x2); LabelGridList.add(lbl2x3); LabelGridList.add(lbl2x4);
        LabelGridList.add(lbl3x1); LabelGridList.add(lbl3x2); LabelGridList.add(lbl3x3); LabelGridList.add(lbl3x4);
        LabelGridList.add(lbl4x1); LabelGridList.add(lbl4x2); LabelGridList.add(lbl4x3); LabelGridList.add(lbl4x4);
    }

    public void updateNameLabel() {
        //Update label in game window to player name
        NameLabel.setText(PlayerName);
    }

    public static void main(String[] args) {
        launch();
    }
}
