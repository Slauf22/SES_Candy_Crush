package be.kuleuven.candycrushjavafxproject;

import be.kuleuven.candycrushjavafxproject.Candies.Candy;
import be.kuleuven.candycrushjavafxproject.Candies.borderDeleteCandy;
import be.kuleuven.candycrushjavafxproject.Candies.normalCandy;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class View{

    ////////////////////
    //Member Variables//
    ////////////////////

    private final double circleRadius = 5;
    private final double rectHeight = 10;
    private final double rectWidth = 10;

    private Controller controller;

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

        this.controller = controller;

        Scene scene = new Scene(fxmlLoader.load(), 632, 435);

        stage.setScene(scene);
        stage.setResizable(true);
    }

    Node makeCandyShape(Position position, Candy candy){
        Node node = null;
        Circle circle;
        Rectangle rectangle;

        switch (candy.getColor()) {
            case 0:
                circle = new Circle();
                circle.setFill(Color.RED);
                circle.setRadius(circleRadius);
                node = circle;
                break;

            case 1:
                circle = new Circle();
                circle.setFill(Color.BLUE);
                circle.setRadius(circleRadius);
                node = circle;
                break;

            case 2:
                circle = new Circle();
                circle.setFill(Color.GREEN);
                circle.setRadius(circleRadius);
                node = circle;
                break;

            case 3:
                circle = new Circle();
                circle.setFill(Color.PINK);
                circle.setRadius(circleRadius);
                node = circle;
                break;

            //Special candies
            //rowDeleteCandy
            case 4:
                rectangle = new Rectangle();
                rectangle.setFill(Color.YELLOW);
                rectangle.setHeight(rectHeight);
                rectangle.setWidth(rectWidth);
                node = rectangle;
                break;

            //extraMoveCandy
            case 5:
                rectangle = new Rectangle();
                rectangle.setFill(Color.DEEPSKYBLUE);
                rectangle.setHeight(rectHeight);
                rectangle.setWidth(rectWidth);
                node = rectangle;
                break;

            //doublePointsCandy
            case 6:
                rectangle = new Rectangle();
                rectangle.setFill(Color.PURPLE);
                rectangle.setHeight(rectHeight);
                rectangle.setWidth(rectWidth);
                node = rectangle;
                break;

            //borderDeleteCandy
            case 7:
                rectangle = new Rectangle();
                rectangle.setFill(Color.CYAN);
                rectangle.setHeight(rectHeight);
                rectangle.setWidth(rectWidth);
                node = rectangle;
                break;

            //borderDeleteCandy
            case 99:
                rectangle = new Rectangle();
                rectangle.setFill(Color.CYAN);
                rectangle.setHeight(rectHeight);
                rectangle.setWidth(rectWidth);
                rectangle.setOpacity(0.0);
                node = rectangle;
                break;
        }

        if (node == null) {throw new NullPointerException();}

        node.setLayoutX(232.0 + ((position.col()-1) * 50));
        node.setLayoutY(170.0 + ((position.row()-1) * 50));

        node.setId("lbl" + position.row() + "x" + position.col());

        node.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                controller.HandleLabelClick(event);
            }
        });

        return node;
    }

    public void highlightNode(Node node){
        if (node instanceof Circle){
            Circle outlineCircle = new Circle();
            outlineCircle.setRadius(circleRadius);
            outlineCircle.setStroke(Color.BLACK);
            outlineCircle.setFill(Color.TRANSPARENT);
            outlineCircle.setStrokeWidth(2);
            outlineCircle.setLayoutX(node.getLayoutX());
            outlineCircle.setLayoutY(node.getLayoutY());
            outlineCircle.setId(node.getId() + "H");
            outlineCircle.setMouseTransparent(true);

            ((Pane) node.getParent()).getChildren().add(outlineCircle);
        }
        else if (node instanceof Rectangle){
            Rectangle outlineRectangle = new Rectangle();
            outlineRectangle.setWidth(rectWidth);
            outlineRectangle.setHeight(rectHeight);
            outlineRectangle.setStroke(Color.BLACK);
            outlineRectangle.setFill(Color.TRANSPARENT);
            outlineRectangle.setStrokeWidth(2);
            outlineRectangle.setLayoutX(node.getLayoutX());
            outlineRectangle.setLayoutY(node.getLayoutY());
            outlineRectangle.setId(node.getId() + "H");
            outlineRectangle.setMouseTransparent(true);

            ((Pane) node.getParent()).getChildren().add(outlineRectangle);
        }
        else {
            throw new IllegalArgumentException("Node isnt a circle or rectangle");
        }
    }

    public void clearHighlights(Node highlightedNode) {
        Node highlight = highlightedNode.getParent().lookup("#" + highlightedNode.getId() + "H");
        if (highlight != null) {
            ((Pane) highlightedNode.getParent()).getChildren().remove(highlight);
        }
    }
}
