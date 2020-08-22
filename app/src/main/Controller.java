package main;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.ThreadLocalRandom;

public class Controller {

    private Group root;
    private Scene scene;

    // buttons
    private Rectangle rectangle;
    private Button randomVerticesGeneratorBtn;
    private Button runAlgorithmBtn;
    private Button clearBtn;
    private Button saveBtn;

    // shapes
    private Circle circle;

    // local variables
    private boolean isVerticesConfirmed = false;

    // constants
    private final int VERTEX_RADIUS = 6;
    private final Double SQUARE_BOARD_SIZE = 800.0;
    private final Double SQUARE_BOARD_X = 380.0;
    private final Double SQUARE_BOARD_Y = 25.0;
    private final Double BUTTONS_Y_SAFE_DISTANCE = 50.0;
    private final int RANDOM_VERTICES_NUMBER = 50;

    public Controller(Scene scene, Group root) {
        this.scene = scene;
        this.root = root;
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
            if (!isVerticesConfirmed) {
                if (rectangle.contains(mouseEvent.getX() + 15, mouseEvent.getY() + 15)
                        && rectangle.contains(mouseEvent.getX() - 15, mouseEvent.getY() - 15)
                ) {
                    addVertexOnClick(mouseEvent);
                }
            }

        });
    }


    private void addVertexOnClick(MouseEvent mouseEvent) {
        circle = new Circle(mouseEvent.getX(), mouseEvent.getY(), VERTEX_RADIUS);
        circle.setFill(VertexColorGenerator.getColor());
//        Vertex vertex;
//        vertex = new Vertex(this.circle, firstIndex + "");
//        System.out.println("add: " + firstIndex);
//        firstIndex++;
//        vertices.add(vertex);
        root.getChildren().add(this.circle);
    }

    private void addVertexOnRandomGenerator() {
        for (int i = 0; i < RANDOM_VERTICES_NUMBER; i++) {
            double randomX = ThreadLocalRandom.current().nextDouble
                    (SQUARE_BOARD_X + 15, SQUARE_BOARD_X + SQUARE_BOARD_SIZE - 15);
            double randomY = ThreadLocalRandom.current().nextDouble
                    (SQUARE_BOARD_Y + 15, SQUARE_BOARD_Y + SQUARE_BOARD_SIZE - 15);

            circle = new Circle(randomX, randomY, VERTEX_RADIUS);
            circle.setFill(VertexColorGenerator.getColor());
            root.getChildren().add(this.circle);
        }
    }

    private void setGenerateRandomVerticesButton() {
        randomVerticesGeneratorBtn.setOnMouseClicked(event -> {
            addVertexOnRandomGenerator();
        });
    }

    private void setClearButton() {

    }

    public static class VertexColorGenerator {
        private static int index = 0;

        public VertexColorGenerator() {
            index = 0;
        }

        public static Color getColor() {
            if (index == 0) {
                index = 1;
                return Color.BLUE;
            } else {
                index = 0;
                return Color.RED;
            }
        }
    }

}
