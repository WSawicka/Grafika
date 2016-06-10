/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

/**
 *
 * @author mloda
 */
@Setter
public class MorfologicalFiltering {

    private BufferedImage bi;
    //private int[][] mask = new int[][]{{0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}, {1, 1, 1, 1, 1}, {0, 0, 1, 0, 0}, {0, 0, 1, 0, 0}};
    private int[][] mask = new int[][]{{0, 1, 0}, {1, 1, 1}, {0, 1, 0}};
    private int maskHalfSize;
    private List<int[][]> tablesSE;
    private List<int[][]> tablesThickeningSE;
    private boolean findCorners = false;

    public MorfologicalFiltering(BufferedImage bi) {
        this.bi = bi;
        this.maskHalfSize = mask.length / 2;
        this.tablesSE = new ArrayList<>();
        tablesSE.add(new int[][]{{-1, 1, -1}, {0, 1, 1}, {0, 0, -1}});
        tablesSE.add(new int[][]{{0, 0, -1}, {0, 1, 1}, {-1, 1, -1}});
        tablesSE.add(new int[][]{{-1, 0, 0}, {1, 1, 0}, {-1, 1, -1}});
        tablesSE.add(new int[][]{{-1, 1, -1}, {1, 1, 0}, {-1, 0, 0}});

        this.tablesThickeningSE = new ArrayList<>();
        this.tablesThickeningSE.add(new int[][]{{1, 1, -1}, {1, 0, -1}, {1, -1, 0}});
        this.tablesThickeningSE.add(new int[][]{{-1, 1, 1}, {-1, 0, 1}, {0, -1, 1}});
        this.tablesThickeningSE.add(new int[][]{{1, 1, 1}, {1, 0, -1}, {-1, -1, 0}});
        this.tablesThickeningSE.add(new int[][]{{-1, -1, 0}, {1, 0, -1}, {1, 1, 1}});
        this.tablesThickeningSE.add(new int[][]{{1, -1, 0}, {1, 0, -1}, {1, 1, -1}});
        this.tablesThickeningSE.add(new int[][]{{0, -1, 1}, {-1, 0, 1}, {-1, 1, 1}});
        this.tablesThickeningSE.add(new int[][]{{1, 1, 1}, {-1, 0, 1}, {0, -1, -1}});
        this.tablesThickeningSE.add(new int[][]{{0, -1, -1}, {-1, 0, 1}, {1, 1, 1}});
    }

    private BufferedImage getCloneOfBufferedImage() {
        BufferedImage biNew = new BufferedImage(this.bi.getWidth(), this.bi.getHeight(), this.bi.getType());
        for (int indexY = 0; indexY < this.bi.getHeight(); indexY++) {
            for (int indexX = 0; indexX < this.bi.getWidth(); indexX++) {
                biNew.setRGB(indexX, indexY, this.bi.getRGB(indexX, indexY));
            }
        }
        return biNew;
    }

    public BufferedImage doDilation() {
        BufferedImage biNew = getCloneOfBufferedImage();

        for (int indexY = 0; indexY < this.bi.getHeight(); indexY++) {
            for (int indexX = 0; indexX < this.bi.getWidth(); indexX++) {
                if ((indexX - this.maskHalfSize) >= 0 && (indexY - this.maskHalfSize) >= 0
                        && (indexX + this.maskHalfSize) < this.bi.getWidth() && (indexY + this.maskHalfSize) < this.bi.getHeight()) {
                    if (hasBlackValueAround(indexX, indexY) && this.bi.getRGB(indexX, indexY) != Color.BLACK.getRGB()) {
                        biNew.setRGB(indexX, indexY, Color.BLACK.getRGB());
                    }
                }
            }
        }
        return biNew;
    }

