/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import model.Image;

/**
 *
 * @author mloda
 */
public class PointByPoint {

    private Image image;

    public PointByPoint(Image image) {
        this.image = image;
    }

    public Image add(float r, float g, float b) {
        BufferedImage bi;
        float[] factors = new float[]{1.0f, 1.0f, 1.0f};
        float[] offsets = new float[]{(float) r, (float) g, (float) b};
        RescaleOp op = new RescaleOp(factors, offsets, null);
        bi = op.filter(image.getContent(), null);
        image.setContent(bi);
        return image;
    }

    public Image less(float r, float g, float b) {
        BufferedImage bi;
        float[] factors = new float[]{1.0f, 1.0f, 1.0f};
        float[] offsets = new float[]{(float) (-r), (float) (-g), (float) (-b)};
        RescaleOp op = new RescaleOp(factors, offsets, null);
        bi = op.filter(image.getContent(), null);
        image.setContent(bi);
        return image;
    }

    public Image multiply(float r, float g, float b) {
        BufferedImage bi;
        float[] factors = new float[]{(float) r, (float) g, (float) b};
        float[] offsets = new float[]{0.0f, 0.0f, 0.0f};
        RescaleOp op = new RescaleOp(factors, offsets, null);
        bi = op.filter(image.getContent(), null);
        image.setContent(bi);
        return image;
    }

    public Image divide(float r, float g, float b) {
        BufferedImage bi;
        float[] factors = new float[]{(float) (1 / r), (float) (1 / g), (float) (1 / b)};
        float[] offsets = new float[]{0.0f, 0.0f, 0.0f};
        RescaleOp op = new RescaleOp(factors, offsets, null);
        bi = op.filter(image.getContent(), null);
        image.setContent(bi);
        return image;
    }

    public Image changeBrightness(float value) {
        BufferedImage bi;
        RescaleOp op = new RescaleOp(1 + value / 100, 0.0f, null);
        bi = op.filter(image.getContent(), null);
        image.setContent(bi);
        return image;
    }

    public Image grayNormal() {
        BufferedImage bi = this.image.getContent();
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                int sum = (red + green + blue) / 3;
                Color newColor = new Color(sum, sum, sum);
                bi.setRGB(j, i, newColor.getRGB());
            }
        }
        image.setContent(bi);
        return image;
    }

    public Image grayNatural() {
        BufferedImage bi = this.image.getContent();
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                int red = (int) (c.getRed() * 0.21);
                int green = (int) (c.getGreen() * 0.72);
                int blue = (int) (c.getBlue() * 0.07);
                int sum = red + green + blue;
                Color newColor = new Color(sum, sum, sum);
                bi.setRGB(j, i, newColor.getRGB());
            }
        }
        image.setContent(bi);
        return image;
    }
}
