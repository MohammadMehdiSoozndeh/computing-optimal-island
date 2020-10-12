package main.graph;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static main.Controller.*;

public class Graph {

    public static final int RANDOM_VERTICES_NUMBER_ORIGIN = 50;
    public static final int RANDOM_VERTICES_NUMBER_BOUND = 150;
    public static final int VERTEX_RADIUS = 5;
    private List<Vertex> vertexList;

    public Graph(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public void randomVertexGenerator() {
        int vertexRandomNum =
                ThreadLocalRandom.current().nextInt(RANDOM_VERTICES_NUMBER_ORIGIN, RANDOM_VERTICES_NUMBER_BOUND);
        for (int i = 0; i < vertexRandomNum; i++) {
            double randomX = ThreadLocalRandom.current().nextDouble
                    (SQUARE_BOARD_X + 15, SQUARE_BOARD_X + SQUARE_BOARD_SIZE - 15);
            double randomY = ThreadLocalRandom.current().nextDouble
                    (SQUARE_BOARD_Y + 15, SQUARE_BOARD_Y + SQUARE_BOARD_SIZE - 15);

            if (isVertexCoordinationInvalid(randomX, randomY)) {
                i--;
            } else {
                Circle circle = new Circle(randomX, randomY, VERTEX_RADIUS);
                circle.setFill(Graph.VertexColorGenerator.getColor());
                circle.setOnMouseClicked(event -> {
                    circle.setFill(VertexColorGenerator.getNextColor(circle.getFill().toString()));
                    System.out.println("X:" + (float) circle.getCenterX() + " Y:" + (float) circle.getCenterY());
                });
                vertexList.add(new Vertex(circle, i + ""));
            }
        }
    }

    public Vertex addVertexOnClick(MouseEvent mouseEvent) {
        Circle circle = new Circle(mouseEvent.getX(), mouseEvent.getY(), VERTEX_RADIUS);
        circle.setFill(VertexColorGenerator.getColor());
        circle.setOnMouseClicked(event -> {
            circle.setFill(VertexColorGenerator.getNextColor(circle.getFill().toString()));
            System.out.println("X:" + circle.getCenterX() + " Y:" + circle.getCenterY());
        });

        Vertex vertex;
        vertex = new Vertex(circle);
        vertexList.add(vertex);
        return vertex;
    }

    public boolean isVertexCoordinationInvalid(double x, double y) {
        for (Vertex v : vertexList) {
            if (v.isTooClose(x, y)) {
                errorCreatingVertex();
                return true;
            }
        }
        return false;
    }

    private void errorCreatingVertex() {
    }

    public void addVertex(Vertex vertex) {
        vertexList.add(vertex);
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public void sortY() {
        vertexList.sort(new SortByY());
    }

    public static class VertexColorGenerator {
        private static int index = 0;

        private static int RANDOM_COLOR_BOUND = 2;

        public VertexColorGenerator() {
            index = 0;
        }

        public static Color getColor() {
            index = ThreadLocalRandom.current().nextInt(0, RANDOM_COLOR_BOUND);

            switch (index) {
                default:
                case 0:
                    return Color.BLUE;
                case 1:
                    return Color.RED;
                case 2:
                    return Color.GREEN;
                case 3:
                    return Color.MAGENTA;
            }

        }

        public static Color getNextColor(String color) {
            if (color.equals(Color.BLUE.toString()))
                return Color.RED;
            else if (color.equals(Color.RED.toString()))
                return RANDOM_COLOR_BOUND == 2 ? Color.BLUE : Color.GREEN;
            else if (color.equals(Color.GREEN.toString()))
                return RANDOM_COLOR_BOUND == 3 ? Color.BLUE : Color.MAGENTA;
            else if (color.equals(Color.MAGENTA.toString()))
                return Color.BLUE;
            return Color.RED;
        }

        public static void colorOptionDialog() {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Color Option");
            alert.setHeaderText("Color Variant");
            alert.setContentText("How Many Type of color do you want?");

            ButtonType buttonTypeTwo = new ButtonType("Two");
            ButtonType buttonTypeThree = new ButtonType("Three");
            ButtonType buttonTypeFour = new ButtonType("Four");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeTwo, buttonTypeThree, buttonTypeFour, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeTwo) {
                RANDOM_COLOR_BOUND = 2;
            } else if (result.isPresent() && result.get() == buttonTypeThree) {
                RANDOM_COLOR_BOUND = 3;
            } else if (result.isPresent() && result.get() == buttonTypeFour) {
                RANDOM_COLOR_BOUND = 4;
            } else if (result.isPresent() && result.get() == buttonTypeCancel) {
                System.out.println("color choose canceled");
            }
        }

    }

    private static class SortByY implements Comparator<Vertex> {
        @Override
        public int compare(Vertex o1, Vertex o2) {
            return (int) (o1.getCircle().getCenterY() - o2.getCircle().getCenterY());
        }
    }

}
