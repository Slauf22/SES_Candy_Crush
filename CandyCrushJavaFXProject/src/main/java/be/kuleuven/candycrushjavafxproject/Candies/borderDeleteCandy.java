package be.kuleuven.candycrushjavafxproject.Candies;

public record borderDeleteCandy(int color) implements Candy {
    public borderDeleteCandy{
        color = 8;
    }

    @Override
    public int getColor(){
        return color;
    }
}
