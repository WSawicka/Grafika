/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.image.BufferedImage;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author mloda
 */
@Getter
@Setter
public class Image {
    private String type;
    private Integer width;
    private Integer height;
    private Integer maxColorValue;
    private BufferedImage content;
}
