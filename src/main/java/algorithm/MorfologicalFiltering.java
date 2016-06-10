/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author mloda
 */
public class MorfologicalFiltering {

    private BufferedImage bi;
    private int[][] mask = new int[][]{{0, 1, 0}, {1, 1, 1}, {0, 1, 0}};
    private int maskHalfSize;

    public MorfologicalFiltering(BufferedImage bi) {
        this.bi = bi;
        this.maskHalfSize = mask.length / 2;
    }

    private BufferedImage getCloneOfBufferedImage() {
        BufferedImage biNew = new BufferedImage(this.bi.getWidth(), this.bi.getHeight(), this.bi.getType());
        for (int indexY = 0; indexY < this.bi.getHeight(); indexY++) {
            for (int indexX = 0; indexX < this.bi.getWidth(); indexX++) {
                biNew.setRGB(indexX, indexX, this.bi.getRGB(indexX, indexX));
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
                    if(hasBlackValueAround(indexX, indexY) ==  true) biNew.setRGB(indexX, indexY, Color.BLACK.getRGB());
                    else biNew.setRGB(indexX, indexY, Color.WHITE.getRGB());
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
                if (this.mask[maskI][maskJ] == 1 && this.bi.getRGB(x, y) == color.getRGB()) {
                    return true;
                }
            }
            maskI = 0;
        }
        return false;
    }

}
