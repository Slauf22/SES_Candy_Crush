package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.CheckNeighboursInGrid;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Random;

public class Model {

    ////////////////////
    //Member variables//
    ////////////////////

    private int userScore = 0;
    private String PlayerName;
    private final BoardSize boardSize;

    ///////////////
    //Constructor//
    ///////////////

    public Model(BoardSize boardSize) {
        this.boardSize = boardSize;
    }

    ///////////////////////
    //Getters and Setters//
    ///////////////////////

    public int getWidth() {
        return boardSize.cols();
    }

    public int getHeight() {
        return boardSize.rows();
    }

    public int getUserScore() {
        return userScore;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    ////////////////////
    //Member Functions//
    ////////////////////

    //Function randomized the grid
    public ArrayList<String> GenerateRandomizedGrid()
    {
        Random random = new Random();

        ArrayList<String> RandomizedValues = new ArrayList<>();

        for (int i = 0; i < boardSize.cols() * boardSize.rows(); i++) {
            int randomNumber = random.nextInt(5) + 1;
            RandomizedValues.add(String.valueOf(randomNumber));
        }

        return RandomizedValues;
    }

    public Iterable<Position> CombinationMade(ArrayList<Integer> gridValues, String gridPosition)
    {
        CheckNeighboursInGrid checkNeighboursInGrid = new CheckNeighboursInGrid();

        int row = Integer.parseInt(gridPosition.substring(0,1));
        int col = Integer.parseInt(gridPosition.substring(2,3));

        Position position = new Position(row,col,boardSize);

        //Get index from known row and cols. Function needs index from 0 to gridsize - 1;
        int index = position.toIndex();

        ArrayList<Integer> neighbourIds = (ArrayList<Integer>) checkNeighboursInGrid.getSameNeighboursIds(gridValues, boardSize.cols(), boardSize.rows(), index);

        ArrayList<Position> neighbourPositions = new ArrayList<>();

        for (Integer id: neighbourIds){
            neighbourPositions.add(Position.fromIndex(id,boardSize));
        }

        return neighbourPositions;
    }

    public int GenerateRandomNumber() {
        Random random = new Random();

        return random.nextInt(5) + 1;
    }

    public void IncreaseScore(int value) {
        userScore += value;
    }
}
