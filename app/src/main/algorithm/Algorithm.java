package main.algorithm;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import main.graph.Graph;
import main.graph.Vertex;

import java.util.List;

public class Algorithm {

    private final Graph graph;

    public Algorithm(Graph graph) {
        this.graph = graph;
    }

    public long BlueB(int i, List<Polygon> deltaList) {
        if (i == 1)
            return 2;
        else
            return BlueB(i - 1, deltaList.subList(0, deltaList.size() - 2))
                    + BlueDelta(deltaList.get(deltaList.size() - 1), graph.getVertexList())
                    - 2;
    }

    public Polygon createTriangle(Circle pCircle, Circle qCircle, Circle rCircle) {
        Polygon delta = new Polygon();
        delta.getPoints().addAll(
                pCircle.getCenterX(), pCircle.getCenterY(),
                qCircle.getCenterX(), qCircle.getCenterY(),
                rCircle.getCenterX(), rCircle.getCenterY());
        return delta;
    }

    public int BlueDelta(Polygon delta, List<Vertex> vertexList) {
        int temp = 0;
        for (Vertex vertex : vertexList)
            if (delta.contains(
                    vertex.getCircle().getCenterX(),
                    vertex.getCircle().getCenterY())
                    &&
                    vertex.getCircle().getFill().equals(Color.BLUE))
                temp++;

        return temp;
    }

    public boolean isPCompatible(Polygon deltaPE, Polygon deltaPEPrime) {
        return hasNoRedPoint(deltaPE, graph.getVertexList())
                && hasNoRedPoint(deltaPEPrime, graph.getVertexList())
                && haveDisjointInterior(deltaPE, deltaPEPrime)
                && isConvexPolygon(deltaPE, deltaPEPrime);
    }

    private boolean hasNoRedPoint(Polygon delta, List<Vertex> vertexList) {
        for (Vertex vertex : vertexList) {
            if (delta.contains(
                    vertex.getCircle().getCenterX(),
                    vertex.getCircle().getCenterY())
                    &&
                    vertex.getCircle().getFill().equals(Color.RED))
                return false;
        }
        return true;
    }

    private boolean haveDisjointInterior(Polygon deltaPE, Polygon deltaPEPrime) {
        double x1 = deltaPE.getPoints().get(0);
        double y1 = deltaPE.getPoints().get(1);
        double x2 = deltaPEPrime.getPoints().get(2);
        double y2 = deltaPEPrime.getPoints().get(3);

        double a = (y1 - y2) / (x1 - x2);
        double b = y1 - (a * x1);

        double y = (a * deltaPEPrime.getPoints().get(4)) + b;
        return y < deltaPEPrime.getPoints().get(5);
    }

    private boolean isConvexPolygon(Polygon deltaPE, Polygon deltaPEPrime) {
        double x1Upper = deltaPE.getPoints().get(0);
        double y1Upper = deltaPE.getPoints().get(1);
        double x2Upper = deltaPE.getPoints().get(2);
        double y2Upper = deltaPE.getPoints().get(3);

        double aUpper = (y1Upper - y2Upper) / (x1Upper - x2Upper);
        double bUpper = y1Upper - (aUpper * x1Upper);

        double x1Lower = deltaPE.getPoints().get(2);
        double y1Lower = deltaPE.getPoints().get(3);
        double x2Lower = deltaPEPrime.getPoints().get(2);
        double y2Lower = deltaPEPrime.getPoints().get(3);

        double aLower = (y1Lower - y2Lower) / (x1Lower - x2Lower);
        double bLower = y1Lower - (aLower * x1Lower);

        double yUpper = (aUpper * deltaPEPrime.getPoints().get(4)) + bUpper;
        double yLower = (aLower * deltaPEPrime.getPoints().get(4)) + bLower;

        double yS = deltaPEPrime.getPoints().get(5);
        return yS < yLower && yS > yUpper;
    }
}
