package be.kuleuven.candycrushjavafxproject;

import javafx.scene.control.Label;

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
}
