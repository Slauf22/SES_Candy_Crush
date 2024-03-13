package be.kuleuven.candycrushjavafxproject;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import javafx.scene.control.Label;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTest {
    @Test
    public void IncreaseUserScoreTests(){
        Model model = new Model(4,4);

        model.IncreaseScore(5);
        model.IncreaseScore(12);

        assertEquals(model.getUserScore(),17);
    }

    @Test
    public void CheckIfInitialScoreIs0Test(){
        Model model = new Model(4,4);

        assertEquals(model.getUserScore(),0);
    }

    @Test
    public void ModelContructorSetHeightTest(){
        Model model = new Model(4,4);

        assert(model.getHeight() == 4);
    }

    @Test
    public void ModelContructorSetWidthTest(){
        Model model = new Model(4,4);

        assert(model.getWidth() == 4);
    }

    @Test
    public void GenerateRandomNumberFunctionAbove1Below5Test(){
        Model model = new Model(4,4);

        int number1 = model.GenerateRandomNumber();
        int number2 = model.GenerateRandomNumber();
        int number3 = model.GenerateRandomNumber();
        int number4 = model.GenerateRandomNumber();
        int number5 = model.GenerateRandomNumber();

        assert ((number1 >= 1 && number1 <= 5) &&
                (number2 >= 1 && number2 <= 5) &&
                (number3 >= 1 && number3 <= 5) &&
                (number4 >= 1 && number4 <= 5) &&
                (number5 >= 1 && number5 <= 5));
    }

    @Test
    public void SetNameTest(){
        Model model = new Model(4,4);

        model.setPlayerName("Rauf");

        assertEquals(model.getPlayerName(),"Rauf");
    }

    @Test
    public void RandomizeGridCompareToDefaultGridTest(){
        Model model = new Model(4,4);

        ArrayList<Label> grid = new ArrayList<>();

        for(int i = 0; i < 16; i++)
        {
            Label lbl = new Label();
            lbl.setText("8");
            grid.add(lbl);
        }

        ArrayList<Label> randomizedGrid = new ArrayList<>(grid);

        model.RandomizeGrid(randomizedGrid);

        assert(!randomizedGrid.equals(grid));
    }
}
