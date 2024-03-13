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
    private int width = 4;

    private int height = 4;

    ///////////////
    //Constructor//
    ///////////////

    public Model(int width, int height) {
        this.width = width;
        this.height = height;
    }

    ///////////////////////
    //Getters and Setters//
    ///////////////////////

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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

        for (int i = 0; i < width*height; i++) {
            int randomNumber = random.nextInt(5) + 1;
            RandomizedValues.add(String.valueOf(randomNumber));
        }

        return RandomizedValues;
    }

    //Function handles when a user clicks on a number on the grid.
    public void CombinationMadeHandler(ArrayList<Label> LabelGridList, MouseEvent event)
    {
        ArrayList<Integer> gridValues = new ArrayList<>();

        //Put the integer values of the grid labels into a integer list to send to checkneighbours function
        for (Label label : LabelGridList) {
            int intValue = Integer.parseInt(label.getText());
            gridValues.add(intValue);
        }

        CheckNeighboursInGrid checkNeighboursInGrid = new CheckNeighboursInGrid();

        Label labelClicked = (Label) event.getSource();

        //The id mentions the grid position: lblRxC. Get row and col from it
        String gridPosition = labelClicked.getId().substring(3);
        int row = Integer.parseInt(gridPosition.substring(0,1));
        int col = Integer.parseInt(gridPosition.substring(2,3));

        //Get index from known row and cols. Function needs index from 0 to gridsize - 1;
        int index = (row - 1) * 4 + (col - 1);

        Iterable<Integer> neighboursIndexesIterable = checkNeighboursInGrid.getSameNeighboursIds(gridValues,width,height,index);

        ArrayList<String> neighboursGridPositionsArray = new ArrayList<>();

        // Translate index back to RxC format
        for (Integer i : neighboursIndexesIterable)
        {
            int r = i/height + 1;
            int c = (i - (r-1)*height) + 1;
            neighboursGridPositionsArray.add(r + "x" + c);
        }

        //See if we have a combination with the neighbours
        if (neighboursGridPositionsArray.size() >= 2)
        {
            //Replace neighbours by randoms
            for (Integer idx : neighboursIndexesIterable)
            {
                LabelGridList.get(idx).setText(Integer.toString(GenerateRandomNumber()));
            }

            //Set clicked to random
            labelClicked.setText(Integer.toString(GenerateRandomNumber()));

            IncreaseScore(neighboursGridPositionsArray.size() + 1);
        }
    }

    public int GenerateRandomNumber() {
        Random random = new Random();

        return random.nextInt(5) + 1;
    }

    public void IncreaseScore(int value) {
        userScore += value;
    }
}
