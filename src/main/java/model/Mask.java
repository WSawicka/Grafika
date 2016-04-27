/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author mloda
 */
@Getter
@Setter
public class Mask {

    //wygladzajacy (usredniajacy)
    private final int[][] averagingMask3x3
            = {{1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}};

    private final int[][] averagingMask5x5
            = {{1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1}};

    //wykrywania krawedzi
    private final int[][] sobelHorizontal
            = {{1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1}};

    private final int[][] sobelVertical
            = {{1, 0, -1},
            {2, 0, -2},
            {1, 0, -1}};

    //gornoprzepustowy wyostrzajacy
    private final int[][] meanRemoval
            = {{-1, -1, -1},
            {-1, 9, -1},
            {-1, -1, -1}};

    private final int[][] hp1
            = {{0, -1, 0},
            {-1, 5, -1},
            {0, -1, 0}};

    private final int[][] hp2
            = {{1, -2, 1},
            {-2, 5, -2},
            {1, -2, 1}};

    private final int[][] hp3
            = {{0, -1, 0},
            {-1, 20, -1},
            {0, -1, 0}};

    //rozmycie gaussowskie
    private final int[][] gauss1
            = {{1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}};

    private final int[][] gauss2
            = {{1, 1, 2, 1, 1},
            {1, 2, 4, 2, 1},
            {2, 4, 8, 4, 2},
            {1, 2, 4, 2, 1},
            {1, 1, 2, 1, 1}};

    private final int[][] gauss3
            = {{0, 1, 2, 1, 0},
            {1, 4, 8, 4, 1},
            {2, 8, 16, 8, 2},
            {1, 4, 8, 4, 1},
            {0, 1, 2, 1, 0}};
    private final int[][] gauss4
            = {{1, 4, 7, 4, 1},
            {4, 16, 26, 16, 4},
            {7, 26, 41, 26, 7},
            {4, 26, 16, 6, 4},
            {1, 4, 7, 4, 1}};
    private final int[][] gauss5
            = {{1, 1, 2, 2, 2, 1, 1},
            {1, 2, 2, 4, 2, 2, 1},
            {2, 2, 4, 8, 4, 2, 2},
            {2, 4, 8, 16, 8, 4, 2},
            {2, 2, 4, 8, 4, 2, 2},
            {1, 2, 2, 4, 2, 2, 1},
            {1, 1, 2, 2, 2, 1, 1}};

    //splot maski
    private final int[][] usersMask = null;

    public int getSummary(int[][] mask) {
        int sum = 0;
        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask.length; j++) {
                sum += mask[i][j];
            }
        }
        return sum;
    }
}
