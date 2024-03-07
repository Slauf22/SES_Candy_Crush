package be.kuleuven.candycrushjavafxproject;

import javafx.application.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewTest{
    private View view;

    @BeforeEach
    public void setUp() {
        view = new View();
    }
    @Test
    public void UpdateLabelInGameSchermNaarSpelerNaamInputRAUF()
    {
        // Set player name
        view.PlayerName = "John";

        // Update name label
        view.updateNameLabel();

        // Assert that the label text has been updated correctly
        assertEquals("John", view.NameLabel.getText());
    }
}
