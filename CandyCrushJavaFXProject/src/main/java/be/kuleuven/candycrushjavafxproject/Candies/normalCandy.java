package be.kuleuven.candycrushjavafxproject.Candies;

public record normalCandy(int color) implements Candy {
    @Override
    public int getColor(){
        return color;
    }
}
