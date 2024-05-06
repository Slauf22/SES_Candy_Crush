package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.candycrushjavafxproject.Candies.*;
import be.kuleuven.candycrushjavafxproject.GenericBoard.Board;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

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
    private final Board<Candy> candyBoard;
    private final Board<Candy> candyBoardCopied;
    private int nClicked;
    private Node highlightedNode;

    ///////////////
    //Constructor//
    ///////////////

    public Controller()
    {
        // Boven een boardsize van 6x6 duurt het heel lang om de hoogste score te vinden met maximizeScore. Ik raad dus max 6x6 aan als je die feature wilt gebruiken.
        boardSize = new BoardSize(5,5);
        candyBoard = new Board<>(boardSize);
        candyBoardCopied = new Board<>(boardSize);
        model = new Model(boardSize, candyBoard);
        view = new View();
        nClicked = 0;
        highlightedNode = null;
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

        ScoreLbl = new Label();
        ScoreLbl.setText("Score: 0");
        ScoreLbl.setId("ScoreLbl");
        ScoreLbl.setLayoutY(213.0);
        ScoreLbl.setLayoutX(414.0);
        ScoreLbl.setFont(new Font(33.0));

        model.setUserScore(0);

        pane.getChildren().add(ScoreLbl);

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
        String name = "Player";

        if (!Objects.equals(NameTextBox.getText(), ""))
        {
            name = NameTextBox.getText();
        }

        //Read name
        model.setPlayerName(name);

        //Read stage, otherwise its null
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        //Create game window
        CreateWindow();

    }

    public void ResetButtonHandler(ActionEvent event) throws IOException{
        model.setUserScore(0);
        ScoreLbl.setText("Score: 0");

        candyBoardCopied.copyTo(candyBoard);

        removeGrid();
        updateGrid();
    }

    public void HandleLabelClick(MouseEvent event) {
        Node nodeClicked = (Node) event.getSource();

        //The id mentions the grid position: lblRxC. Get row and col from it
        String gridPosition = nodeClicked.getId().substring(3);
        Position pressedLabelPosition = model.RxCToPosition(gridPosition);

        // If pressed is a clear candy
        if (candyBoard.getCellAt(pressedLabelPosition).getColor() == 99)
        {
            return;
        }

        // When you select the first candy, highlight it
        if (nClicked == 0){
            nClicked++;
            view.highlightNode(nodeClicked);
            highlightedNode = nodeClicked;
        }
        // When you select the second, attempt a swap
        else {
            // If pressed same node again, deselect that node
            if (nodeClicked.equals(highlightedNode)){
                // Clear the highlight
                model.nodeToPosition(highlightedNode);

                nClicked = 0;
                view.clearHighlights(highlightedNode);

                // Update the grid in UI
                removeGrid();
                updateGrid();

                // Set to null for selecting a original candy
                highlightedNode = null;

                return;
            }

            // Swap candies if possible
            Board<Candy> backupBoard = new Board<>(boardSize);
            candyBoard.copyTo(backupBoard);

            Position firstCandyPosition = model.nodeToPosition(highlightedNode);
            Position secondCandyPosition = model.nodeToPosition(nodeClicked);

            // If swap is possible
            if (candyBoard.swapTwoPositions(firstCandyPosition, secondCandyPosition)) {

                // The swap hasnt lead to a match, meaning that its a false swap and i need to return the board to how it was.;
                if (model.findAllMatches().isEmpty()){
                    backupBoard.copyTo(candyBoard);
                    return;
                }

                // Clear the highlight
                model.nodeToPosition(highlightedNode);

                nClicked = 0;
                view.clearHighlights(highlightedNode);

                // Set to null for selecting a original candy
                highlightedNode = null;

                // Update the board after a valid match
                model.updateBoard();

                // Update the grid in UI
                removeGrid();
                updateGrid();

                // Update user score on UI
                ScoreLbl.setText("Score: " + model.getUserScore());
            }
        }
    }

    public void RestartButtonHandler() throws IOException {
        view.CreateLoginWindow(stage);
    }

    public void SolveButtonHandler() throws  IOException {
        model.maximizeScore();
    }

    private void GenerateGridNodes()
    {
        Function<Position, Candy> cellCreator = position -> {
            return model.GenerateRandomCandy();
        };

        candyBoard.fill(cellCreator);
        candyBoard.copyTo(candyBoardCopied);

        if (!model.findAllMatches().isEmpty())
        {
            GenerateGridNodes();
        }

        updateGrid();
    }

    private void updateGrid(){
        List<Double> x_positions = new ArrayList<>();

        for (int i = 0; i < boardSize.cols()* boardSize.rows(); i++)
        {
            Position currentPosition = Position.fromIndex(i,boardSize);

            Node shape = view.makeCandyShape(currentPosition, candyBoard.getCellAt(currentPosition));

            pane.getChildren().add(shape);

            x_positions.add(shape.getLayoutX());
        }

        double largest_x = x_positions.stream().max(Double::compareTo).orElseThrow();

        ScoreLbl.setLayoutX(largest_x + 60.0);
    }

    private void removeGrid(){
        if(highlightedNode != null) {
            view.clearHighlights(highlightedNode);
            highlightedNode = null;
            nClicked = 0;
        }

        ArrayList<String> idsToRemoveList = new ArrayList<>();

        for (int i = 1; i <= boardSize.rows(); i++) {
            for (int j = 1; j <= boardSize.cols(); j++) {
                String id = "lbl" + i + "x" + j;
                idsToRemoveList.add(id);
            }
        }

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
