/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.Arrays;
import static jdk.nashorn.internal.objects.NativeMath.exp;
import model.Image;

/**
 *
 * @author mloda
 */
public class Binarization {

    private Image image;
    private int[] histogram = new int[256];

    public Binarization(Image image) {
        this.image = image;
    }

    public Image custom(float boundary) {
        BufferedImage bi = this.image.getContent();
        for (int j = 0; j < bi.getHeight(); j++) {
            for (int i = 0; i < bi.getWidth(); i++) {
                Color c = new Color(bi.getRGB(i, j));
                float brightness = (int) (c.getRed() * 0.299 + c.getGreen() * 0.587 + c.getBlue() * 0.114);
                if (brightness > boundary) {
                    bi.setRGB(i, j, Color.WHITE.getRGB());
                } else {
                    bi.setRGB(i, j, Color.BLACK.getRGB());
                }
            }
        }
        image.setContent(bi);
        return image;
    }

    public Image blackSelection(int percent) {
        BufferedImage bi = this.image.getContent();
        setHistogram(bi);
        // turn into cumulative histogram
        for (int i = 1; i <= 255; i++) {
            this.histogram[i] += this.histogram[i - 1];
        }

        int boundary = 0;
        int pixels = bi.getHeight() * bi.getWidth();
        int blackPixels = (pixels * percent) / 100;
        for (int i = 0; i < 256; i++) {
            if (this.histogram[i] >= blackPixels) {
                boundary = i;
                break;
            }
        }
        doBlackWhite(bi, boundary);
        this.image.setContent(bi);
        return image;
    }

    public Image meanSelection() {
        BufferedImage bi = this.image.getContent();
        setHistogram(bi);

        int meanFirst = getMeanFrom(this.histogram);

        int times = 0;
        while (true) {
            int indexFirstMean = getIndexOfValueNearest(meanFirst, this.histogram);

            int[] leftSide = Arrays.copyOfRange(this.histogram, 0, indexFirstMean - 1);
            int[] rightSide = Arrays.copyOfRange(this.histogram, indexFirstMean, this.histogram.length);

            int leftMean = getMeanFrom(leftSide);
            int rightMean = getMeanFrom(rightSide);

            int newMean = (leftMean + rightMean) / 2;
            int difference = Math.abs(newMean - meanFirst);
            if (difference == 0 || times == 100) {
                break;
            }
            meanFirst = newMean;
            times++;
        }

        doBlackWhite(bi, getIndexOfValueNearest(meanFirst, this.histogram));
        this.image.setContent(bi);
        return image;
    }

    public Image entropySelection() {
        BufferedImage bi = this.image.getContent();
        setHistogram(bi);

        //table of probabilities
        int pixels = bi.getHeight() * bi.getWidth();
        BigDecimal[] probability = new BigDecimal[this.histogram.length];
        for (int i = 0; i < probability.length; i++) {
            probability[i] = BigDecimal.valueOf((long) this.histogram[i]).divide(BigDecimal.valueOf((long) pixels));
            if (probability[i] == BigDecimal.valueOf(0)) {
                probability[i] = BigDecimal.valueOf((long) 0.00000001);
            }
        }

        //entropy table
        double[] entropy = new double[this.histogram.length];
        for (int i = 0; i < entropy.length; i++) {
            entropy[i] = Math.log(probability[i].doubleValue()) * probability[i].doubleValue();
        }

        //find index of smallest entropy       
        //index is the boundary of black & white  
        int indexMin = 0;
        double min = entropy[indexMin];

        for (int i = 1; i < entropy.length; i++) {
            if (entropy[i] < min) {
                min = entropy[i];
                indexMin = i;
            }
        }

        doBlackWhite(bi, indexMin);

        this.image.setContent(bi);
        return image;
    }

    private void setHistogram(BufferedImage bi) {
        for (int j = 0; j < bi.getHeight(); j++) {
            for (int i = 0; i < bi.getWidth(); i++) {
                Color c = new Color(bi.getRGB(i, j));
                int brightness = (int) (c.getRed() * 0.299 + c.getGreen() * 0.587 + c.getBlue() * 0.114);
                ++this.histogram[brightness];
            }
        }
    }

    private int getMeanFrom(int[] table) {
        int mean = 0;
        for (int i = 0; i < table.length; i++) {
            mean += (table[i] * i);
        }
        mean /= table.length;
        return mean;
    }

    private int getIndexOfValueNearest(int value, int[] table) {
        int index = 0;
        int diff = value;

        for (int i = 0; i < table.length; i++) {
            if (Math.abs((table[i] * i) - value) < diff) {
                index = i;
                diff = Math.abs((table[i] * i) - value);
            }
        }

        return index;
    }

    public void doBlackWhite(BufferedImage bi, int boundary) {
        for (int j = 0; j < bi.getHeight(); j++) {
            for (int i = 0; i < bi.getWidth(); i++) {
                Color c = new Color(bi.getRGB(i, j));
                int brightness = (int) (c.getRed() * 0.299 + c.getGreen() * 0.587 + c.getBlue() * 0.114);
                if (brightness > boundary) {
                    bi.setRGB(i, j, Color.WHITE.getRGB());
                } else {
                    bi.setRGB(i, j, Color.BLACK.getRGB());
                }
            }
        }
    }
}
