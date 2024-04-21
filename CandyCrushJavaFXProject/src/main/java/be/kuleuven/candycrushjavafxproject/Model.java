package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.CheckNeighboursInGrid;
import be.kuleuven.candycrushjavafxproject.Candies.*;
import be.kuleuven.candycrushjavafxproject.GenericBoard.Board;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Model {

    ////////////////////
    //Member variables//
    ////////////////////

    private int userScore = 0;
    private String PlayerName;
    private final BoardSize boardSize;
    private final Board<Candy> candyBoard;
    ///////////////
    //Constructor//
    ///////////////

    public Model(BoardSize boardSize, Board<Candy> candyBoard) {
        this.boardSize = boardSize;
        this.candyBoard = candyBoard;
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

    public void IncreaseScore(int value) {
        userScore += value;
    }

    public Iterable<Position> getSameNeighbourPositions(Position position){

        //Find the row of the index
        int indexRow = position.row();

        // Find the column of the index
        int indexCol = position.col();

        //Value (color) of element at index
        int valueIndex = candyBoard.getCellAt(position).getColor();

        //Array for saving neighbour positions
        ArrayList<Position> neighbours = new ArrayList<>();

        int rowCounter = 1;
        int colCounter = 1;

        // Loop over grid
        for (int i = 0; i < boardSize.rows()* boardSize.cols(); i++)
        {
            // We are in a neighbouring column, left or right. Can also be same column if element is one row above or below
            if (colCounter == indexCol - 1  || colCounter == indexCol + 1 || colCounter == indexCol)
            {
                // We are in a neighbouring row, above or below. Can also be same row if element is one col left or right
                if (rowCounter == indexRow - 1 || rowCounter == indexRow + 1 || rowCounter == indexRow)
                {
                    //If neighbour has same value, add index to arraylist. Also check if the loop isn't at the indexToCheck.
                    if ((candyBoard.getCellAt(Position.fromIndex(i,boardSize)).getColor() == valueIndex) && !(rowCounter == indexRow && colCounter == indexCol))
                    {
                        neighbours.add(RxCToPosition(Integer.toString(rowCounter) + "x" + Integer.toString(colCounter)));
                    }
                }
            }

            //Update row and col of element in grid
            if(colCounter == 4)
            {
                colCounter = 1;
                rowCounter++;
            }
            else
            {
                colCounter++;
            }
        }

        return neighbours;
    }

    boolean firstTwoHaveCandy(Candy candy, Stream<Position> positions){
        List<Position> positionList = positions.toList();

        if (positionList.size() < 2){
            return false;
        }

        long nMatches = positionList.stream()
                .limit(2)
                .filter(position -> candyBoard.getCellAt(position).getColor() == candy.getColor())
                .count();

        return nMatches >= 2;
    }

    List<Position> longestMatchToRight(Position pos){
        Stream<Position> rightPositions = pos.walkRight();

        Candy currentCandy = candyBoard.getCellAt(pos);
        List<Position> lst = rightPositions
                .takeWhile(position -> candyBoard.getCellAt(position).getColor() == currentCandy.getColor())
                .toList();

        return lst;
    }

    List<Position> longestMatchDown(Position pos){
        Stream<Position> rightPositions = pos.walkDown();

        Candy currentCandy = candyBoard.getCellAt(pos);
        List<Position> lst = rightPositions
                .takeWhile(position -> candyBoard.getCellAt(position).getColor() == currentCandy.getColor())
                .toList();

        return lst;
    }

    Stream<Position> horizontalStartingPositions(){
        List<Position> positionList = new ArrayList<>();
        Position currentPosition;
        Candy currentCandy;

        for (int i = 0; i < boardSize.rows()*boardSize.cols(); i++){
            currentPosition = Position.fromIndex(i,boardSize);
            List<Position> positionsLeft = currentPosition.walkLeft().toList();

            // No combinations possible
            if (positionsLeft.size() < 2){
                continue;
            }

            currentCandy = candyBoard.getCellAt(currentPosition);
            Stream<Position> positionsStream = positionsLeft.stream();

            if (!firstTwoHaveCandy(currentCandy, positionsStream)){
                positionList.add(currentPosition);
            }
        }

        return positionList.stream();
    }
}
