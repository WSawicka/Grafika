/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import model.Image;

/**
 *
 * @author mloda
 */
public class P3File implements PFile{
    @Override
    public void read(Image image, Scanner reader) throws IOException{
        int[][] r = new int[image.getHeight()][image.getWidth()];
        int[][] g = new int[image.getHeight()][image.getWidth()];
        int[][] b = new int[image.getHeight()][image.getWidth()];
        
        int x = 0;
        while (reader.hasNextInt() && x < image.getHeight()) {
            for (int i = 0; i < image.getWidth(); i++) {
                r[x][i] = Integer.valueOf(getIntWithoutComment(reader).trim());
                g[x][i] = Integer.valueOf(getIntWithoutComment(reader).trim());
                b[x][i] = Integer.valueOf(getIntWithoutComment(reader).trim());
            }
            x++;
        }

        BufferedImage img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int rX = r[i][j];
                int gX = g[i][j];
                int bX = b[i][j];

                rX = rX & 0xffff; //ograniczenie do 65536
                gX = gX & 0xffff;
                bX = bX & 0xffff;
                // 0xffff - 65536
                //0xff - 255
                int red = (rX * 255) / image.getMaxColorValue();
                int green = (gX * 255) / image.getMaxColorValue();
                int blue = (bX * 255) / image.getMaxColorValue();

                int color = ((red & 0xff) << 16) | ((green & 0xff) << 8) | (blue & 0xff);//bajt r, g, b, jeden bajt to 2^8
                img.setRGB(j, i, color);
            }
        }
        image.setContent(img);
    }

    private static String getIntWithoutComment(Scanner reader) throws IOException {
        String tmp = null;
        do {
            tmp = reader.next();
            if (tmp.startsWith("#")) {
                reader.nextLine();
            }
        } while (tmp.startsWith("#"));
        return tmp;
    }
}
