package be.kuleuven.candycrushjavafxproject;

public record Position(int row, int col, BoardSize boardSize) {
    public Position {
        if (row == 0 || col == 0 || row > boardSize.rows() || col > boardSize.cols()) {
            throw new IllegalArgumentException("Specified cel position isnt in the grid. The first element has row 1 and column 1. The last is row " + boardSize.rows() + " and col is " + boardSize.cols() + ".");
        }
    }

    public int toIndex(){
        return (row - 1) * 4 + (col - 1);
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

}
