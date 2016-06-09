/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.awt.Point;
import lombok.Getter;

/**
 *
 * @author mloda
 */
@Getter
public class MatrixTransitions {

    private double[][] matrix;

    public void setMoveMatrix(Point vector) {
        matrix = new double[][]{{1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {vector.x, vector.y, 1.0}};
    }

    public void setTurnMatrix(Point vector, double angle) {
        matrix = new double[][]{{Math.cos(angle), Math.sin(angle), 0.0}, {-Math.sin(angle), Math.cos(angle), 0.0}, {vector.x, vector.y, 1.0}};
        //matrix = new double[][]{{Math.cos(angle), -Math.sin(angle), 0.0}, {Math.sin(angle), Math.cos(angle), 0.0}, {0, 0, 1.0}};
    }

    public void setScaleMatrix(Point vector, double scalingFactor) {
        matrix = new double[][]{{scalingFactor, 0.0, 0.0}, {0.0, scalingFactor, 0.0}, {vector.x, vector.y, 1.0}};
        //matrix = new double[][]{{scalingFactor, 0.0, 0.0}, {0.0, scalingFactor, 0.0}, {0, 0, 1.0}};
    }

    private double[] multiplyVectorByMatrix(double[] in) {
        double[] out = new double[3];
        for (int i = 0; i < in.length; i++) {
            for (int repet = 0; repet < in.length; repet++) {
                out[repet] += (in[i] * matrix[i][repet]);
            }
        }
        return out;
    }

    public Point getMovedPoint(Point before) {
        double[] in = new double[]{before.x, before.y, 1};
        double[] out = multiplyVectorByMatrix(in);
        Point after = new Point((int) Math.abs(out[0]), (int) Math.abs(out[1]));
        return after;
    }

    public Point getMovedPoint(Point before, Point vector, double angle) {
        double x = Math.abs(vector.x + (before.x - vector.x) * Math.cos(angle) - (before.y - vector.y) * Math.sin(angle));
        double y = Math.abs(vector.y + (before.x - vector.x) * Math.sin(angle) + (before.y - vector.y) * Math.cos(angle));
        Point after = new Point((int)x, (int)y);
        return after;
    }
}
