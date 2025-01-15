/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.helpers;

import java.awt.image.BufferedImage;
import org.opencv.core.Mat;

/**
 *
 * @author simek.jan
 */
public class MatToBufferedImageConvertor {
    public static BufferedImage convert(Mat mat) {
        int width = mat.width();
        int height = mat.height();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

        mat.get(0, 0, ((java.awt.image.DataBufferByte) image.getRaster().getDataBuffer()).getData());
        
        return image;
    }
}
