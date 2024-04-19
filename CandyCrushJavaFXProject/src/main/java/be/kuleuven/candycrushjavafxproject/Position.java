package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.CheckNeighboursInGrid;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.stream.Stream;

public record Position(int row, int col, BoardSize boardSize) {
    public Position {
        if (row == 0 || col == 0 || row > boardSize.rows() || col > boardSize.cols()) {
            throw new IllegalArgumentException("Specified cel position isnt in the grid. The first element has row 1 and column 1. The last is row " + boardSize.rows() + " and col is " + boardSize.cols() + ".");
        }
    }

    public int toIndex(){
        return (row - 1) * boardSize.cols() + (col - 1);
    }

    public static Position fromIndex(int index, BoardSize size){
        if (index > (size.cols()* size.rows())-1)
        {
            throw new IllegalArgumentException("Index passed to fromIndex function is bigger then the maximum index in the boardsize");
        }

        if (index < 0)
        {
            throw new IllegalArgumentException("Index passed to fromIndex is below 0");
        }

        int r = index / size.cols() + 1;
        int c = (index - (r - 1) * size.cols()) + 1;

        return new Position(r,c,size);
    }

    public Iterable<Position> neighboursPositions(){
        ArrayList<Position> neighbourPositions = new ArrayList<>();
        Position position;

        if (row - 1 > 0){ // up
            if (col - 1 > 0){ // left up
                position = new Position(this.row-1,this.col-1,this.boardSize);
                neighbourPositions.add(position);
            }

            position = new Position(this.row-1,this.col,this.boardSize);
            neighbourPositions.add(position);

            if (col + 1 <= boardSize.cols()){ // right up
                position = new Position(this.row-1,this.col+1,this.boardSize);
                neighbourPositions.add(position);
            }
        }

        if (col - 1 > 0){// left
            position = new Position(this.row,this.col - 1, this.boardSize);
            neighbourPositions.add(position);
        }

        if (col + 1 <= boardSize.cols()){// right
            position = new Position(this.row,this.col + 1, this.boardSize);
            neighbourPositions.add(position);
        }

        if (row + 1 <= boardSize.rows()){ // down
            if (col - 1 > 0){ // left down
                position = new Position(this.row+1,this.col-1,this.boardSize);
                neighbourPositions.add(position);
            }

            position = new Position(this.row+1,this.col,this.boardSize);
            neighbourPositions.add(position);

            if (col + 1 <= boardSize.cols()){ // right down
                position = new Position(this.row+1,this.col+1,this.boardSize);
                neighbourPositions.add(position);
            }
        }
        return neighbourPositions;
    }

    public boolean isLastColumn(){
        return col == boardSize.cols();
    }

    public Stream<Position> walkLeft(){
        return Stream.iterate(this, pos -> new Position(pos.row(), pos.col() - 1, this.boardSize()))
                .limit(col - 1);
    }

    public Stream<Position> walkRight(){
        return Stream.iterate(this, pos -> new Position(pos.row(), pos.col() + 1, this.boardSize()))
                .limit(col + 1);
    }

    public Stream<Position> walkUp(){
        return Stream.iterate(this, pos -> new Position(pos.row() - 1, pos.col(), this.boardSize()))
                .limit(row - 1);
    }

    public Stream<Position> walkDown(){
        return Stream.iterate(this, pos -> new Position(pos.row() + 1, pos.col(), this.boardSize()))
                .limit(row + 1);
    }
}
