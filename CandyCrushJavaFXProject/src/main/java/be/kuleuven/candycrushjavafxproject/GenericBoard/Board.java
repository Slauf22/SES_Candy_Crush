package be.kuleuven.candycrushjavafxproject.GenericBoard;

import be.kuleuven.candycrushjavafxproject.BoardSize;
import be.kuleuven.candycrushjavafxproject.Position;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.function.Function;

public class Board <T>{

    private ArrayList<T> gridData;
    private int rows;
    private int cols;
    private BoardSize boardSize;

    public Board(BoardSize boardSize){
        gridData = new ArrayList<>();
        this.rows = boardSize.rows();
        this.cols = boardSize.cols();
        this.boardSize = boardSize;
    }

    public T getCellAt(Position position){
        return gridData.get(position.toIndex());
    }

    public void replaceCellAt(Position position,T newCell){
        gridData.set(position.toIndex(), newCell);
    }

    public void fill(Function<Position, T> cellCreator){
        gridData.clear();

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                Position currentPosition = new Position(i+1,j+1,boardSize);
                T cell = cellCreator.apply(currentPosition);
                gridData.add(cell);
            }
        }
    }

    public void copyTo(Board<T> otherBoard){
        if (otherBoard.cols != this.cols && otherBoard.rows != this.rows){
            throw new IllegalArgumentException("The dimensions of the current grid and the received grid arent the same.");
        }

        for (int i = 0; i < rows * cols; i++) {
            otherBoard.gridData.add(this.gridData.get(i));
        }
    }
}
