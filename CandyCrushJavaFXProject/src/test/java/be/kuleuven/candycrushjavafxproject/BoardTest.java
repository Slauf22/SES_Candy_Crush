package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.candycrushjavafxproject.Candies.Candy;
import be.kuleuven.candycrushjavafxproject.Candies.normalCandy;
import be.kuleuven.candycrushjavafxproject.Candies.rowDeleteCandy;
import be.kuleuven.candycrushjavafxproject.GenericBoard.Board;
import org.junit.jupiter.api.Test;


import java.util.function.Function;

public class BoardTest {
    @Test
    public void boardFillWithCandiesWithHardcodedCandies(){
        BoardSize boardSize = new BoardSize(4,4);
        Board<Candy> board = new Board<>(boardSize);

        Function<Position, Candy> cellCreator = position -> {
            if (position.isLastColumn()){
                return new rowDeleteCandy(4);
            }
            else{
                return new normalCandy(1);
            }
        };

        board.fill(cellCreator);

        assert( board.getCellAt(Position.fromIndex(0,boardSize)).getColor() == 1 &&
                board.getCellAt(Position.fromIndex(15,boardSize)).getColor() == 4 &&
                board.getCellAt(Position.fromIndex(5,boardSize)).getColor() == 1);
    }

    @Test
    public void copyBoardAndCompareCellsCandies(){
        BoardSize boardSize = new BoardSize(4,4);
        Board<Candy> board = new Board<>(boardSize);

        Function<Position, Candy> cellCreator = position -> {
            if (position.isLastColumn()){
                return new rowDeleteCandy(4);
            }
            else{
                return new normalCandy(1);
            }
        };

        board.fill(cellCreator);

        Board<Candy> board2 = new Board<>(boardSize);
        board.copyTo(board2);

        boolean same = true;

        for (int i = 0; i < boardSize.rows()*boardSize.cols(); i++){
            if (board.getCellAt(Position.fromIndex(i, boardSize)).getColor() != board2.getCellAt(Position.fromIndex(i, boardSize)).getColor()){
                same = false;
                break;
            }
        }

        assert(same);
    }

}
