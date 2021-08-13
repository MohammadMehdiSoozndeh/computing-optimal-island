package main.dualspace;

import javafx.scene.shape.Line;
import main.graph.Vertex;

import java.util.ArrayList;
import java.util.List;

import static main.Controller.SQUARE_BOARD_SIZE;
import static main.Controller.SQUARE_BOARD_X;

public class DualSpace {

    private final List<Line> linesEquivalenceList;
    private final List<Line> convexHallLinesEquivalence;

    public DualSpace(List<Vertex> allVertices, List<Vertex> convexHallVertices) {
        linesEquivalenceList = new ArrayList<>();
        for (Vertex v : allVertices)
            linesEquivalenceList.add(dualityTransform(v));

        convexHallLinesEquivalence = new ArrayList<>();
        for (Vertex v :convexHallVertices)
            convexHallLinesEquivalence.add(dualityTransform(v));
    }

    private Line dualityTransform(Vertex vertex) {
        double a = vertex.getCircle().getCenterX();
        double b = vertex.getCircle().getCenterY();
        // duality equivalence =>  y=ax-b
        Line line = new Line(
                SQUARE_BOARD_X, (a * SQUARE_BOARD_X) - b,
                SQUARE_BOARD_X + SQUARE_BOARD_SIZE, a * (SQUARE_BOARD_X + SQUARE_BOARD_SIZE) - b
        );
        line.setStroke(vertex.getCircle().getStroke());
//        line.on
        return line;
    }

    public List<Line> getLinesEquivalenceList() {
        return linesEquivalenceList;
    }

    public List<Line> getConvexHallLinesEquivalence() {
        return convexHallLinesEquivalence;
    }
}
