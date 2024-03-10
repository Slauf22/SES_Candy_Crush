package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.CheckNeighboursInGrid;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
    private final int width = 4;
    private final int height = 4;

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
            CreateWindow();
        }
    }

    public void RandomizeButtonHandler(ActionEvent event) throws IOException{
        model = new Model();
        model.RandomizeGrid(LabelGridList,height*width);
    }

    public void handleLabelClick(MouseEvent event) {
        ArrayList<Integer> gridValues = new ArrayList<>();

        //Put the integer values of the grid labels into a integer list to send to checkneighbours function
        for (Label label : LabelGridList) {
            int intValue = Integer.parseInt(label.getText());
            gridValues.add(intValue);
        }

        CheckNeighboursInGrid checkNeighboursInGrid = new CheckNeighboursInGrid();

        Label labelClicked = (Label) event.getSource();

        //The id mentions the grid position: lblRxC
        String gridPosition = labelClicked.getId().substring(3);
        int row = Integer.parseInt(gridPosition.substring(0,1));
        int col = Integer.parseInt(gridPosition.substring(2,3));

        //Get index from known row and cols. Function needs index from 0 to 15;
        int index = (row - 1) * 4 + (col - 1);

        Iterable <Integer> neighboursIndexes = checkNeighboursInGrid.getSameNeighboursIds(gridValues,width,height,index);

        System.out.println(neighboursIndexes);
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
        model.RandomizeGrid(LabelGridList,width*height);

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
