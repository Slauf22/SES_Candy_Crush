package be.kuleuven.candycrushjavafxproject.GenericBoard;

import be.kuleuven.candycrushjavafxproject.BoardSize;
import be.kuleuven.candycrushjavafxproject.Candies.Candy;
import be.kuleuven.candycrushjavafxproject.Position;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Board <T>{

    private final HashMap<Position, T> gridData;
    private final int rows;
    private final int cols;
    private final BoardSize boardSize;

    public Board(BoardSize boardSize){
        gridData = new HashMap<>();
        this.rows = boardSize.rows();
        this.cols = boardSize.cols();
        this.boardSize = boardSize;
    }

    public T getCellAt(Position position){
        return gridData.get(position);
    }

    public void replaceCellAt(Position position,T newCell){
        gridData.put(position, newCell);
    }

    public void fill(Function<Position, T> cellCreator){
        gridData.clear();

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                Position currentPosition = new Position(i+1,j+1,boardSize);
                T cell = cellCreator.apply(currentPosition);
                gridData.put(currentPosition, cell);
            }
        }
    }

    public void copyTo(Board<T> otherBoard){
        if (otherBoard.cols != this.cols && otherBoard.rows != this.rows){
            throw new IllegalArgumentException("The dimensions of the current grid and the received grid arent the same.");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Position currentPosition = new Position(i + 1,j+1, boardSize);

                otherBoard.gridData.put(currentPosition,this.gridData.get(currentPosition));
            }
        }
    }
}
