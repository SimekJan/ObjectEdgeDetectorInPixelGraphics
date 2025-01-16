/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.helpers.MatBufferedImageConvertor;
import java.awt.image.BufferedImage;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * CannyEdge algorithm using OpenCV implementation.
 * @author simek.jan
 */
public class CannyEdgeAlgorithm extends EdgeAlgorithm {

    public static int treshold1 = 400;
    public static int treshold2 = 400;
    
    /**
     * Runs CannyEdge algorithm for given image.
     * @param inputImage
     * @return CannyEdge output image.
     */
    @Override
    public BufferedImage run(BufferedImage inputImage) {
        Mat image = MatBufferedImageConvertor.bufferedImageToMat(inputImage);
        Mat edges = new Mat();
        Imgproc.Canny(image, edges, treshold1, treshold2);
        BufferedImage toReturn = MatBufferedImageConvertor.matToBufferedImage(edges);
        return toReturn;
    }    
}
