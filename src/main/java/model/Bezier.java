/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author mloda
 */
@Getter
public class Bezier {

    private List<Point> points = new ArrayList<>();

    public void addPoint(Point p) {
        this.points.add(p);
    }

    public List<Point> getBezierPoints(double step) {
        List<Point> bezier = new ArrayList<>();
        int n = this.points.size();
        if (n > 2) {
            for (double t = step; t < 1; t += step) {
                int x = 0, y = 0;
                for (int i = 0; i < this.points.size(); i++) {
                    x += (binomialTheorem(n, i) * Math.pow(1 - t, n - i) * Math.pow(t, i) * this.points.get(i).x);
                    y += (binomialTheorem(n, i) * Math.pow(1 - t, n - i) * Math.pow(t, i) * this.points.get(i).y);
                }
                bezier.add(new Point(x, y));
            }
        }
        return bezier;
    }

    // dwumian Newtona
    private double binomialTheorem(double n, double i) {
        if (i == 0 || i == n) {
            return 1;
        } else {
            return factorial(n) / (factorial(i) * factorial(n - 1));
        }
    }

    // silnia
    public double factorial(double i) {
        if (i == 0) {
            return 1;
        } else {
            return i * factorial(i - 1);
        }
    }
}
