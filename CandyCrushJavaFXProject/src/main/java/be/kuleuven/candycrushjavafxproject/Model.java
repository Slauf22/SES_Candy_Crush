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
    public Iterable<Integer> CombinationMadeHandler(ArrayList<Integer> gridValues, String gridPosition)
    {
        CheckNeighboursInGrid checkNeighboursInGrid = new CheckNeighboursInGrid();

        int row = Integer.parseInt(gridPosition.substring(0,1));
        int col = Integer.parseInt(gridPosition.substring(2,3));

        //Get index from known row and cols. Function needs index from 0 to gridsize - 1;
        int index = (row - 1) * 4 + (col - 1);

        return checkNeighboursInGrid.getSameNeighboursIds(gridValues,width,height,index);
    }

    public int GenerateRandomNumber() {
        Random random = new Random();

        return random.nextInt(5) + 1;
    }

    public void IncreaseScore(int value) {
        userScore += value;
    }
}
