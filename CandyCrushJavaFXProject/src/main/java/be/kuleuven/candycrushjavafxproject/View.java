package be.kuleuven.candycrushjavafxproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class View{

    ////////////////////
    //Member Functions//
    ////////////////////

    public void CreateLoginWindow(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("MainScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 632, 435);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void CreateGameWindow(Stage stage, Controller controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Controller.class.getResource("GameScreen.fxml"));
        fxmlLoader.setController(controller);

        Scene scene = new Scene(fxmlLoader.load(), 632, 435);

        stage.setScene(scene);
        stage.setResizable(false);
    }
}
