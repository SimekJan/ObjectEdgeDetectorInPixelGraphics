/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.helpers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Helper class that converts BufferedImage and Mat in both directions.
 * @author simek.jan
 */
public class MatBufferedImageConvertor {
    
    /**
     * Converts given BufferedImage to Mat ensuring it is in gray-scale in process.
     * @param image Image to convert.
     * @return Result Mat.
     */
    public static Mat bufferedImageToMat(BufferedImage image) {
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);  
        Graphics g = grayImage.getGraphics();  
        g.drawImage(image, 0, 0, null);  
        g.dispose();  
        
        byte[] data = ((DataBufferByte) grayImage.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(grayImage.getHeight(), grayImage.getWidth(), CvType.CV_8UC1);
        mat.put(0, 0, data);
        return mat;
    }

    /**
     * Converts given Mat to BufferedImage ensuring it is in gray-scale in process.
     * @param mat Mat to convert.
     * @return Result image.
     */
    public static BufferedImage matToBufferedImage(Mat mat) {
        int width = mat.width();
        int height = mat.height();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        byte[] data = new byte[width * height * (int) mat.elemSize()];
        mat.get(0, 0, data);
        image.getRaster().setDataElements(0, 0, width, height, data);
        return image;
    }
}
