package be.kuleuven.candycrushjavafxproject;

public record BoardSize(int rows, int cols) {
    public BoardSize {
        if (rows == 0 || cols == 0){
            throw new IllegalArgumentException("Rows and columns must be atleast 1 big.");
        }
    }
}
