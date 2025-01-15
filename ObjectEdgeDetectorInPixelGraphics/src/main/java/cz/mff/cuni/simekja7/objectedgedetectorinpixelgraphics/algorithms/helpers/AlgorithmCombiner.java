/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.helpers;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.CannyEdgeAlgorithm;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.EdgeAlgorithm;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.SobelEdgeAlgorithm;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author simek.jan
 */
public class AlgorithmCombiner extends EdgeAlgorithm {

    public EdgeAlgorithm alg1 = new CannyEdgeAlgorithm();
    public EdgeAlgorithm alg2 = new SobelEdgeAlgorithm();
    public Boolean useAnd = true;
    public int threshold = 1;
    
    @Override
    public BufferedImage run(BufferedImage inputImage) {
        BufferedImage alg1Result = alg1.run(inputImage);
        BufferedImage alg2Result = alg2.run(inputImage);
        
        Mat alg1Mat = MatBufferedImageConvertor.bufferedImageToMat(alg1Result);
        Mat alg2Mat = MatBufferedImageConvertor.bufferedImageToMat(alg2Result);
        
        Mat combined = new Mat();
        if(useAnd) {
            Core.bitwise_and(alg1Mat, alg2Mat, combined);
        }
        else { // useOr
            Core.bitwise_and(alg1Mat, alg2Mat, combined);
        }
            
        Mat resultMat = new Mat();
        Imgproc.threshold(combined, resultMat, threshold, 255, Imgproc.THRESH_BINARY);
        
        BufferedImage resultImage = MatBufferedImageConvertor.matToBufferedImage(resultMat);
        
        return resultImage;
    }
    
    /**
     * Change all parameters of combining at ones.
     * @param alg1 First new algorithm to use.
     * @param alg2 Second new algorithm to use.
     * @param useAnd Whether use AND (true) or OR (false) of the algorithm results.
     * @param threshold Threshold to add to result if combination is higher than. For AND can be as low as 1, for OR higher value is preferable.
     */
    public void changeParams(EdgeAlgorithm alg1, EdgeAlgorithm alg2, Boolean useAnd, int threshold) {
        this.alg1 = alg1;
        this.alg2 = alg2;
        this.useAnd = useAnd;
        this.threshold = threshold;
    }
}
