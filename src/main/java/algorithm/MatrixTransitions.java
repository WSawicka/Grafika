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
    private Point vector;

    public MatrixTransitions(Point vector) {
        this.vector = vector;
    }

    public void setMoveMatrix() {
        matrix = new double[][]{{1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {this.vector.x, this.vector.y, 1.0}};
    }

    public void setTurnMatrix(double angle) {
        matrix = new double[][]{{Math.cos(angle), Math.sin(angle), 0.0}, {-Math.sin(angle), Math.cos(angle), 0.0}, {this.vector.x, this.vector.y, 1.0}};
    }

    public void setScaleMatrix(double scalingFactor) {
        matrix = new double[][]{{scalingFactor, 0.0, 0.0}, {0.0, scalingFactor, 0.0}, {this.vector.x, this.vector.y, 1.0}};
    }

    private double[] multiplyVectorByMatrix(double[] in) {
        double[] out = new double[3];
        for (int i = 0; i < in.length; i++) {
            for (int repet = 0; repet < in.length; repet++) {
                out[i] += (in[repet] * matrix[i][repet]);
            }
        }
        return out;
    }

    public Point getMovedPoint(Point before) {
        double[] in = new double[]{before.x, before.y, 1};
        double[] out = multiplyVectorByMatrix(in);
        Point after = new Point((int) out[0], (int) out[1]);
        return after;
    }
}
