package be.kuleuven;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args)
    {
        //Variables
        int gridWidth = 4;
        int gridHeight = 4;
        int indexToCheck = 15;

        //Initialize grid
        ArrayList<Integer> grid = new ArrayList<>(Arrays.asList(
                0, 0, 1, 0,
                1, 1, 0, 2,
                2, 0, 1, 3,
                0, 1, 1, 1
        ));

        //Create object
        CheckNeighboursInGrid myObj = new CheckNeighboursInGrid();

        //Get neighbour indexes with member function
        Iterable<Integer> neighboursIds = myObj.getSameNeighboursIds(grid, gridWidth,gridHeight,indexToCheck);

        //Print neighbouring indexes
        System.out.println(neighboursIds);
    }
}