package main;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Controller {

    private Group root;

    // views
    private Rectangle rectangle;
    private Button randomVerticesGeneratorBtn;
    private Button runAlgorithmBtn;
    private Button saveBtn;

    public Controller(Group root) {
        this.root = root;
    }

    public void setScene(Group root) {
        setBoard();
        setMenu();
    }

    private void setBoard() {
        rectangle = new Rectangle(380, 25, 800, 800);
        rectangle.setStroke(Color.DARKGREY);
        rectangle.setStrokeWidth(2);
        rectangle.setFill(Color.gray(0.95));
        rectangle.setArcHeight(5);
        rectangle.setArcWidth(5);
        root.getChildren().add(rectangle);
    }

    private void setMenu() {
        randomVerticesGeneratorBtn = new Button("Generate Random Vertices");
        randomVerticesGeneratorBtn.setLayoutY(rectangle.getY() + 25);
        setupButtonView(randomVerticesGeneratorBtn);

        runAlgorithmBtn = new Button("Run Algorithm");
        runAlgorithmBtn.setLayoutY(randomVerticesGeneratorBtn.getLayoutY() + 50);
        setupButtonView(runAlgorithmBtn);

        saveBtn = new Button("Save");
        saveBtn.setLayoutY(runAlgorithmBtn.getLayoutY() + 50);
        setupButtonView(saveBtn);

    }

    private void setupButtonView(Button button) {
        button.setLayoutX(25);
        button.setMaxWidth(rectangle.getX() - 50);
        button.setMinWidth(rectangle.getX() - 50);
        button.setStyle("-fx-font: 16 sans;");
        root.getChildren().add(button);
    }

}
