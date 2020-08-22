package main;

import javafx.scene.shape.Circle;

public class Vertex {

    private Circle circle;
    private String label;

    public Vertex(Circle circle, String label) {
        this.circle = circle;
        this.label = label;
    }

    public Vertex(Circle circle) {
        this.circle = circle;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
