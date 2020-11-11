package main.computation;

import javafx.scene.paint.Color;
import main.graph.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WeightComputer {

    private final Graph graph;

    private final PreProcessor preProcessor;

    public WeightComputer(@NotNull Graph graph) {
        this.graph = graph;
        graph.sortY();
        System.out.println(graph.getVertexList().toString());
        preProcessor = new PreProcessor(graph);
    }

    public void run() {
        for (int i = 0; i < graph.getVertexList().size(); i++) {
            Vertex p = graph.getVertexList().get(i);
            if (!p.getCircle().getFill().equals(Color.BLUE)) continue;
            System.out.println(p);
            List<Point> orderedPoints = orderPointsBelowHp(i, p);
            List<Edge> fineEdgesBelowHp = fineEdgesBelowHp(p, orderedPoints);

            if (fineEdgesBelowHp == null) continue;

            List<Point> pointsBelowP = processEdgesContainsP(p, orderedPoints, fineEdgesBelowHp);
            p.setBelowPointsList(pointsBelowP);
        }

        for (Vertex v : graph.getVertexList()) {
            try {
                System.out.println(v.getBelowPointsList().get(v.getBelowPointsList().size() - 1).getLbi());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private @NotNull List<Point> orderPointsBelowHp(int pIndex, Vertex mP) {
        List<Point> positiveA = new ArrayList<>();
        List<Point> negativeA = new ArrayList<>();

        for (int i = pIndex + 1; i < graph.getVertexList().size(); i++) {
            Vertex vertex = graph.getVertexList().get(i);
            if (!vertex.getCircle().getFill().equals(Color.BLUE)) continue;
            double angel = Utils.calculateAofLine(vertex, mP);

            Point point = new Point(vertex.getCircle(), vertex.getGlobalLabel());
            if (angel == 0.0)
                if (mP.getCircle().getCenterX() < vertex.getCircle().getCenterX())
                    angel = 0.0001;
                else
                    angel = -1000;
            point.setAngel(angel);

            if (angel > 0)
                positiveA.add(point);
            else
                negativeA.add(point);
        }

        positiveA.sort((p1, p2) -> (int) ((p2.getAngel() - p1.getAngel()) * 10000));
        negativeA.sort((p1, p2) -> (int) ((p2.getAngel() - p1.getAngel()) * 10000));

        List<Point> pointsBelowHp = new ArrayList<>();
        pointsBelowHp.addAll(negativeA);
        pointsBelowHp.addAll(positiveA);

        System.out.println(pointsBelowHp);

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

    private List<Point> processEdgesContainsP(Vertex p, @NotNull List<Point> orderedPoints, List<Edge> fineEdgesBelowHp) {
        List<Point> pointsContainsWeightedEdges = new ArrayList<>();
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

            Point prevPi = null;
            if (orderedPoints.indexOf(pi) > 0)
                prevPi = orderedPoints.get(orderedPoints.indexOf(pi) - 1);
            pointsContainsWeightedEdges.add(observation2(p, pi, prevPi, Lai, Lbi));  // for point pi
        }
        return pointsContainsWeightedEdges;
    }

    private Point observation2(Vertex p, Point pi, @Nullable Point prevPi, List<Edge> Lai, @NotNull List<Edge> Lbi) {
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
                if (prevPi == null) continue;
                for (Edge prevPBmi : prevPi.getLbi()) {
                    if (prevPBmi.equals(Lai.get(hsm))) {
                        bmi.setPrev(prevPBmi);
                        bmi.setWeight(prevPBmi.getWeight() + preProcessor.BlueDelta(deltaB, graph.getVertexList()) - 2);
                    }
                }
            }
        }
        pi.setLai(Lai);
        pi.setLbi(Lbi);
        return pi;
    }

}
