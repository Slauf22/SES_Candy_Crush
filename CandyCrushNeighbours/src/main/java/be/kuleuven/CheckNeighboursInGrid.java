package be.kuleuven;

import java.util.ArrayList;

public class CheckNeighboursInGrid {

    public Iterable<Integer> getSameNeighboursIds(Iterable<Integer> grid,int width, int height, int indexToCheck)
    {
        //Wrong input detection
        if (indexToCheck > (width*height)-1)
        {
            System.out.println("Index is outside of the grid range. Grid has " + height*width + " elements ranging from 0 to " + ((height*width)-1));
            return null;
        }

        //Find the row of the index
        int indexRow = indexToCheck/height;

        // Find the column of the index
        int indexCol = indexToCheck;

        while(true)
        {
            if (indexCol >= width)
            {
                indexCol -= width;
            }
            else
            {
                break;
            }
        }

        //Value of element at index
        int valueIndex = 0;
        int indexCnt = 0;

        for (Integer element : grid)
        {
            if (indexCnt == indexToCheck)
            {
                valueIndex = element;
            }
            indexCnt++;
        }

        //Array for saving neighbour indexes
        ArrayList<Integer> neighbours = new ArrayList<>();

        int rowCounter = 0;
        int colCounter = 0;

        // Loop over grid
        for (Integer element : grid)
        {
            // We are in a neighbouring column, left or right. Can also be same column if element is one row above or below
            if (colCounter == indexCol - 1  || colCounter == indexCol + 1 || colCounter == indexCol)
            {
                // We are in a neighbouring row, above or below. Can also be same row if element is one col left or right
                if (rowCounter == indexRow - 1 || rowCounter == indexRow + 1 || rowCounter == indexRow)
                {
                    //If neighbour has same value, add index to arraylist. Also check if the loop isn't at the indexToCheck.
                    if ((element == valueIndex) && !(rowCounter == indexRow && colCounter == indexCol))
                    {
                        neighbours.add((rowCounter*width)+colCounter);
                    }
                }
            }

            //Update row and col of element in grid
            if(colCounter == 3)
            {
                colCounter = 0;
                rowCounter++;
            }
            else
            {
                colCounter++;
            }
        }

        return neighbours;
    }
}
