package main.graph;

public class Utils {

    // Linear equation in the X-Y Axis: y = Ax + B
    public static double calculateAofLine(Vertex point1, Vertex point2) {
        double x1 = point1.getCircle().getCenterX();
        double y1 = point1.getCircle().getCenterY();
        double x2 = point2.getCircle().getCenterX();
        double y2 = point2.getCircle().getCenterY();

        return (y1 - y2) / (x1 - x2);
    }

    // Linear equation in the X-Y Axis: y = Ax + B
    public static double calculateBofLine(Vertex point, double mA) {
        double x1 = point.getCircle().getCenterX();
        double y1 = point.getCircle().getCenterY();

        return y1 - (mA * x1);
    }

}
