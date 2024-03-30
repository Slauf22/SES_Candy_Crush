package be.kuleuven.candycrushjavafxproject.GenericBoard;

import be.kuleuven.candycrushjavafxproject.BoardSize;
import be.kuleuven.candycrushjavafxproject.Position;
import javafx.geometry.Pos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class Board <T>{

    private final HashMap<Position, T> positionToCellMap;
    private HashMap<T, Set<Position>> cellToPositionMap;
    private final int rows;
    private final int cols;
    private final BoardSize boardSize;

    public Board(BoardSize boardSize){
        positionToCellMap = new HashMap<>();
        cellToPositionMap = new HashMap<>();

        this.rows = boardSize.rows();
        this.cols = boardSize.cols();
        this.boardSize = boardSize;
    }

    public T getCellAt(Position position){
        return positionToCellMap.get(position);
    }

    public void replaceCellAt(Position position,T newCell){
        T oldCell = positionToCellMap.get(position);

        positionToCellMap.put(position, newCell);

        // If there is an old cell, remove the corresponding position
        if (oldCell != null) {
            Set<Position> oldPositions = cellToPositionMap.get(oldCell);
            oldPositions.remove(position);

            if (oldPositions.isEmpty()) {
                cellToPositionMap.remove(oldCell);
            }
        }

        // Update the new cell in the inverse map
        Set<Position> positions = cellToPositionMap.getOrDefault(newCell, new HashSet<>());
        positions.add(position);
        cellToPositionMap.put(newCell, positions);
    }

    public void fill(Function<Position, T> cellCreator){
        positionToCellMap.clear();

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                Position currentPosition = new Position(i+1,j+1,boardSize);

                T cell = cellCreator.apply(currentPosition);

                positionToCellMap.put(currentPosition, cell);

                Set<Position> positionSet = cellToPositionMap.getOrDefault(cell, new HashSet<>());
                positionSet.add(currentPosition);
                cellToPositionMap.put(cell, positionSet);
            }
        }
    }

    public void copyTo(Board<T> otherBoard) {
        if (otherBoard.cols != this.cols || otherBoard.rows != this.rows) {
            throw new IllegalArgumentException("The dimensions of the current grid and the received grid are not the same.");
        }

        otherBoard.positionToCellMap.putAll(this.positionToCellMap);

        otherBoard.cellToPositionMap.putAll(this.cellToPositionMap);
    }

}
