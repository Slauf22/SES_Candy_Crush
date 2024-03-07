module be.kuleuven.candycrushjavafxproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires be.kuleuven.checkneighbours;

    opens be.kuleuven.candycrushjavafxproject to javafx.fxml;
    exports be.kuleuven.candycrushjavafxproject;
}