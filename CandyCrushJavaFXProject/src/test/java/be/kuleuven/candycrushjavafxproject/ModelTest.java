package be.kuleuven.candycrushjavafxproject;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Objects;
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
        Model model = new Model(4,4);

        ArrayList<Label> labelArrayList1 = new ArrayList<>();

        //Initialize a lbl array all with text 0
        for (int i = 0; i < 16; i++)
        {
            Label lbl = new Label();
            lbl.setText("0");
            labelArrayList1.add(lbl);
        }

        //Copy to second arraylist
        ArrayList<Label> labelArrayList2;
        labelArrayList2 = labelArrayList1;

        //Randomize both and check if they are different
        model.RandomizeGrid(labelArrayList1);
        model.RandomizeGrid(labelArrayList2);

        int counter = 0;

        for (int i = 0; i < 16; i++)
        {
            if (Objects.equals(labelArrayList2.get(i).getText(), labelArrayList1.get(i).getText()))
            {
                counter++;
            }
        }

        assert(counter < 16);
    }

    @Test
    public void modelContructorSetHeightWidthTest(){
        Model model = new Model(4,4);

        assert(model.getHeight() == 4 && model.getWidth() == 4);
    }
}
