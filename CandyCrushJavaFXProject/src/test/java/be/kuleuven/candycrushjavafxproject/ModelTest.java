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
    public void RandomizeTwoGridsTest(){
        Model model = new Model(2,2);

        ArrayList<Label> labelArrayListOriginal = new ArrayList<>();

        Label lbl1 = new Label();
        Label lbl2 = new Label();
        Label lbl3 = new Label();
        Label lbl4 = new Label();

        lbl1.setText("8");
        lbl2.setText("8");
        lbl3.setText("8");
        lbl4.setText("8");

        labelArrayListOriginal.add(lbl1);
        labelArrayListOriginal.add(lbl2);
        labelArrayListOriginal.add(lbl3);
        labelArrayListOriginal.add(lbl4);

        ArrayList<Label> labelArrayListRandomized = new ArrayList<>(labelArrayListOriginal);

        model.RandomizeGrid(labelArrayListRandomized);

        assert(!(labelArrayListOriginal.equals(labelArrayListRandomized)));
    }

    @Test
    public void ModelContructorSetHeightWidthTest(){
        Model model = new Model(4,4);

        assert(model.getHeight() == 4 && model.getWidth() == 4);
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
}
