package main.computation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

    public static boolean hasNoRedPoint(Delta delta, @NotNull List<Vertex> vertexList) {
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

    public long BlueB(int i, List<Delta> deltaList) {
        if (i == 1)
            return 2;
        else
            return BlueB(i - 1, deltaList.subList(0, deltaList.size() - 2))
                    + BlueDelta(deltaList.get(deltaList.size() - 1), graph.getVertexList())
                    - 2;
    }

    public int BlueDelta(Delta delta, @NotNull List<Vertex> vertexList) {
        int temp = 0;
        for (Vertex vertex : vertexList) {
            if (vertex.getGlobalLabel().equals(delta.getP().getGlobalLabel())) continue;
            if (vertex.getGlobalLabel().equals(delta.getQ().getGlobalLabel())) continue;
            if (vertex.getGlobalLabel().equals(delta.getR().getGlobalLabel())) continue;
            if (delta.getDelta().contains(vertex.getCircle().getCenterX(), vertex.getCircle().getCenterY())
                    && vertex.getCircle().getFill().equals(Color.BLUE))
                temp++;
        }
        return temp + 3;
    }

    public boolean isPCompatible(Delta mPE, Delta mPEPrime) {
        System.out.println(mPE);
        System.out.println(mPEPrime);
        boolean isPCompatible = hasNoRedPoint(mPE, graph.getVertexList()) && hasNoRedPoint(mPEPrime, graph.getVertexList())
                && haveDisjointInteriors(mPE, mPEPrime)
                && isConvexPolygon(mPE, mPEPrime);
        System.out.println("isPCompatible -> " + isPCompatible);
        System.out.println("haveDisjointInteriors(mPE, mPEPrime) - > " + haveDisjointInteriors(mPE, mPEPrime));
        System.out.println("isConvexPolygon(mPE, mPEPrime) - > " + isConvexPolygon(mPE, mPEPrime) + "\n");
        return isPCompatible;
    }

    private boolean haveDisjointInteriors(@NotNull Delta mPE, @NotNull Delta mPEPrime) {
        double a = Utils.calculateAofLine(mPE.getR(), mPEPrime.getP());
        double b = Utils.calculateBofLine(mPE.getR(), a);

        double yOnLine = (a * mPEPrime.getQ().getCircle().getCenterX()) + b;
        double xOnLine = (mPEPrime.getQ().getCircle().getCenterY() - b) / a;

        Circle target = mPEPrime.getQ().getCircle();

        return ((a > 0 && yOnLine > target.getCenterY()) || (a < 0 && yOnLine < target.getCenterY()))
                && xOnLine < target.getCenterX();
    }

    private boolean isConvexPolygon(@NotNull Delta mPE, @NotNull Delta mPEPrime) {
        double a = Utils.calculateAofLine(mPE.getP(), mPEPrime.getQ());
        double b = Utils.calculateBofLine(mPE.getP(), a);

        double aPrime = Utils.calculateAofLine(mPE.getR(), mPE.getQ());
        double bPrime = Utils.calculateBofLine(mPE.getR(), aPrime);

        Vertex intersectionOfLines = Utils.theIntersectionPointOfTwoLine(a, b, aPrime, bPrime);
        if (intersectionOfLines == null) return false;

        System.out.println("mPE.getR() =>  " + mPE.getR());
        System.out.println("intersectionOfLines => " + intersectionOfLines);

        System.out.println("Utils.distance(mPE.getR(), mPE.getQ()) => " + Utils.distance(mPE.getR(), mPE.getQ()));
        System.out.println("Utils.distance(mPE.getR(), intersectionOfLines) => " + Utils.distance(mPE.getR(), intersectionOfLines) + "\n");
        return Utils.distance(mPE.getR(), mPE.getQ()) > Utils.distance(mPE.getR(), intersectionOfLines);
    }

}
