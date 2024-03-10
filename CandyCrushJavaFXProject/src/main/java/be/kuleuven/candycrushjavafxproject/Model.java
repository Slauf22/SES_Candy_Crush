package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.CheckNeighboursInGrid;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Random;

public class Model {

    ////////////////////
    //Member variables//
    ////////////////////

    private int userScore = 0;
    private String PlayerName;
    private final int width = 4;
    private final int height = 4;

    ///////////////////////
    //Getters and Setters//
    ///////////////////////

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
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
    public void RandomizeGrid(ArrayList<Label> labelGridList)
    {
        Random random = new Random();

        for (int i = 0; i < width*height; i++) {
            int randomNumber = random.nextInt(5) + 1;
            labelGridList.get(i).setText(String.valueOf(randomNumber));
        }
    }

    //Function handles when a user clicks on a number on the grid.
    public void CombinationMadeHandler(ArrayList<Integer> gridValues, MouseEvent event)
    {
        CheckNeighboursInGrid checkNeighboursInGrid = new CheckNeighboursInGrid();

        Label labelClicked = (Label) event.getSource();

        //The id mentions the grid position: lblRxC
        String gridPosition = labelClicked.getId().substring(3);
        int row = Integer.parseInt(gridPosition.substring(0,1));
        int col = Integer.parseInt(gridPosition.substring(2,3));

        //Get index from known row and cols. Function needs index from 0 to 15;
        int index = (row - 1) * 4 + (col - 1);

        Iterable<Integer> neighboursIndexesIterable = checkNeighboursInGrid.getSameNeighboursIds(gridValues,width,height,index);

        ArrayList<String> neighboursGridPositionsArray = new ArrayList<>();

        for (Integer i : neighboursIndexesIterable)
        {
            int r = i/height + 1;
            int c = (i - (r-1)*height) + 1;
            neighboursGridPositionsArray.add(r + "x" + c);
        }

        if (!neighboursGridPositionsArray.isEmpty())
        {
            userScore += neighboursGridPositionsArray.size() + 1;
        }
    }
}
