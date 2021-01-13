package main.graph;

import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.List;

public class Vertex {

    // safe distance in neighborhood to another vertex
    public static final double NEIGHBORHOOD_DISTANCE = 25.0;

    private final Circle circle;
    private String globalLabel;

    private Text labelText;

    private List<Point> belowPointsList;

    public Vertex(Circle circle, String globalLabel) {
        this.circle = circle;
        this.globalLabel = globalLabel;
    }

    public Vertex(Circle circle) {
        this.circle = circle;
    }

    // prevent vertices to be too close to each other
    public boolean isTooClose(double x, double y) {
        double dY = Math.abs(this.circle.getCenterY() - y);
        double dX = Math.abs(this.circle.getCenterX() - x);
        double dD = Math.pow(dY, 2) + Math.pow(dX, 2);
        return Math.sqrt(dD) < NEIGHBORHOOD_DISTANCE;
    }

    public Circle getCircle() {
        return circle;
    }

    public String getGlobalLabel() {
        return globalLabel;
    }

    @Override
    public String toString() {
        return "\n Label= p" + globalLabel +
                "\t color = " + circle.getFill().toString() +
                "\t X:" + (float) circle.getCenterX() + "\t Y:" + (float) circle.getCenterY();
    }

    public Text getLabelText() {
        return labelText;
    }

    public void setLabelText(Text labelText) {
        this.labelText = labelText;
    }

    public List<Point> getBelowPointsList() {
        return belowPointsList;
    }

    public void setBelowPointsList(List<Point> belowPointsList) {
        this.belowPointsList = belowPointsList;
    }
}
