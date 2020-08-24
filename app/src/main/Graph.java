package main;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static main.Controller.*;

public class Graph {

    private List<Vertex> vertexList;

    public static final int RANDOM_VERTICES_NUMBER_ORIGIN = 30;
    public static final int RANDOM_VERTICES_NUMBER_BOUND = 80;
    public static final int VERTEX_RADIUS = 6;

    public Graph(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public List<Vertex> randomVertexGenerator() {
        int vertexRandomNum = ThreadLocalRandom.current().nextInt(RANDOM_VERTICES_NUMBER_ORIGIN, RANDOM_VERTICES_NUMBER_BOUND);
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
                vertexList.add(new Vertex(circle));
            }
        }
        return vertexList;

    }

    public Vertex addVertexOnClick(MouseEvent mouseEvent) {
        Circle circle = new Circle(mouseEvent.getX(), mouseEvent.getY(), VERTEX_RADIUS);
        circle.setFill(VertexColorGenerator.getColor());
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

    public static class VertexColorGenerator {
        private static int index = 0;

        public VertexColorGenerator() {
            index = 0;
        }

        public static Color getColor() {
            index = ThreadLocalRandom.current().nextInt(0, 2);
            if (index == 0) {
                return Color.BLUE;
            } else {
                return Color.RED;
            }
        }
    }
}
