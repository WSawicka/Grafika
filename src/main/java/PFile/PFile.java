/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PFile;

import java.io.IOException;
import java.util.Scanner;
import model.Image;

/**
 *
 * @author mloda
 */
public interface PFile {
    public void read(Image image, Scanner reader) throws IOException;
}
