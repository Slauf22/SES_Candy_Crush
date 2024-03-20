package be.kuleuven.candycrushjavafxproject.Candies;

public record normalCandy(int color) implements Candy {
    public normalCandy{
        if (color < 0 || color > 3){
            throw new IllegalArgumentException("Color must be between 0 and 3");
        }
    }
}
