package be.kuleuven.candycrushjavafxproject;

import org.controlsfx.control.PropertySheet;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

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

        ArrayList<Label> labelArrayList = new ArrayList<>();

    }
}
