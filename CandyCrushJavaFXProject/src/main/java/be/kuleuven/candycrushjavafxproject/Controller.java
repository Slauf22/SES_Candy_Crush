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
    private final Model model;
    private final View view;

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

        SetLabelsToRandomized();

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
        SetLabelsToRandomized();
    }

    public void HandleLabelClick(MouseEvent event) {
        Label labelClicked = (Label) event.getSource();

        ArrayList<Integer> gridValues = new ArrayList<>();

        //Put the integer values of the grid labels into a integer list to send to checkneighbours function
        for (Label label : LabelGridList) {
            int intValue = Integer.parseInt(label.getText());
            gridValues.add(intValue);
        }

        //The id mentions the grid position: lblRxC. Get row and col from it
        String gridPosition = labelClicked.getId().substring(3);

        Iterable<Integer> neighboursIndexesIterable = model.CombinationMade(gridValues, gridPosition);

        if (neighboursIndexesIterable == null)
        {
            return;
        }

        ArrayList<String> neighboursGridPositionsArray = new ArrayList<>();

        // Translate index back to RxC format
        for (Integer i : neighboursIndexesIterable)
        {
            int r = i/ model.getHeight() + 1;
            int c = (i - (r-1)*model.getHeight()) + 1;
            neighboursGridPositionsArray.add(r + "x" + c);
        }

        //See if we have a combination with the neighbours
        if (neighboursGridPositionsArray.size() >= 2)
        {
            //Replace neighbours by randoms
            for (Integer idx : neighboursIndexesIterable)
            {
                LabelGridList.get(idx).setText(Integer.toString(model.GenerateRandomNumber()));
            }

            //Set clicked to random
            labelClicked.setText(Integer.toString(model.GenerateRandomNumber()));

            model.IncreaseScore(neighboursGridPositionsArray.size() + 1);
        }

        ScoreLbl.setText("Score: " + model.getUserScore());
    }

    public void RestartButtonHandler() throws IOException {
        view.CreateLoginWindow(stage);
    }

    private void SetLabelsToRandomized()
    {
        ArrayList<String> RandomizedValuesList = new ArrayList<>(model.GenerateRandomizedGrid());

        for (int i = 0; i < model.getWidth()*model.getHeight(); i++)
        {
            LabelGridList.get(i).setText(RandomizedValuesList.get(i));
        }
    }
}
