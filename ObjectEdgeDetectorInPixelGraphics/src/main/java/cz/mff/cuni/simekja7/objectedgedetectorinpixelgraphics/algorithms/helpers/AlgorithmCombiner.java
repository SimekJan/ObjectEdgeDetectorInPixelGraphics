/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.helpers;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.CannyEdgeAlgorithm;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.EdgeAlgorithm;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.SobelEdgeAlgorithm;
import java.awt.image.BufferedImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Class for combining two EdgeAlgorithms using various methods.
 * @author simek.jan
 */
public class AlgorithmCombiner extends EdgeAlgorithm {

    public enum COMBINE_MODE {AND, OR, WEIGHTED}
    
    public static EdgeAlgorithm alg1 = new CannyEdgeAlgorithm();
    public static EdgeAlgorithm alg2 = new SobelEdgeAlgorithm();
    public static COMBINE_MODE mode = COMBINE_MODE.AND;
    
    // Only for weighted variant
    public static int threshold = 1;
    public static double alpha1 = 0.5;
    public static double alpha2 = 0.5;
    
    /**
     * Runs both algorithms and then merges results in a way entered to mode variable.
     * @param inputImage 
     * @return Image representing combined results of both algorithms.
     */
    @Override
    public BufferedImage run(BufferedImage inputImage) {
        BufferedImage alg1Result = alg1.run(inputImage);
        BufferedImage alg2Result = alg2.run(inputImage);
        
        Mat alg1Mat = MatBufferedImageConvertor.bufferedImageToMat(alg1Result);
        Mat alg2Mat = MatBufferedImageConvertor.bufferedImageToMat(alg2Result);
        
        Mat combined = new Mat();
        Mat resultMat = new Mat();

        switch (mode) {
            case WEIGHTED -> {
                Core.addWeighted(alg1Mat, alpha1, alg2Mat, alpha2, 0, combined);
                Imgproc.threshold(combined, resultMat, threshold, 255, Imgproc.THRESH_BINARY);
            }
            case OR -> {
                Core.bitwise_or(alg1Mat, alg2Mat, combined);
                resultMat = combined;
            }
            default -> {
                // AND
                Core.bitwise_and(alg1Mat, alg2Mat, combined);
                resultMat = combined;
            }
        }
        
        BufferedImage resultImage = MatBufferedImageConvertor.matToBufferedImage(resultMat);
        
        return resultImage;
    }
}
