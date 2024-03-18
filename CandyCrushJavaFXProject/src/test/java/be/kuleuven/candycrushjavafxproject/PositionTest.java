package be.kuleuven.candycrushjavafxproject;

import javafx.geometry.Pos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
