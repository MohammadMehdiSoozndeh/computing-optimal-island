package main.graph;

import javafx.scene.shape.Circle;

public class Point extends Vertex {

    private int label;
    private double angel;

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public double getAngel() {
        return angel;
    }

    public void setAngel(double angel) {
        this.angel = angel;
    }

    public Point(Circle circle, String globalLabel) {
        super(circle, globalLabel);
    }

    public Point(Circle circle) {
        super(circle);
    }
}
