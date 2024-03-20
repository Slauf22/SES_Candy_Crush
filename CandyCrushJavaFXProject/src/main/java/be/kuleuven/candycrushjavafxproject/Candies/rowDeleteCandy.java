package be.kuleuven.candycrushjavafxproject.Candies;

public record rowDeleteCandy(int color) implements Candy{
    public rowDeleteCandy{
        color = 4;
    }

    @Override
    public int getColor(){
        return color;
    }
}
