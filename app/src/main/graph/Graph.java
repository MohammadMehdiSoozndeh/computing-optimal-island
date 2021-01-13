package main.graph;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static main.Controller.*;

public class Graph {

    // Boundaries of random points generator
    // TODO: 1/13/2021 set these two boundaries as you wish
    public static final int RANDOM_VERTICES_NUMBER_ORIGIN = 70; // min
    public static final int RANDOM_VERTICES_NUMBER_BOUND = 75;  // max

    public static final int VERTEX_RADIUS = 5;

    private final List<Vertex> vertexList;
    private List<Line> lineList;

    private List<String> islandBorderList;
    private int maxW;

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
                vertexList.add(new Vertex(circle, vertexList.size() + ""));
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
        vertex = new Vertex(circle, vertexList.size() + "");
        vertexList.add(vertex);
        return vertex;
    }

    public boolean isVertexCoordinationInvalid(double x, double y) {
        for (Vertex v : vertexList) {
            if (v.isTooClose(x, y)
                    || (Math.abs(v.getCircle().getCenterX() - x) < 0.1)
                    || (Math.abs(v.getCircle().getCenterY() - y) < 0.1)
                    || isThreePointOnLine(x, y)
            ) {
                errorCreatingVertex();
                return true;
            }
        }
        return false;
    }

    private boolean isThreePointOnLine(double x, double y) {
        for (Vertex v : vertexList) {
            for (Vertex o : vertexList) {
                if (v.equals(o)) continue;
                double a = Utils.calculateAofLine(v, o);
                double b = Utils.calculateBofLine(o, a);
                if (Math.abs(y - (a * x + b)) < 0.1)
                    return true;
            }
        }
        return false;
    }

    private void errorCreatingVertex() {
        System.out.println("error creating vertex");
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public void sortY() {
        vertexList.sort(new SortByY());
    }

    @Override
    public String toString() {
        return " Graph {" +
                "vertexList=" + vertexList +
                '}';
    }

    public void clearGraph() {
        vertexList.clear();
        if (lineList != null) lineList.clear();
        if (islandBorderList != null) islandBorderList.clear();
    }

    public List<Line> getLineList() {
        if (lineList == null) lineList = new ArrayList<>();
        return lineList;
    }

    public void setLineList(List<Line> lineList) {
        this.lineList = lineList;
    }

    public void addLine(Line line) {
        if (lineList == null)
            lineList = new ArrayList<>();
        lineList.add(line);
    }

    public List<String> getIslandBorderList() {
        return islandBorderList;
    }

    public void setIslandBorderList(List<String> islandBorderList) {
        this.islandBorderList = islandBorderList;
    }

    public void addBorder(String str) {
        if (islandBorderList == null)
            islandBorderList = new ArrayList<>();
        islandBorderList.add(str);
    }

    public int getMaxW() {
        return maxW;
    }

    public void setMaxW(int maxW) {
        this.maxW = maxW;
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
            return (int) ((o1.getCircle().getCenterY() - o2.getCircle().getCenterY()) * 1000);
        }
    }
}
