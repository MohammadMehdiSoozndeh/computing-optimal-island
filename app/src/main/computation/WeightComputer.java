package main.computation;

import main.graph.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WeightComputer {

    private Graph graph;

    public WeightComputer(@NotNull Graph graph) {
        this.graph = graph;
        graph.sortY();
    }

    public void run() {
        for (int i = 0; i < graph.getVertexList().size(); i++) {
            List<Point> orderedPoints = orderPointsBelowHp(i, graph.getVertexList().get(i));
            List<Edge> fineEdgesBelowHp = fineEdgesBelowHp(graph.getVertexList().get(i), orderedPoints);
        }
    }

    private @NotNull List<Point> orderPointsBelowHp(int pIndex, Vertex mP) {
        List<Point> positiveA = new ArrayList<>();
        List<Point> negativeA = new ArrayList<>();

        for (int i = pIndex + 1; i < graph.getVertexList().size(); i++) {
            Vertex vertex = graph.getVertexList().get(i);
            double angel = Utils.calculateAofLine(vertex, mP);

            Point point = new Point(vertex.getCircle(), vertex.getGlobalLabel());
            point.setAngel(angel);

            if (angel > 0)
                positiveA.add(point);
            else
                negativeA.add(point);
        }

        positiveA.sort((p1, p2) -> (int) (p1.getAngel() - p2.getAngel()));
        negativeA.sort((p1, p2) -> (int) (p2.getAngel() - p1.getAngel()));

        List<Point> pointsBelowHp = new ArrayList<>();
        pointsBelowHp.addAll(positiveA);
        pointsBelowHp.addAll(negativeA);

        for (int i = 0; i < pointsBelowHp.size(); i++)
            pointsBelowHp.get(i).setLabel(i);

        return pointsBelowHp;

    }

    private @Nullable List<Edge> fineEdgesBelowHp(Vertex p, @NotNull List<Point> orderedPoints) {
        if (orderedPoints.size() < 2) return null;
        List<Edge> fineEdgesBelowHp = new ArrayList<>();

        for (int i = 0; i < orderedPoints.size() - 1; i++) {
            for (int j = i + 1; j < orderedPoints.size(); j++) {
                Delta delta = new Delta(p, orderedPoints.get(i), orderedPoints.get(j));

                if (PreProcessor.hasNoRedPoint(delta, graph.getVertexList())) {
                    Edge edge = new Edge(orderedPoints.get(i), orderedPoints.get(j));
                    edge.setDirection(Direction.PtoQ);
                    fineEdgesBelowHp.add(edge);
                }

            }
        }
        return fineEdgesBelowHp;
    }

}
