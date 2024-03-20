package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.candycrushjavafxproject.Candies.Candy;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTest {
    @Test
    public void IncreaseUserScoreTests(){
        // Arrange
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize);

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
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize);

        // Act
        int score = model.getUserScore();

        // Assert
        assertEquals(score,0);
    }

    @Test
    public void ModelContructorSetHeightTest(){
        // Arrange
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize);;

        // Act
        int height = model.getHeight();

        // Assert
        assert(height == 4);
    }

    @Test
    public void ModelContructorSetWidthTest(){
        // Arrange
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize);

        // Act
        int width = model.getWidth();

        // Assert
        assert(width == 4);
    }

    @Test
    public void GenerateRandomNumberFunctionAbove1Below5Test(){
        // Arrange
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize);

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
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize);

        // Act
        model.setPlayerName("Rauf");

        // Assert
        assertEquals(model.getPlayerName(),"Rauf");
    }

    @Test
    public void RandomizeGridCompareToDefaultGridTest(){
        // Arrange
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize);

        ArrayList<Integer> gridValues = new ArrayList<>();

        for (int i = 0; i < 16; i++){
            gridValues.add(8);
        }

        // Act
        ArrayList<Candy> gridValuesRandomized = new ArrayList<>(model.GenerateRandomizedCandies());
        ArrayList<Integer> values = new ArrayList<>();

        for (Candy candy: gridValuesRandomized){
            values.add(candy.getColor());
        }

        // Assert
        assert(!values.equals(gridValues));
    }

    @Test
    public void CheckIfGenerateRandomGridGeneratesBetween0And8(){
        // Arrange
        BoardSize boardSize = new BoardSize(1,1);
        Model model = new Model(boardSize);

        // Act
        ArrayList<Candy> generated = new ArrayList<>(model.GenerateRandomizedCandies());

        // Assert
        assert((generated.getFirst().getColor()) >= 0 && (generated.getFirst().getColor()) <= 8);
    }

    @Test
    public void TestCombinationMadeWithHardCodedGrid(){
        // Arrange
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize);

        ArrayList<Integer> grid = new ArrayList<>();

        grid.add(1); grid.add(5); grid.add(2); grid.add(3);
        grid.add(1); grid.add(1); grid.add(1); grid.add(3);
        grid.add(1); grid.add(4); grid.add(2); grid.add(2);
        grid.add(3); grid.add(1); grid.add(1); grid.add(5);

        String gridPosition = "2x1";

        List<Position> HardcodedIndexes = new ArrayList<>();
        HardcodedIndexes.add(Position.fromIndex(0,boardSize));
        HardcodedIndexes.add(Position.fromIndex(5,boardSize));
        HardcodedIndexes.add(Position.fromIndex(8,boardSize));

        // Act
        Iterable<Position> neighboursIndexesIterable = model.CombinationMade(grid,gridPosition);

        // Assert
        assert(Objects.equals(HardcodedIndexes.toString(), neighboursIndexesIterable.toString()));
    }

    @Test
    public void IncreaseScoreInMultipleOccassions(){
        // Arrange
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize);

        // Act
        model.IncreaseScore(5);
        int score1 = model.getUserScore();
        model.IncreaseScore(4);
        int score2 = model.getUserScore();
        model.IncreaseScore(1);
        int score3 = model.getUserScore();

        // Assert
        assert(score1 == 5 && score2 == 9 && score3 == 10);
    }
}
