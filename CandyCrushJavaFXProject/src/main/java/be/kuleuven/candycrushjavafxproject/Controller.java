package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.candycrushjavafxproject.Candies.Candy;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
    public Label ScoreLbl;
    @FXML
    public AnchorPane pane;

    ////////////////////
    //Member Variables//
    ////////////////////

    private Stage stage;
    private final Model model;
    private final View view;
    private final BoardSize boardSize;
    private ArrayList<Candy> gridCandiesList;

    ///////////////
    //Constructor//
    ///////////////

    public Controller()
    {
        boardSize = new BoardSize(4,4);
        model = new Model(boardSize);
        view = new View();
        gridCandiesList = new ArrayList<>();
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

        GenerateGridNodes();

        stage.show();
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
        removeGrid();
        GenerateGridNodes();
    }

    public void HandleLabelClick(MouseEvent event) {
        Node nodeClicked = (Node) event.getSource();

        ArrayList<Integer> gridColorValues = new ArrayList<>();

        for (Candy candy : gridCandiesList) {
            gridColorValues.add(candy.getColor());
        }

        //The id mentions the grid position: lblRxC. Get row and col from it
        String gridPosition = nodeClicked.getId().substring(3);

        Iterable<Position> neighoursPositions = model.CombinationMade(gridColorValues, gridPosition);

        if (neighoursPositions == null)
        {
            return;
        }

        ArrayList<String> neighboursGridPositionsArray = new ArrayList<>();

        // Translate index back to RxC format
        for (Position i : neighoursPositions)
        {
            int r = i.toIndex()/ model.getHeight() + 1;
            int c = (i.toIndex() - (r-1)*model.getHeight()) + 1;
            neighboursGridPositionsArray.add(r + "x" + c);
        }

        //See if we have a combination with the neighbours
        if (neighboursGridPositionsArray.size() >= 2)
        {
            //Replace neighbours by randoms
            for (Position idx : neighoursPositions)
            {
                gridCandiesList.set(idx.toIndex(), model.GenerateRandomCandy());
            }

            //Set clicked to random
            gridCandiesList.set(model.RxCToPosition(gridPosition).toIndex(),model.GenerateRandomCandy());

            //Update the grid
            removeGrid();

            updateGrid();

            model.IncreaseScore(neighboursGridPositionsArray.size() + 1);
        }

        ScoreLbl.setText("Score: " + model.getUserScore());
    }

    public void RestartButtonHandler() throws IOException {
        view.CreateLoginWindow(stage);
    }

    private void GenerateGridNodes()
    {
        gridCandiesList = new ArrayList<>(model.GenerateRandomizedCandies());

        updateGrid();
    }

    private void updateGrid(){
        int cnt = 0;

        for (Candy candy: gridCandiesList)
        {
            pane.getChildren().add(view.makeCandyShape(Position.fromIndex(cnt,boardSize),candy));
            cnt++;
        }
    }

    private void removeGrid(){
        ArrayList<String> idsToRemoveList = new ArrayList<>();

        idsToRemoveList.add("lbl1x1");idsToRemoveList.add("lbl1x2");idsToRemoveList.add("lbl1x3");idsToRemoveList.add("lbl1x4");
        idsToRemoveList.add("lbl2x1");idsToRemoveList.add("lbl2x2");idsToRemoveList.add("lbl2x3");idsToRemoveList.add("lbl2x4");
        idsToRemoveList.add("lbl3x1");idsToRemoveList.add("lbl3x2");idsToRemoveList.add("lbl3x3");idsToRemoveList.add("lbl3x4");
        idsToRemoveList.add("lbl4x1");idsToRemoveList.add("lbl4x2");idsToRemoveList.add("lbl4x3");idsToRemoveList.add("lbl4x4");

        Iterator<Node> iterator = pane.getChildren().iterator();

        while (iterator.hasNext()) {
            Node node = iterator.next();
            String nodeId = node.getId();

            if (nodeId != null && idsToRemoveList.contains(nodeId)) {
                iterator.remove();
            }
        }
    }
}
