package be.kuleuven.candycrushjavafxproject;

import javafx.geometry.Pos;

import java.util.ArrayList;

public record BoardSize(int rows, int cols) {
    public BoardSize {
        if (rows == 0 || cols == 0){
            throw new IllegalArgumentException("Rows and columns must be atleast 1 big.");
        }
    }
    
    public Iterable<Position> positions(){
        ArrayList<Position> positions = new ArrayList<Position>();

        for (int row = 1; row <= rows; row++){
            for (int col = 1; col <= cols; col++){
                positions.add(new Position(row,col,this));
            }
        }

        return positions;
    }
}
