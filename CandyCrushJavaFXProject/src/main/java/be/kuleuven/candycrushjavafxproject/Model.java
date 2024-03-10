package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.CheckNeighboursInGrid;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Random;

public class Model {
    public void RandomizeGrid(ArrayList<Label> labelGridList,int nElements)
    {
        Random random = new Random();

        for (int i = 0; i < nElements; i++) {
            int randomNumber = random.nextInt(5) + 1;
            labelGridList.get(i).setText(String.valueOf(randomNumber));
        }
    }

    public void CombinationMadeHandler(ArrayList<Integer> gridValues, MouseEvent event, int gridHeight, int gridWidth)
    {
        CheckNeighboursInGrid checkNeighboursInGrid = new CheckNeighboursInGrid();

        Label labelClicked = (Label) event.getSource();

        //The id mentions the grid position: lblRxC
        String gridPosition = labelClicked.getId().substring(3);
        int row = Integer.parseInt(gridPosition.substring(0,1));
        int col = Integer.parseInt(gridPosition.substring(2,3));

        //Get index from known row and cols. Function needs index from 0 to 15;
        int index = (row - 1) * 4 + (col - 1);

        Iterable<Integer> neighboursIndexesIterable = checkNeighboursInGrid.getSameNeighboursIds(gridValues,gridWidth,gridHeight,index);

        ArrayList<String> neighboursGridPositionsArray = new ArrayList<>();

        for (Integer i : neighboursIndexesIterable)
        {
            int r = i/gridHeight + 1;
            int c = (i - (r-1)*gridHeight) + 1;
            neighboursGridPositionsArray.add(r + "x" + c);
        }

        System.out.println(neighboursGridPositionsArray);
    }
}
