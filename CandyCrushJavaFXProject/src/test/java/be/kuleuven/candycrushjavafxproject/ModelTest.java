package be.kuleuven.candycrushjavafxproject;

import org.controlsfx.control.PropertySheet;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javafx.scene.control.Label;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTest {
    @Test
    public void IncreaseUserScoreTests(){
        // Arrange
        Model model = new Model(4,4);

        // Act
        model.IncreaseScore(5);
        model.IncreaseScore(12);
        int score = model.getUserScore();

        // Assert
        assertEquals(score,17);
    }

    @Test
    public void CheckIfInitialScoreIs0Test(){
        // Arrange
        Model model = new Model(4,4);

        // Act
        int score = model.getUserScore();

        // Assert
        assertEquals(score,0);
    }

    @Test
    public void ModelContructorSetHeightTest(){
        // Arrange
        Model model = new Model(4,4);

        // Act
        int height = model.getHeight();

        // Assert
        assert(height == 4);
    }

    @Test
    public void ModelContructorSetWidthTest(){
        // Arrange
        Model model = new Model(4,4);

        // Act
        int width = model.getWidth();

        // Assert
        assert(width == 4);
    }

    @Test
    public void GenerateRandomNumberFunctionAbove1Below5Test(){
        // Arrange
        Model model = new Model(4,4);

        // Act
        int number1 = model.GenerateRandomNumber();
        int number2 = model.GenerateRandomNumber();
        int number3 = model.GenerateRandomNumber();
        int number4 = model.GenerateRandomNumber();
        int number5 = model.GenerateRandomNumber();

        // Assert
        assert ((number1 >= 1 && number1 <= 5) &&
                (number2 >= 1 && number2 <= 5) &&
                (number3 >= 1 && number3 <= 5) &&
                (number4 >= 1 && number4 <= 5) &&
                (number5 >= 1 && number5 <= 5));
    }

    @Test
    public void SetNameTest(){
        // Arrange
        Model model = new Model(4,4);

        // Act
        model.setPlayerName("Rauf");

        // Assert
        assertEquals(model.getPlayerName(),"Rauf");
    }

    @Test
    public void RandomizeGridCompareToDefaultGridTest(){
        // Arrange
        Model model = new Model(4,4);

        ArrayList<String> gridValues = new ArrayList<>();

        for (int i = 0; i < 16; i++){
            gridValues.add("8");
        }

        // Act
        ArrayList<String> gridValuesRandomized = new ArrayList<>(model.GenerateRandomizedGrid());

        // Assert
        assert(!gridValuesRandomized.equals(gridValues));
    }

    @Test
    public void CheckIfGenerateRandomGridGeneratesBetween1And5(){
        // Arrange
        Model model = new Model(1,1);

        // Act
        ArrayList<String> generated= new ArrayList<>(model.GenerateRandomizedGrid());

        // Assert
        assert(Integer.parseInt(generated.getFirst()) >= 1 && Integer.parseInt(generated.getFirst()) <= 5);
    }

    @Test
    public void TestCombinationMadeWithHardCodedGrid(){
        // Arrange
        Model model = new Model(4,4);

        ArrayList<Integer> grid = new ArrayList<>();

        grid.add(1); grid.add(5); grid.add(2); grid.add(3);
        grid.add(1); grid.add(1); grid.add(1); grid.add(3);
        grid.add(1); grid.add(4); grid.add(2); grid.add(2);
        grid.add(3); grid.add(1); grid.add(1); grid.add(5);

        String gridPosition = "2x1";

        List<Integer> HardcodedIndexes = new ArrayList<>();
        HardcodedIndexes.add(0);
        HardcodedIndexes.add(5);
        HardcodedIndexes.add(8);

        // Act
        Iterable<Integer> neighboursIndexesIterable = model.CombinationMade(grid,gridPosition);

        // Assert
        assert(Objects.equals(HardcodedIndexes.toString(), neighboursIndexesIterable.toString()));
    }
}
