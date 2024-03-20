package be.kuleuven.candycrushjavafxproject.Candies;

public record doublePointsCandy(int color) implements Candy{
    public doublePointsCandy{
        color = 6;
    }

    @Override
    public int getColor(){
        return color;
    }
}
