package main.graph;

import javafx.scene.shape.Polygon;
import org.jetbrains.annotations.NotNull;

public class Delta {

    private final Vertex r;
    private final Vertex p;
    private final Vertex q;

    private final Polygon delta;

    // △(r,p-q)
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

    public Vertex getP() {
        return p;
    }

    public Vertex getQ() {
        return q;
    }

    public Polygon getDelta() {
        return delta;
    }

    @Override
    public String toString() {
        return "Delta{" +
                "r=" + r +
                ", p=" + p +
                ", q=" + q +
                '}';
    }
}
