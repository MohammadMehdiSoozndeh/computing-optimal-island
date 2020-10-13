package main.graph;

import javafx.scene.shape.Circle;

public class Vertex {

    public static final double NEIGHBORHOOD_DISTANCE = 25.0;

    private Circle circle;
    private String globalLabel;

    public Vertex(Circle circle, String globalLabel) {
        this.circle = circle;
        this.globalLabel = globalLabel;
    }

    public Vertex(Circle circle) {
        this.circle = circle;
    }

    public boolean isTooClose(double x, double y) {
        double dY = Math.abs(this.circle.getCenterY() - y);
        double dX = Math.abs(this.circle.getCenterX() - x);
        double dD = Math.pow(dY, 2) + Math.pow(dX, 2);
        return Math.sqrt(dD) < NEIGHBORHOOD_DISTANCE;
    }

    public boolean isTooClose(Circle circle) {
        double dY = Math.abs(this.circle.getCenterY() - circle.getCenterY());
        double dX = Math.abs(this.circle.getCenterX() - circle.getCenterX());
        double dD = Math.pow(dY, 2) + Math.pow(dX, 2);
        return Math.sqrt(dD) < NEIGHBORHOOD_DISTANCE;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public String getGlobalLabel() {
        return globalLabel;
    }

    public void setGlobalLabel(String globalLabel) {
        this.globalLabel = globalLabel;
    }

    @Override
    public String toString() {
        return "\n\t" + globalLabel + " : " + circle.getFill().toString() +
                "\t X:" + (float) circle.getCenterX() + "\t Y:" + (float) circle.getCenterY();
    }
}
