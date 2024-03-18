package be.kuleuven.candycrushjavafxproject;

public record Position(int row, int col, BoardSize boardSize) {
    public Position {
        if (row == 0 || col == 0 || row > boardSize.rows() || col > boardSize.cols()) {
            throw new IllegalArgumentException("Specified cel position isnt in the grid. The first element has row 1 and column 1. The last is row " + boardSize.rows() + " and col is " + boardSize.cols() + ".");
        }
    }
}
