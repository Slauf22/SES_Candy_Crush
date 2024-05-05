package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.candycrushjavafxproject.Candies.Candy;
import be.kuleuven.candycrushjavafxproject.Candies.borderDeleteCandy;
import be.kuleuven.candycrushjavafxproject.Candies.doublePointsCandy;
import be.kuleuven.candycrushjavafxproject.Candies.normalCandy;
import be.kuleuven.candycrushjavafxproject.GenericBoard.Board;
import javafx.geometry.Pos;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTest {
    @Test
    public void IncreaseUserScoreTests(){
        // Arrange
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize,null);

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
        Model model = new Model(boardSize,null);

        // Act
        int score = model.getUserScore();

        // Assert
        assertEquals(score,0);
    }

    @Test
    public void ModelContructorSetHeightTest(){
        // Arrange
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize,null);

        // Act
        int height = model.getHeight();

        // Assert
        assert(height == 4);
    }

    @Test
    public void ModelContructorSetWidthTest(){
        // Arrange
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize,null);

        // Act
        int width = model.getWidth();

        // Assert
        assert(width == 4);
    }

    @Test
    public void SetNameTest(){
        // Arrange
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize,null);

        // Act
        model.setPlayerName("Rauf");

        // Assert
        assertEquals(model.getPlayerName(),"Rauf");
    }

    @Test
    public void IncreaseScoreInMultipleOccassions(){
        // Arrange
        BoardSize boardSize = new BoardSize(4,4);
        Model model = new Model(boardSize,null);

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
    public void testWalkLeftReturnedStream(){
        BoardSize boardSize = new BoardSize(4,4);
        Board<Candy> candyBoard = new Board<>(boardSize);
        Model model = new Model(boardSize,candyBoard);
        Position position = Position.fromIndex(6, boardSize);

        List<Position> lst = position.walkLeft().toList();

        assert(lst.size() == 3);
    }

    @Test
    public void testWalkUpReturnedStream(){
        BoardSize boardSize = new BoardSize(4,4);
        Board<Candy> candyBoard = new Board<>(boardSize);
        Model model = new Model(boardSize,candyBoard);
        Position position = Position.fromIndex(6, boardSize);

        List<Position> lst = position.walkUp().toList();

        assert(lst.size() == 2);
    }

    @Test
    public void firstTwoHaveCandyTestWithBoardDefinedSoReturnTrue(){
        BoardSize boardSize = new BoardSize(4,4);
        Board<Candy> candyBoard = new Board<>(boardSize);
        Model model = new Model(boardSize,candyBoard);
        Position position = Position.fromIndex(7, boardSize);

        Function<Position, Candy> cellCreator = pos -> {
            if (pos.toIndex() == 6 || pos.toIndex() == 7){
                return new normalCandy(2);
            }
            else {
                return new normalCandy(1);
            }
        };
        candyBoard.fill(cellCreator);

        Candy normalCandy = new normalCandy(2);

        Stream<Position> PositionsLeft = position.walkLeft();

        boolean result = model.firstTwoHaveCandy(normalCandy, PositionsLeft);

        assert(result);
    }

    @Test
    public void LongestMatchRightFunctionTesting(){
        BoardSize boardSize = new BoardSize(4,4);
        Board<Candy> candyBoard = new Board<>(boardSize);
        Model model = new Model(boardSize,candyBoard);
        Position position = Position.fromIndex(4, boardSize);

        Function<Position, Candy> cellCreator = pos -> {
            if (pos.toIndex() == 4 || pos.toIndex() == 5 || pos.toIndex() == 6)
                return new normalCandy(2);
            else
                return new normalCandy(1);
        };
        candyBoard.fill(cellCreator);

        List<Position> lst = model.longestMatchToRight(position);

        assert(lst.size() == 3);
    }

    @Test
    public void LongestMatchDownFunctionTesting(){
        BoardSize boardSize = new BoardSize(4,4);
        Board<Candy> candyBoard = new Board<>(boardSize);
        Model model = new Model(boardSize,candyBoard);
        Position position = Position.fromIndex(4, boardSize);

        Function<Position, Candy> cellCreator = pos -> {
            if (pos.toIndex() == 4 || pos.toIndex() == 8){
                return new normalCandy(2);
            }
            else
                return new normalCandy(1);
        };
        candyBoard.fill(cellCreator);

        List<Position> lst = model.longestMatchDown(position);

        assert(lst.size() == 2);
    }

    @Test
    public void horizontalStartingPositionsTest(){
        BoardSize boardSize = new BoardSize(4,4);
        Board<Candy> candyBoard = new Board<>(boardSize);
        Model model = new Model(boardSize,candyBoard);

        Function<Position, Candy> cellCreator = pos -> {
            if(pos.toIndex() == 0 || pos.toIndex() == 4 || pos.toIndex() == 8 || pos.toIndex() == 12
                    || pos.toIndex() == 1 || pos.toIndex() == 5 || pos.toIndex() == 9 || pos.toIndex() == 13) {
                return new normalCandy(1);
            }
            else {
                return new normalCandy(2);
            }
        };
        candyBoard.fill(cellCreator);

        Stream<Position> stream = model.horizontalStartingPositions();

        System.out.println(stream.toList());
    }

    @Test
    public void findAllMatchesTest(){
        BoardSize boardSize = new BoardSize(4,4);
        Board<Candy> candyBoard = new Board<>(boardSize);
        Model model = new Model(boardSize,candyBoard);

        Function<Position, Candy> cellCreator = pos -> {
            if(pos.toIndex()%2 == 0){
                return new normalCandy(1);
            }
            else {
                return new normalCandy(2);
            }
        };

        candyBoard.fill(cellCreator);

        Set<List<Position>> matches = model.findAllMatches();

        System.out.println(matches.size());
    }

    @Test
    public void fallDownToTest(){
        BoardSize boardSize = new BoardSize(4,4);
        Board<Candy> candyBoard = new Board<>(boardSize);
        Model model = new Model(boardSize,candyBoard);

        Function<Position, Candy> cellCreator = pos -> {
            if (pos.toIndex() == 0 || pos.toIndex() == 1 || pos.toIndex() == 3 || pos.toIndex() == 5 || pos.toIndex() == 6 ||
                    pos.toIndex() == 8 || pos.toIndex() ==  10){
                return new normalCandy(1);
            }
            else {
                return new normalCandy(99);
            }
        };

        candyBoard.fill(cellCreator);

        model.fallDownTo(Position.fromIndex(12,boardSize));
        model.fallDownTo(Position.fromIndex(13,boardSize));
        model.fallDownTo(Position.fromIndex(14,boardSize));
        model.fallDownTo(Position.fromIndex(15,boardSize));

        System.out.println("After fall down");
    }

    @Test
    public void updateBoardTest(){
        BoardSize boardSize = new BoardSize(4,4);
        Board<Candy> candyBoard = new Board<>(boardSize);
        Model model = new Model(boardSize,candyBoard);

        Function<Position, Candy> cellCreator = pos -> {
            Random random = new Random();
            return new normalCandy(random.nextInt(2));
//            if (pos.toIndex() == 2 || pos.toIndex() == 3 || pos.toIndex() == 4 || pos.toIndex() == 5 || pos.toIndex() == 6 ||pos.toIndex() == 8 ||pos.toIndex() == 11 ||pos.toIndex() == 15){
//                return new normalCandy(0);
//            }
//            else return new normalCandy(2);
        };

        candyBoard.fill(cellCreator);

        model.updateBoard();
    }

    @Test
    public void maximizeScoreTest(){
        BoardSize boardSize = new BoardSize(4,4);
        Board<Candy> candyBoard = new Board<>(boardSize);
        Model model = new Model(boardSize,candyBoard);

        Function<Position, Candy> cellCreator = pos -> {
            int index = pos.toIndex();
            return switch (index) {
                case 0 -> new normalCandy(1);
                case 1, 3, 10, 14, 15 -> new normalCandy(3);
                case 2, 5, 7, 8, 12 -> new normalCandy(0);
                case 4, 6, 9 -> new normalCandy(2);
                case 11 -> new doublePointsCandy(6);
                case 13 -> new borderDeleteCandy(7);
                default -> null; // Handle invalid indexes if needed
            };
        };

        candyBoard.fill(cellCreator);

        candyBoard.printBoard();

        Board<Candy> boardToSolve = new Board<>(boardSize);
        candyBoard.copyTo(boardToSolve);

        model.maximizeScore();
    }
}
