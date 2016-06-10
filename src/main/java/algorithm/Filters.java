/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import app.Utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author mloda
 */
public class Filters {

    private BufferedImage bi;
    private Utils utils;
    private int[][] mask;
    private int maskHalfSize;
    private int maskSummary;

    public Filters(BufferedImage bi, int maskHalfSize) {
        this.bi = bi;
        this.maskHalfSize = maskHalfSize;
        this.maskSummary = 1;
    }

    public Filters(BufferedImage bi, int[][] mask, int maskSummary) {
        this.bi = bi;
        this.mask = mask;
        this.maskHalfSize = mask.length / 2;
        this.maskSummary = maskSummary;
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

    public BufferedImage setMedian() {
        BufferedImage biNew = getCloneOfBufferedImage();

        for (int indexY = 0; indexY < this.bi.getHeight(); indexY++) {
            for (int indexX = 0; indexX < this.bi.getWidth(); indexX++) {
                setMedianPixel(biNew, indexX, indexY);
            }
        }
        return biNew;
    }

    private void setMedianPixel(BufferedImage biNew, int indexX, int indexY){
        if ((indexX - this.maskHalfSize) >= 0 && (indexY - this.maskHalfSize) >= 0
                && (indexX + this.maskHalfSize) < this.bi.getWidth() && (indexY + this.maskHalfSize) < this.bi.getHeight()) {

            List<Integer> listR = new ArrayList<>();
            List<Integer> listG = new ArrayList<>();
            List<Integer> listB = new ArrayList<>();

            for (int i = indexY - this.maskHalfSize; i <= indexY + this.maskHalfSize; i++) {
                for (int j = indexX - this.maskHalfSize; j <= indexX + this.maskHalfSize; j++) {
                    Color actColor = new Color(this.bi.getRGB(j, i));
                    listR.add(actColor.getRed());
                    listG.add(actColor.getGreen());
                    listB.add(actColor.getBlue());
                }
            }
            Collections.sort(listR);
            Collections.sort(listG);
            Collections.sort(listB);

            int r = listR.get(listR.size() / 2);
            int g = listG.get(listG.size() / 2);
            int b = listB.get(listB.size() / 2);

            Color newColor = new Color(r, g, b);
            biNew.setRGB(indexX, indexY, newColor.getRGB());
        }
    }

    public BufferedImage setBufferedImage() {
        BufferedImage biNew = getCloneOfBufferedImage();

        for (int indexY = 0; indexY < this.bi.getHeight(); indexY++) {
            for (int indexX = 0; indexX < this.bi.getWidth(); indexX++) {
                setBufferedPixel(biNew, indexX, indexY);
            }
        }
        return biNew;
    }

    private void setBufferedPixel(BufferedImage biNew, int indexX, int indexY){
        if ((indexX - this.maskHalfSize) >= 0 && (indexY - this.maskHalfSize) >= 0
                && (indexX + this.maskHalfSize) < this.bi.getWidth() && (indexY + this.maskHalfSize) < this.bi.getHeight()) {

            int maskY = 0;
            int r = 0;
            int g = 0;
            int b = 0;

            for (int i = indexY - this.maskHalfSize; i <= indexY + this.maskHalfSize; i++, maskY++) {
                int maskX = 0;
                for (int j = indexX - this.maskHalfSize; j <= indexX + this.maskHalfSize; j++, maskX++) {
                    Color actColor = new Color(this.bi.getRGB(j, i));
                    r += (actColor.getRed() * this.mask[maskX][maskY]);
                    g += (actColor.getGreen() * this.mask[maskX][maskY]);
                    b += (actColor.getBlue() * this.mask[maskX][maskY]);
                }
            }

            divideByMaskSummary(r, g, b);

            r = utils.limitColor(r);
            g = utils.limitColor(g);
            b = utils.limitColor(b);

            Color newColor = new Color(r, g, b);
            biNew.setRGB(indexX, indexY, newColor.getRGB());
        }
    }

    private void divideByMaskSummary(int r, int g, int b){
        if (this.maskSummary != 0) {
            r /= this.maskSummary;
            g /= this.maskSummary;
            b /= this.maskSummary;
        }
    }
}
