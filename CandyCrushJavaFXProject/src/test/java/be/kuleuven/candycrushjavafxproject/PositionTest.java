package be.kuleuven.candycrushjavafxproject;

import javafx.geometry.Pos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PositionTest {
    @Test
    public void BoardSizeWith2Rows4ColumnsPositionRow1AndColumn2ToIndex1(){
        BoardSize boardSize = new BoardSize(2,4);
        Position position = new Position(1,2,boardSize);

        assert(position.toIndex() == 1);
    }

    @Test
    public void GetPositionWithRow1AndColumn4WhenGivenIndex3(){
        BoardSize boardSize = new BoardSize(2,4);

        Position returnedPosition = Position.fromIndex(3,boardSize);

        assert (returnedPosition.row() == 1 && returnedPosition.col() == 4);
    }

    @Test
    public void ThrowExceptionWhenInvalidInputInFromIndexFunctionInPosition(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           BoardSize boardSize = new BoardSize(2,4);
           Position position = new Position(1,1,boardSize);

           Position returnedPosition = position.fromIndex(10,boardSize);
        });
    }

    // 0 1 2 3
    // 4 5 6 7
    // 8 9 10 11
    // 12 13 14 15
    @Test
    public void In4x4GridGetNeighboursOfIndex5ResultsAreIndeces0_1_2_4_6_8_9_10(){
        BoardSize boardSize = new BoardSize(4,4);
        Position position = new Position(2,2, boardSize);

        ArrayList<Position> neighbourPositions = new ArrayList<>();
        neighbourPositions = (ArrayList<Position>) position.neighboursPositions();

        assert (
                neighbourPositions.get(0).toIndex() == 0 &&
                neighbourPositions.get(1).toIndex() == 1 &&
                neighbourPositions.get(2).toIndex() == 2 &&
                neighbourPositions.get(3).toIndex() == 4 &&
                neighbourPositions.get(4).toIndex() == 6 &&
                neighbourPositions.get(5).toIndex() == 8 &&
                neighbourPositions.get(6).toIndex() == 9 &&
                neighbourPositions.get(7).toIndex() == 10
        );
    }
}
