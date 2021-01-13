package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class Main extends Application {

    private Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull Stage primaryStage) {
        Group root = new Group();

        // set windows aspects and color
        Scene scene = new Scene(root, 1200, 850);
        scene.setFill(Color.rgb(225, 225, 225));

        // set the main window's title
        primaryStage.setTitle("Computing Optimal Islands");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        controller = new Controller(primaryStage, scene, root);

        setScene(root);

        primaryStage.show();
    }

    private void setScene(Group root) {
        controller.setScene();
    }

}
