package be.kuleuven.candycrushjavafxproject.GenericBoard;

import be.kuleuven.candycrushjavafxproject.BoardSize;
import be.kuleuven.candycrushjavafxproject.Position;

import java.util.*;
import java.util.function.Function;

public class Board <T>{

    private final HashMap<Position, T> positionToCellMap;
    private final HashMap<T, Set<Position>> cellToPositionMap;
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

    public synchronized T getCellAt(Position position){
        return positionToCellMap.get(position);
    }
    
    public synchronized void replaceCellAt(Position position,T newCell){
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

    public synchronized void fill(Function<Position, T> cellCreator){
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

    public synchronized void copyTo(Board<T> otherBoard) {
        if (otherBoard.cols != this.cols || otherBoard.rows != this.rows) {
            throw new IllegalArgumentException("The dimensions of the current grid and the received grid are not the same.");
        }

        otherBoard.positionToCellMap.putAll(this.positionToCellMap);

        otherBoard.cellToPositionMap.putAll(this.cellToPositionMap);
    }

    public synchronized Set<Position> getPositionsOfElement(T cell){
        return Collections.unmodifiableSet(cellToPositionMap.get(cell));
    }

    public boolean swapTwoPositions(Position pos1, Position pos2){
        if ((pos2.row() == pos1.row() - 1 && pos2.col() == pos1.col()) || (pos2.row() == pos1.row() + 1 && pos2.col() == pos1.col()) || (pos2.row() == pos1.row() && pos2.col() == pos1.col() - 1) || (pos2.row() == pos1.row() && pos2.col() == pos1.col() + 1)){
            T temp = positionToCellMap.get(pos1);
            positionToCellMap.put(pos1,positionToCellMap.get(pos2));
            positionToCellMap.put(pos2, temp);
            return true;
        }else return false;
    }
}
