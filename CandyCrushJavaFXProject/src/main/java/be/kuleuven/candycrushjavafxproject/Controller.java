package be.kuleuven.candycrushjavafxproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Controller extends Application {

    /////////////////////////
    //FXML Widget Variables//
    /////////////////////////

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

    @FXML
    public Label ScoreLbl;

    ////////////////////
    //Member Variables//
    ////////////////////

    private Stage stage;
    private Model model;
    private View view;

    ///////////////
    //Constructor//
    ///////////////

    public Controller()
    {
        model = new Model(4,4);
        view = new View();
    }

    ////////////////////
    //JavaFX Functions//
    ////////////////////

    @Override
    public void start(Stage stage) throws IOException {
        view.CreateLoginWindow(stage);
    }

    public static void main(String[] args) {
        launch();
    }

    ////////////////////
    //Member Functions//
    ////////////////////

    public void CreateWindow() throws IOException {
        view.CreateGameWindow(stage, this);

        UpdateNameLabel();
        LoadGridLabelIds();

        model.RandomizeGrid(LabelGridList);

        stage.show();
    }

    public void LoadGridLabelIds() {
        LabelGridList.add(lbl1x1); LabelGridList.add(lbl1x2); LabelGridList.add(lbl1x3); LabelGridList.add(lbl1x4);
        LabelGridList.add(lbl2x1); LabelGridList.add(lbl2x2); LabelGridList.add(lbl2x3); LabelGridList.add(lbl2x4);
        LabelGridList.add(lbl3x1); LabelGridList.add(lbl3x2); LabelGridList.add(lbl3x3); LabelGridList.add(lbl3x4);
        LabelGridList.add(lbl4x1); LabelGridList.add(lbl4x2); LabelGridList.add(lbl4x3); LabelGridList.add(lbl4x4);
    }

    public void UpdateNameLabel() {
        //Update label in game window to player name
        NameLabel.setText(model.getPlayerName());
    }

    //////////////////
    //Event Handlers//
    //////////////////

    public void StartButtonHandle(ActionEvent event) throws IOException {
        if (!Objects.equals(NameTextBox.getText(), ""))
        {
            //Read name
            model.setPlayerName(NameTextBox.getText());

            //Read stage, otherwise its null
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            //Create game window
            CreateWindow();
        }
    }

    public void RandomizeButtonHandler(ActionEvent event) throws IOException{
        model.RandomizeGrid(LabelGridList);
    }

    public void HandleLabelClick(MouseEvent event) {

        model.CombinationMadeHandler(LabelGridList, event);

        ScoreLbl.setText("Score: " + model.getUserScore());
    }

    public void RestartButtonHandler() throws IOException {
        view.CreateLoginWindow(stage);
    }
}
