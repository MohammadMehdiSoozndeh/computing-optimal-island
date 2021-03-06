package main.graph;

import javafx.scene.shape.Circle;

import java.util.List;

public class Point extends Vertex {

    private double angel;

    private List<Edge> Lai;
    private List<Edge> Lbi;

    public Point(Circle circle, String globalLabel) {
        super(circle, globalLabel);
    }

    public double getAngel() {
        return angel;
    }

    public void setAngel(double angel) {
        this.angel = angel;
    }

    public List<Edge> getLai() {
        return Lai;
    }

    public void setLai(List<Edge> lai) {
        Lai = lai;
    }

    public List<Edge> getLbi() {
        return Lbi;
    }

    public void setLbi(List<Edge> lbi) {
        Lbi = lbi;
    }

    @Override
    public String toString() {
        return "\nPoint{" +
                "GL=" + getGlobalLabel() +
                "}";
    }
}
