/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PFile;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import model.Image;

/**
 *
 * @author mloda
 */
@AllArgsConstructor
public class P6File implements PFile{
    private String path;
    private FileInputStream is;
    private int amountOfChars;

    @Override
    public void read(Image image, Scanner reader)  throws IOException{
        is = new FileInputStream(path);
        is.skip(amountOfChars);

        BufferedInputStream bufferedInputStream = new BufferedInputStream(is);
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        if (image.getMaxColorValue() < 256) {
            byte[] pixelsbytes = new byte[image.getWidth() * 3];
            for (int i = 0; i < image.getHeight(); i++) {

                bufferedInputStream.read(pixelsbytes);
                for (int j = 0; j < (pixelsbytes.length / 3); j++) {
                    byte red = (byte) ((pixelsbytes[j * 3] * 255) / image.getMaxColorValue());
                    byte green = (byte) ((pixelsbytes[j * 3 + 1] * 255) / image.getMaxColorValue());
                    byte blue = (byte) ((pixelsbytes[j * 3 + 2] * 255) / image.getMaxColorValue());
                    int color = ((red & 0xff) << 16) | ((green & 0xff) << 8) | (blue & 0xff);
                    newImage.setRGB(j, i, color);
                }
            }
        } else {
            byte[] pixelsbytes = new byte[image.getWidth() * 3 * 2];
            for (int i = 0; i < image.getHeight(); i++) {
                bufferedInputStream.read(pixelsbytes);
                for (int j = 0; j < (pixelsbytes.length / 6); j++) {

                    int r = (pixelsbytes[j * 6] << 8);
                    r += pixelsbytes[j * 6 + 1];
                    int g = (pixelsbytes[j * 6 + 2] << 8);
                    g += pixelsbytes[j * 6 + 3];
                    int b = (pixelsbytes[j * 6 + 4] << 8);
                    b += pixelsbytes[j * 6 + 5];

                    r = r & 0xffff;
                    g = g & 0xffff;
                    b = b & 0xffff;

                    int red = (r * 255) / image.getMaxColorValue();
                    int green = (g * 255) / image.getMaxColorValue();
                    int blue = (b * 255) / image.getMaxColorValue();
                    int color = ((red & 0xff) << 16) | ((green & 0xff) << 8) | (blue & 0xff);
                    newImage.setRGB(j, i, color);
                }
            }
        }
        image.setContent(newImage);
    }
}
