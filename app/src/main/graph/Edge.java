package main.graph;

public class Edge {

    private Vertex p;
    private Vertex q;

    private Edge prev;

    private int weight;

    private Label label;

    private Direction direction;

    private double angel;

    public Edge(Vertex p, Vertex q) {
        this.p = p;
        this.q = q;
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

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public double getAngel() {
        return angel;
    }

    public void setAngel(double angel) {
        this.angel = angel;
    }

    public static class Label {
        private String label;
        private int index1;
        private int index2;

        public Label(String label, int index1, int index2) {
            this.label = label;
            this.index1 = index1;
            this.index2 = index2;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getIndex1() {
            return index1;
        }

        public void setIndex1(int index1) {
            this.index1 = index1;
        }

        public int getIndex2() {
            return index2;
        }

        public void setIndex2(int index2) {
            this.index2 = index2;
        }
    }

    @Override
    public String toString() {
        return "\nEdge{"+ p +
                " to " + q +
                ",\nprev=" + prev +
//                ", weight=" + weight +
//                ", label=" + label +
//                ", direction=" + direction +
//                ", angel=" + angel +
                "}";
    }
}
