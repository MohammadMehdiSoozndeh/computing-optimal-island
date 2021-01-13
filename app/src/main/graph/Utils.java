package main.graph;

import javafx.scene.shape.Circle;

public class Utils {

    // A of Linear equation in the X-Y Axis: y = Ax + B
    public static double calculateAofLine(Vertex point1, Vertex point2) {
        double x1 = point1.getCircle().getCenterX();
        double y1 = point1.getCircle().getCenterY();
        double x2 = point2.getCircle().getCenterX();
        double y2 = point2.getCircle().getCenterY();

        return (y1 - y2) / (x1 - x2);
    }

    // B of Linear equation in the X-Y Axis: y = Ax + B
    public static double calculateBofLine(Vertex point, double mA) {
        double x1 = point.getCircle().getCenterX();
        double y1 = point.getCircle().getCenterY();

        return y1 - (mA * x1);
    }

    // calculate distance of two vertex base on X-Y Coordination
    public static double distance(Vertex point1, Vertex point2) {
        double x = point1.getCircle().getCenterX() - point2.getCircle().getCenterX();
        double y = point1.getCircle().getCenterY() - point2.getCircle().getCenterY();
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    // return a vertex where two lines meet
    public static Vertex theIntersectionPointOfTwoLine(double a, double b, double aPrime, double bPrime) {
        if (a - aPrime == 0) return null;
        double x = (bPrime - b) / (a - aPrime);
        double y = a * x + b;
        return new Vertex(new Circle(x, y, 2));
    }

}
