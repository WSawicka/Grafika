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
public class Histogram {

    private final int MAX_COLOR = 255;
    private BufferedImage bi;
    private int[] lutR = new int[this.MAX_COLOR + 1];
    private int[] lutG = new int[this.MAX_COLOR + 1];
    private int[] lutB = new int[this.MAX_COLOR + 1];
    private double[] distributionR = new double[this.MAX_COLOR + 1];
    private double[] distributionG = new double[this.MAX_COLOR + 1];
    private double[] distributionB = new double[this.MAX_COLOR + 1];
    private int maxR = 0;
    private int maxG = 0;
    private int maxB = 0;
    private int minR = this.MAX_COLOR;
    private int minG = this.MAX_COLOR;
    private int minB = this.MAX_COLOR;

    public Histogram(BufferedImage bi) {
        this.bi = bi;
        setMinMax();
    }

    private void setMinMax() {
        for (int y = 0; y < this.bi.getHeight(); y++) {
            for (int x = 0; x < this.bi.getWidth(); x++) {
                Color color = new Color(this.bi.getRGB(x, y));
                
                //FIXME refactor kodu, wyciągnąć do metody i sparametryzować
                if (color.getRed() < this.minR) this.minR = color.getRed();
                else if (color.getRed() > this.maxR) this.maxR = color.getRed();
                if (color.getGreen() < this.minG) this.minG = color.getGreen();
                else if (color.getGreen() > this.maxG) this.maxG = color.getGreen();
                if (color.getBlue() < this.minB) this.minB = color.getBlue();
                else if (color.getBlue() > this.maxB) this.maxB = color.getBlue();
            }
        }
    }

    public void setStretchLUT() {
        for (int i = 0; i < this.MAX_COLOR + 1; i++) {
            this.lutR[i] = ((this.MAX_COLOR) / (this.maxR - this.minR)) * (i - this.minR);
            this.lutG[i] = ((this.MAX_COLOR) / (this.maxG - this.minG)) * (i - this.minG);
            this.lutB[i] = ((this.MAX_COLOR) / (this.maxB - this.minB)) * (i - this.minB);
        }
    }

    private void setDistribution() {
        for (int j = 0; j < this.bi.getHeight(); j++) {
            for (int i = 0; i < this.bi.getWidth(); i++) {
                Color color = new Color(this.bi.getRGB(i, j));
                this.distributionR[color.getRed()]++;
                this.distributionG[color.getGreen()]++;
                this.distributionB[color.getBlue()]++;
            }
        }

        double sumR = this.distributionR[0];
        double sumG = this.distributionG[0];
        double sumB = this.distributionB[0];
        //to make a proper distribuant
        double before, after;
        for (int i = 1; i < this.MAX_COLOR + 1; i++) {
            double actR = this.distributionR[i];
            double actG = this.distributionG[i];
            double actB = this.distributionB[i];
            this.distributionR[i] += sumR;
            this.distributionG[i] += sumG;
            this.distributionB[i] += sumB;
            sumR += actR;
            sumG += actG;
            sumB += actB;
        }

        for (int i = 0; i < this.MAX_COLOR + 1; i++) {
            int allPixels = this.bi.getHeight() * this.bi.getWidth();
            this.distributionR[i] /= allPixels;
            this.distributionG[i] /= allPixels;
            this.distributionB[i] /= allPixels;
        }
    }

    private int getIndexOfFirstNotZeroElement(double[] table) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != 0.0) return i;
        }
        return table.length;
    }

    public void setEqualizingLUT() {
        setDistribution();
        double dR0 = this.distributionR[getIndexOfFirstNotZeroElement(this.distributionR)];
        double dG0 = this.distributionG[getIndexOfFirstNotZeroElement(this.distributionG)];
        double dB0 = this.distributionB[getIndexOfFirstNotZeroElement(this.distributionB)];

        for (int i = 0; i < this.MAX_COLOR + 1; i++) {
            double a = this.distributionR[i];
            double b = (this.distributionR[i] - dR0);
            double c = (1 - dR0);
            double d = ((this.distributionR[i] - dR0) / (1 - dR0));
            
            this.lutR[i] = (int)(((this.distributionR[i] - dR0) / (1 - dR0)) * this.MAX_COLOR);
            this.lutG[i] = (int)(((this.distributionG[i] - dG0) / (1 - dG0)) * this.MAX_COLOR);
            this.lutB[i] = (int)(((this.distributionB[i] - dB0) / (1 - dB0)) * this.MAX_COLOR);
        }
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

    public BufferedImage applyLutOnHistogram() {
        BufferedImage biNew = getCloneOfBufferedImage();

        for (int indexY = 0; indexY < this.bi.getHeight(); indexY++) {
            for (int indexX = 0; indexX < this.bi.getWidth(); indexX++) {
                Color actColor = new Color(this.bi.getRGB(indexX, indexY));
                int r = actColor.getRed();
                int g = actColor.getGreen();
                int b = actColor.getBlue();

                int newR = this.lutR[r];
                int newG = this.lutG[g];
                int newB = this.lutB[b];
                
                newR = limitColor(newR);
                newG = limitColor(newG);
                //TODO poprawić
                if (newR < 0) newR = 0;
                else if (newR > 255) newR = 255;
                if (newG < 0) newG = 0;
                else if (newG > 255) newG = 255;
                if (newB < 0) newB = 0;
                else if (newB > 255) newB = 255;

                Color newColor = new Color(newR, newG, newB);
                biNew.setRGB(indexX, indexY, newColor.getRGB());
            }
        }

        return biNew;
    }

    private int limitColor(int color) {
        if(color > 255) {
            return 255;
        } else if (color < 0) {
            return 0;
        } else {
            return color;
        }
    }
}
