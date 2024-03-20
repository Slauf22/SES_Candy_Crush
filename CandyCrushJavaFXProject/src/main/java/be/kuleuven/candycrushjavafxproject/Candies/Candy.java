package be.kuleuven.candycrushjavafxproject.Candies;

public sealed interface Candy permits borderDeleteCandy, doublePointsCandy, extraMoveCandy, normalCandy, rowDeleteCandy {
    int getColor();
}