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

    public List<Point> getBezierPoints(int steps) {
        List<Point> bezier = new ArrayList<>();
        int n = this.points.size() - 1;
        if (n >= 2) {
            double step = 1.0 / steps;
            bezier.add(this.points.get(0));
            for (double t = step; t < 1; t += step) {
                int x = 0, y = 0;
                for (int i = 0; i <= n; i++) {
                    x += (binomialTheorem(n, i) * Math.pow(1 - t, n - i) * Math.pow(t, i) * this.points.get(i).x);
                    y += (binomialTheorem(n, i) * Math.pow(1 - t, n - i) * Math.pow(t, i) * this.points.get(i).y);
                }
                bezier.add(new Point(x, y));
            }
            bezier.add(this.points.get(this.points.size() - 1));
        }
        return bezier;
    }

    // dwumian Newtona
    public int binomialTheorem(int n, int i) {
        if (i == 0 || i == n) {
            return 1;
        } else {
            return factorial(n) / (factorial(i) * factorial(n - i));
        }
    }

    // silnia
    public int factorial(int i) {
        if (i == 0) {
            return 1;
        } else {
            return i * factorial(i - 1);
        }
    }
}
