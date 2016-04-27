/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PFile;

import PFile.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import model.Image;
/**
 *
 * @author mloda
 */
public class ReadFile {
    public static Image readImage(String path) throws IOException {
        Image image = new Image();
        FileInputStream file = new FileInputStream(path);
        Scanner reader = new Scanner(file);
        int amountOfChars = 0;

        // pobierz typ, liczbę kolumn i wierszy oraz ilosć kolorów
        for (int i = 0; i < 3; i++) {
            if (reader.hasNextLine()) {
                if (image.getType() == null) {
                    String type = getLineWithoutComment(reader);
                    amountOfChars += type.length();
                    image.setType(type);
                } else if (image.getHeight() == null) {
                    // wczytaj oba
                    String heightAndWidth = getLineWithoutComment(reader).trim();
                    amountOfChars += heightAndWidth.length() + 1;
                    
                    String height = heightAndWidth.substring(heightAndWidth.indexOf(" ") + 1, heightAndWidth.length());
                    image.setHeight(Integer.valueOf(height));

                    String width = heightAndWidth.substring(0, heightAndWidth.indexOf(" "));
                    image.setWidth(Integer.valueOf(width));
                } else if (image.getMaxColorValue() == null) {
                    Integer maxColor = Integer.valueOf(getLineWithoutComment(reader));
                    amountOfChars += String.valueOf(maxColor).length() + 2;
                    image.setMaxColorValue(maxColor);
                }
            }
        }
        
        PFile pFile = (image.getType().equals("P3")) ? new P3File() : new P6File(path, file, amountOfChars);
        pFile.read(image, reader);
        return image;
    }

    private static String getLineWithoutComment(Scanner reader) throws IOException {
        String tmp = null;
        String value = null;
        do {
            tmp = reader.nextLine();
            if (tmp.contains("#")) {
                value = tmp.substring(0, tmp.indexOf('#'));
            } else {
                value = tmp;
            }
        } while (tmp.startsWith("#"));
        return value;
    }
}
