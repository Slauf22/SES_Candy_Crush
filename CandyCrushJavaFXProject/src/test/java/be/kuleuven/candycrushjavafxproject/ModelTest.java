package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.candycrushjavafxproject.Candies.Candy;
import be.kuleuven.candycrushjavafxproject.Candies.normalCandy;
import javafx.geometry.Pos;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

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

    @Test
    public void firstTwoHaveCandyTestWithPositionWithOneLeftShouldFalse(){
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize);
        Position position = Position.fromIndex(5, boardSize);

        Candy normalCandy = new normalCandy(2);

        Stream<Position> PositionsLeft = position.walkLeft();

        boolean result = model.firstTwoHaveCandy(normalCandy, PositionsLeft);

        assert (!result);
    }

    @Test
    public void firstTwoHaveCandyTestWithBoardDefinedSoReturnTrue(){
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize);
        Position position = Position.fromIndex(6, boardSize);

        Candy normalCandy = new normalCandy(2);

        Stream<Position> PositionsLeft = position.walkLeft();

        boolean result = model.firstTwoHaveCandy(normalCandy, PositionsLeft);

        System.out.println(result);
    }
}
