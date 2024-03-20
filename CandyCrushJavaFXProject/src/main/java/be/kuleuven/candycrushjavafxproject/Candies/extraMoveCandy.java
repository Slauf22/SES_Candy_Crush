package be.kuleuven.candycrushjavafxproject.Candies;

public record extraMoveCandy(int color) implements Candy{
    public extraMoveCandy{
        color = 5;
    }

    @Override
    public int getColor(){
        return color;
    }
}
