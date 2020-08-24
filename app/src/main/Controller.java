package main;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Controller {

    // constants
    public static final Double SQUARE_BOARD_SIZE = 800.0;
    public static final Double SQUARE_BOARD_X = 380.0;
    public static final Double SQUARE_BOARD_Y = 25.0;
    private final Double BUTTONS_Y_SAFE_DISTANCE = 50.0;
    private final Group root;
    private final Scene scene;
    // buttons
    private Rectangle rectangle;
    private Button randomVerticesGeneratorBtn;
    private Button runAlgorithmBtn;
    private Button clearBtn;
    private Button saveBtn;
    // shapes
    private Circle circle;
    // local variables
    private final boolean isVerticesConfirmed = false;
    private final Graph graph;

    public Controller(Scene scene, Group root) {
        this.scene = scene;
        this.root = root;
        graph = new Graph(new ArrayList<>());
    }

    public void setScene() {
        setBoard();
        setMenu();
        setBoardClickable();
        setGenerateRandomVerticesButton();
        setClearButton();
    }

    private void setBoard() {
        rectangle = new Rectangle(SQUARE_BOARD_X, SQUARE_BOARD_Y, SQUARE_BOARD_SIZE, SQUARE_BOARD_SIZE);
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
        runAlgorithmBtn.setLayoutY(randomVerticesGeneratorBtn.getLayoutY() + BUTTONS_Y_SAFE_DISTANCE);
        setupButtonView(runAlgorithmBtn);

        clearBtn = new Button("Clear");
        clearBtn.setLayoutY(runAlgorithmBtn.getLayoutY() + BUTTONS_Y_SAFE_DISTANCE);
        setupButtonView(clearBtn);

        saveBtn = new Button("Save Data");
        saveBtn.setLayoutY(clearBtn.getLayoutY() + BUTTONS_Y_SAFE_DISTANCE);
        setupButtonView(saveBtn);

    }

    private void setupButtonView(Button button) {
        button.setLayoutX(25);
        button.setMaxWidth(rectangle.getX() - 50);
        button.setMinWidth(rectangle.getX() - 50);
        button.setStyle("-fx-font: 16 sans;");
        root.getChildren().add(button);
    }

    private void setBoardClickable() {
        scene.setOnMouseClicked(mouseEvent -> {
            if (!isVerticesConfirmed && isBoardClicked(mouseEvent) &&
                    !graph.isVertexCoordinationInvalid(mouseEvent.getX(), mouseEvent.getY()))
                root.getChildren().add(graph.addVertexOnClick(mouseEvent).getCircle());
        });
    }

    private boolean isBoardClicked(MouseEvent mouseEvent) {
        return rectangle.contains(mouseEvent.getX() + 10, mouseEvent.getY() + 10)
                && rectangle.contains(mouseEvent.getX() - 10, mouseEvent.getY() - 10);
    }

    private void setGenerateRandomVerticesButton() {
        randomVerticesGeneratorBtn.setOnMouseClicked(event -> {
            graph.randomVertexGenerator();
            for (Vertex v : graph.getVertexList())
                root.getChildren().add(v.getCircle());
        });
    }

    private void setClearButton() {

    }

}
