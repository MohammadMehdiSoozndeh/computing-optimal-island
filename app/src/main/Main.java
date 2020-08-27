package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        Scene scene = new Scene(root, 1200, 850);
        scene.setFill(Color.rgb(225, 225, 225));

        primaryStage.setTitle("Computing Optimal Islands");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        controller = new Controller(scene, root);

        setScene(root);

        primaryStage.show();
    }

    private void setScene(Group root) {
        controller.setScene();
    }

}
