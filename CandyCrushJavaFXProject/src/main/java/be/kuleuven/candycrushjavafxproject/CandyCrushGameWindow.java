package be.kuleuven.candycrushjavafxproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class CandyCrushGameWindow {
    private final Stage stage;

    public CandyCrushGameWindow(Stage stage)
    {
        this.stage = stage;
    }

    public void CreateWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CandyCrushGameWindow.class.getResource("GameScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 632, 435);

        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }
}
