/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import edu.princeton.cs.introcs.StdDraw3D;
/**
 *
 * @author mloda
 */
public class Cube {
    public Cube() {
        StdDraw3D.setScale(-1.5, 2.5);
        for (double x = 0; x <= 1; x = x + 0.075) {
            for (double y = 0; y <= 1; y = y + 0.075) {
                for (double z = 0; z <= 1; z = z + 0.075) {
                    StdDraw3D.setPenColor((int) (x * 255), (int) (y * 255), (int) (z * 255));
                    StdDraw3D.cube(x, y, z, 0.1);
                }
            }
        }
        StdDraw3D.finished();

    }
}
