package main.computation;

import main.graph.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WeightComputer {

    private Graph graph;

    private PreProcessor preProcessor;

    public WeightComputer(@NotNull Graph graph) {
        this.graph = graph;
        graph.sortY();
        preProcessor = new PreProcessor(graph);
    }

    public void run() {
        for (int i = 0; i < graph.getVertexList().size(); i++) {
            Vertex p = graph.getVertexList().get(i);
            List<Point> orderedPoints = orderPointsBelowHp(i, p);
            List<Edge> fineEdgesBelowHp = fineEdgesBelowHp(p, orderedPoints);
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

    private void processEdgesContainsP(Point p, List<Point> orderedPoints, List<Edge> fineEdgesBelowHp) {
        for (Point pi : orderedPoints) {
            List<Edge> Lai = new ArrayList<>(); // page 5 of paper, list of incoming edges to Pi; La,i = {a1,i , ... , aq,i}
            List<Edge> Lbi = new ArrayList<>(); // page 5 of paper, list of outgoing edges to Pi; Lb,i = {b1,i , ... , bq,i}

            for (Edge e : fineEdgesBelowHp) {
                if (e.getQ().equals(pi))
                    Lai.add(e);
                else if (e.getP().equals(pi))
                    Lbi.add(e);

                double angel = Utils.calculateAofLine(e.getP(), e.getQ());
                e.setAngel(angel);

                Delta delta = new Delta(p, e.getP(), e.getQ());
                int weight = preProcessor.BlueDelta(delta, graph.getVertexList());
                e.setWeight(weight);
            }

            Lai.sort((o1, o2) -> (int) (o2.getAngel() - o1.getAngel()));
            Lbi.sort((o1, o2) -> (int) (o1.getAngel() - o2.getAngel()));

            observation2(p, Lai, Lbi);
        }
    }

    private void observation2(Point p, List<Edge> Lai, List<Edge> Lbi) {
        for (Edge bmi : Lbi) {
            int smIndex = 0;
            Delta deltaB = new Delta(p, bmi.getP(), bmi.getQ());
            for (int i = Lai.size() - 1; i >= 0; i--) {
                Delta deltaA = new Delta(p, Lai.get(i).getP(), Lai.get(i).getQ());
                if (!preProcessor.isPCompatible(deltaA, deltaB)) continue;
                smIndex = i;
            }
            if (smIndex == 0)
                bmi.setWeight(preProcessor.BlueDelta(deltaB, graph.getVertexList()));
            else {
                int max = Lai.get(0).getWeight();
                int hsm = 0;
                for (int i = 0; i < smIndex; i++) {
                    if (Lai.get(i).getWeight() > max) {
                        hsm = i;
                        max = Lai.get(i).getWeight();
                    }
                }

                bmi.setPrev(Lai.get(hsm));
                bmi.setWeight(Lai.get(hsm).getWeight() + preProcessor.BlueDelta(deltaB, graph.getVertexList()) - 2);
            }
        }
    }

}
