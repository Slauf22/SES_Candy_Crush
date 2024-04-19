package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.candycrushjavafxproject.Candies.Candy;
import be.kuleuven.candycrushjavafxproject.GenericBoard.Board;

import java.util.Random;
import java.util.function.Function;

public class MultithreadingClient {
    public static void main(String[] args) {
        BoardSize boardSize = new BoardSize(4,4);
        Board<Candy> board = new Board<>(boardSize);
        Model model = new Model(boardSize);

        Function<Position, Candy> cellCreator = position -> {
            return model.GenerateRandomCandy();
        };

        board.fill(cellCreator);
        Random random = new Random();

        var t1 = new Thread(() -> {
            while (true) {
                Position position = Position.fromIndex(random.nextInt(16), boardSize);
                Candy newCandy = model.GenerateRandomCandy();

                board.replaceCellAt(position, newCandy);
            }
        });

        var t2 = new Thread(() -> {
            while (true) {
                Position position = Position.fromIndex(random.nextInt(16), boardSize);
                Candy newCandy = model.GenerateRandomCandy();

                board.replaceCellAt(position, newCandy);
            }
        });

        t1.start();
        t2.start();
    }
}
