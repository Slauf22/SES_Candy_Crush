package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.CheckNeighboursInGrid;
import be.kuleuven.candycrushjavafxproject.Candies.*;

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
    public ArrayList<Candy> GenerateRandomizedCandies()
    {
        Random random = new Random();

        ArrayList<Candy> RandomizedCandies = new ArrayList<>();

        for (int i = 0; i < boardSize.cols() * boardSize.rows(); i++) {
            RandomizedCandies.add(GenerateRandomCandy());
        }

        return RandomizedCandies;
    }

    public Candy GenerateRandomCandy(){
        Random random = new Random();
        int randomNumber = random.nextInt(40);

        //Minder kans om een speciale snoep te genereren dan een normale
        if (randomNumber == 39){
            return new rowDeleteCandy(4);
        }
        if (randomNumber == 38){
            return new extraMoveCandy(5);
        }
        if (randomNumber == 37){
            return new doublePointsCandy(6);
        }
        if (randomNumber == 36){
            return new borderDeleteCandy(7);
        }

        return new normalCandy(random.nextInt(4));
    }

    public Position RxCToPosition(String gridPosition){
        int row = Integer.parseInt(gridPosition.substring(0,1));
        int col = Integer.parseInt(gridPosition.substring(2,3));

        return new Position(row,col,boardSize);
    }

    public Iterable<Position> CombinationMade(ArrayList<Integer> gridValues, String gridPosition)
    {
        CheckNeighboursInGrid checkNeighboursInGrid = new CheckNeighboursInGrid();

        //Get index from known row and cols. Function needs index from 0 to gridsize - 1;
        int index = RxCToPosition(gridPosition).toIndex();

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