    public BufferedImage doErosion() {
        BufferedImage biNew = getCloneOfBufferedImage();

        for (int indexY = 0; indexY < this.bi.getHeight(); indexY++) {
            for (int indexX = 0; indexX < this.bi.getWidth(); indexX++) {
                if ((indexX - this.maskHalfSize) >= 0 && (indexY - this.maskHalfSize) >= 0
                        && (indexX + this.maskHalfSize) < this.bi.getWidth() && (indexY + this.maskHalfSize) < this.bi.getHeight()) {
                    if (hasWhiteValueAround(indexX, indexY) && this.bi.getRGB(indexX, indexY) != Color.WHITE.getRGB()) {
                        biNew.setRGB(indexX, indexY, Color.WHITE.getRGB());
                    }
                }
            }
        }
        return biNew;
    }

    private boolean hasWhiteValueAround(int x, int y) {
        return hasValueAround(Color.WHITE, x, y);
    }

    private boolean hasBlackValueAround(int x, int y) {
        return hasValueAround(Color.BLACK, x, y);
    }

    private boolean hasValueAround(Color color, int x, int y) {
        int maskI = 0;
        int maskJ = 0;
        for (int j = y - maskHalfSize; j < y + maskHalfSize; j++, maskJ++) {
            for (int i = x - maskHalfSize; i < x + maskHalfSize; i++, maskI++) {
                if (this.mask[maskI][maskJ] == 1 && this.bi.getRGB(i, j) == color.getRGB()) {
                    return true;
                }
            }
            maskI = 0;
        }
        return false;
    }

    public BufferedImage doHitOrMiss() {
        BufferedImage biNew = getCloneOfBufferedImage();

        int halfLen = tablesSE.get(0).length / 2;
        for (int indexY = halfLen; indexY < this.bi.getHeight() - halfLen; indexY++) {
            for (int indexX = halfLen; indexX < this.bi.getWidth() - halfLen; indexX++) {

                if (this.bi.getRGB(indexX, indexY) == Color.BLACK.getRGB()) {
                    int color = (checkIfSame(indexX, indexY)) ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
                    biNew.setRGB(indexX, indexY, color);
                }
            }
        }
        return biNew;
    }

    public BufferedImage doThickening() {
        BufferedImage biNew = getCloneOfBufferedImage();

        int halfLen = tablesSE.get(0).length / 2;
        for (int indexY = halfLen; indexY < this.bi.getHeight() - halfLen; indexY++) {
            for (int indexX = halfLen; indexX < this.bi.getWidth() - halfLen; indexX++) {

                if (this.bi.getRGB(indexX, indexY) == Color.WHITE.getRGB()) {
                    int color = (checkIfSame(indexX, indexY)) ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
                    biNew.setRGB(indexX, indexY, color);
                }
            }
        }
        return biNew;
    }

    private boolean checkIfSame(int x, int y) {
        if (this.findCorners) {
            for (int[][] table : this.tablesSE) {
                if (checkIfSameTable(table, x, y)) {
                    return true;
                }
            }
        } else {
            for (int[][] table : this.tablesThickeningSE) {
                if (checkIfSameTable(table, x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfSameTable(int[][] table, int x, int y) {
        int halfLen = table.length / 2;
        int tableIndexI = 0;
        int a = 0;
        for (int i = x - halfLen; i <= x + halfLen; i++, tableIndexI++) {
            int tableIndexJ = 0;
            for (int j = y - halfLen; j <= y + halfLen; j++, tableIndexJ++) {

                if (table[tableIndexI][tableIndexJ] != -1) {
                    if (!checkIfSamePixel(table[tableIndexI][tableIndexJ], i, j)) {
                        return false;
                    }
                }
                a++;
            }
        }
        return true;
    }

    private boolean checkIfSamePixel(int tableValue, int x, int y) {
        Color color = new Color(this.bi.getRGB(x, y));
        if (tableValue == 0) {
            return color.getRGB() == Color.WHITE.getRGB();
        } else if (tableValue == 1) {
            return color.getRGB() == Color.BLACK.getRGB();
        } else {
            throw new RuntimeException("Check values inside the table!!! The wrong value equals " + tableValue);
        }
    }
}
