package main.graph;

public class Edge {

    private final Vertex p;
    private final Vertex q;

    private Edge prev;

    private int weight;

    private double angel;

    public Edge(Vertex p, Vertex q) {
        this.p = p;
        this.q = q;
    }

    public Vertex getP() {
        return p;
    }

    public Vertex getQ() {
        return q;
    }

    public Edge getPrev() {
        return prev;
    }

    public void setPrev(Edge prev) {
        this.prev = prev;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getAngel() {
        return angel;
    }

    public void setAngel(double angel) {
        this.angel = angel;
    }

    @Override
    public String toString() {
        return "\nEdge{"+ p +
                " to " + q +
                ",\nprev=" + prev +
                "}";
    }
}
