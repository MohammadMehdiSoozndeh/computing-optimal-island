package main.graph;

import javafx.scene.shape.Polygon;
import org.jetbrains.annotations.NotNull;

public class Delta {

    private Vertex r;
    private Vertex p;
    private Vertex q;

    private Polygon delta;

    public Delta(@NotNull Vertex r, @NotNull Vertex p, @NotNull Vertex q) {
        this.r = r;
        this.p = p;
        this.q = q;

        delta = new Polygon();
        delta.getPoints().addAll(
                r.getCircle().getCenterX(), r.getCircle().getCenterY(),             // r
                p.getCircle().getCenterX(), p.getCircle().getCenterY(),             // p
                q.getCircle().getCenterX(), q.getCircle().getCenterY()              // q
        );
    }

    public Vertex getR() {
        return r;
    }

    public void setR(Vertex r) {
        this.r = r;
    }

    public Vertex getP() {
        return p;
    }

    public void setP(Vertex p) {
        this.p = p;
    }

    public Vertex getQ() {
        return q;
    }

    public void setQ(Vertex q) {
        this.q = q;
    }

    public Polygon getDelta() {
        return delta;
    }

    public void setDelta(Polygon delta) {
        this.delta = delta;
    }
}
