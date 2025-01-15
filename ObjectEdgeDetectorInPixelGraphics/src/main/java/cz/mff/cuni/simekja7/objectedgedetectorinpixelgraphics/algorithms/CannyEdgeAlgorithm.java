/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.helpers.MatToBufferedImageConvertor;
import java.awt.image.BufferedImage;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * CannyEdge algorithm using OpenCV implementation.
 * @author simek.jan
 */
public class CannyEdgeAlgorithm extends EdgeAlgorithm {

    static int treshold1 = 400;
    static int treshold2 = 400;
    
    @Override
    public BufferedImage run(String image_name) {
        Mat image = Imgcodecs.imread(image_name, Imgcodecs.IMREAD_GRAYSCALE);
        Mat edges = new Mat();
        Imgproc.Canny(image, edges, treshold1, treshold2);
        BufferedImage toReturn = MatToBufferedImageConvertor.convert(edges);
        return toReturn;
    }    
}
