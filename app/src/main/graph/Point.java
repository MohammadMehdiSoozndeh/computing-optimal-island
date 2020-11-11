package main.graph;

import javafx.scene.shape.Circle;

import java.util.List;

public class Point extends Vertex {

    private int label;
    private double angel;

    private List<Edge> Lai;
    private List<Edge> Lbi;

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
}
