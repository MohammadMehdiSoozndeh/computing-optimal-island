package main.computation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import main.graph.Delta;
import main.graph.Graph;
import main.graph.Utils;
import main.graph.Vertex;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PreProcessor {

    private final Graph graph;

    public PreProcessor(Graph graph) {
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

    public int BlueDelta(Polygon delta, @NotNull List<Vertex> vertexList) {
        int temp = 0;
        for (Vertex vertex : vertexList)
            if (delta.contains(vertex.getCircle().getCenterX(), vertex.getCircle().getCenterY())
                    && vertex.getCircle().getFill().equals(Color.BLUE))
                temp++;

        return temp;
    }


    public boolean isPCompatible(Delta mPE, Delta mPEPrime) {
        return hasNoRedPoint(mPE, graph.getVertexList()) && hasNoRedPoint(mPEPrime, graph.getVertexList())
                && haveDisjointInteriors(mPE, mPEPrime)
                && isConvexPolygon(mPE, mPEPrime);
    }

    private boolean hasNoRedPoint(Delta delta, @NotNull List<Vertex> vertexList) {
        for (Vertex vertex : vertexList) {
            if (delta.getDelta().contains(
                    vertex.getCircle().getCenterX(),
                    vertex.getCircle().getCenterY())
                    &&
                    vertex.getCircle().getFill().equals(Color.RED))
                return false;
        }
        return true;
    }

    private boolean haveDisjointInteriors(@NotNull Delta mPE, @NotNull Delta mPEPrime) {
        double a = Utils.calculateAofLine(mPE.getR(), mPEPrime.getQ());
        double b = Utils.calculateBofLine(mPE.getR(), a);

        double y = (a * mPEPrime.getQ().getCircle().getCenterX()) + b;
        return y < mPEPrime.getQ().getCircle().getCenterY();
    }

    private boolean isConvexPolygon(@NotNull Delta mPE, @NotNull Delta mPEPrime) {

        double aUpper = Utils.calculateAofLine(mPE.getR(), mPE.getP());
        double bUpper = Utils.calculateBofLine(mPE.getR(), aUpper);

        double aLower = Utils.calculateAofLine(mPE.getP(), mPEPrime.getP());
        double bLower = Utils.calculateBofLine(mPE.getP(), aLower);

        double yUpper = (aUpper * mPEPrime.getQ().getCircle().getCenterX()) + bUpper;
        double yLower = (aLower * mPEPrime.getQ().getCircle().getCenterX()) + bLower;

        double yS = mPEPrime.getQ().getCircle().getCenterY();
        return yS < yLower && yS > yUpper;
    }

}
