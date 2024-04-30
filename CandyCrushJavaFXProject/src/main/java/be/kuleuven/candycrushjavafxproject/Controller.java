package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.candycrushjavafxproject.Candies.Candy;
import be.kuleuven.candycrushjavafxproject.Candies.normalCandy;
import be.kuleuven.candycrushjavafxproject.Candies.rowDeleteCandy;
import be.kuleuven.candycrushjavafxproject.GenericBoard.Board;
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

    ///////////////
    //Constructor//
    ///////////////

    public Controller()
    {
        boardSize = new BoardSize(8,8);
        candyBoard = new Board<>(boardSize);
        model = new Model(boardSize, candyBoard);
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

        ScoreLbl = new Label();
        ScoreLbl.setText("Score: 0");
        ScoreLbl.setId("ScoreLbl");
        ScoreLbl.setLayoutY(213.0);
        ScoreLbl.setLayoutX(414.0);
        ScoreLbl.setFont(new Font(33.0));

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

        //The id mentions the grid position: lblRxC. Get row and col from it
        String gridPosition = nodeClicked.getId().substring(3);
        Position pressedLabelPosition = model.RxCToPosition(gridPosition);

        // If pressed is a clear candy
        if (candyBoard.getCellAt(pressedLabelPosition).getColor() == 99)
        {
            return;
        }

        Iterable<Position> neighoursPositions = model.getSameNeighbourPositions(pressedLabelPosition);

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
                candyBoard.replaceCellAt(idx,model.GenerateRandomCandy());
            }

            //Set clicked to random
            candyBoard.replaceCellAt(model.RxCToPosition(gridPosition), model.GenerateRandomCandy());

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
        Function<Position, Candy> cellCreator = position -> {
            return model.GenerateRandomCandy();
        };

        candyBoard.fill(cellCreator);

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
