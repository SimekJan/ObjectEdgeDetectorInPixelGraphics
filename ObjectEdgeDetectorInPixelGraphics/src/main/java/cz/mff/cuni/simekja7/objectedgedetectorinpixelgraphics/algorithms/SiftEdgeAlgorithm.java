/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.helpers.MatBufferedImageConvertor;
import java.awt.image.BufferedImage;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Custom Edge algorithm using methods similar to SIFT detection.
 * @author simek.jan
 */
public class SiftEdgeAlgorithm extends EdgeAlgorithm {

    public static double sigma1 = 1.0;
    public static double sigma2 = 4.0;
    public static int threshold = 40;
    
    @Override
    public BufferedImage run(BufferedImage inputImage) {
        Mat image = MatBufferedImageConvertor.bufferedImageToMat(inputImage);
        
        Mat lowBlur = new Mat();
        Mat highBlur = new Mat();
        
        Imgproc.GaussianBlur(image, lowBlur, new Size(5, 5), sigma1);
        Imgproc.GaussianBlur(image, highBlur, new Size(5, 5), sigma2);
        
        Core.subtract(lowBlur, highBlur, image);

        image = AdjustBrightness(image);
        
        Imgproc.threshold(image, image, threshold, 255, Imgproc.THRESH_BINARY);
                
        BufferedImage toReturn = MatBufferedImageConvertor.matToBufferedImage(image);
        
        return toReturn;
    }
    
    /**
     * Adjust the brighthness of a picture so that the brightest point is now white.
     * @param input Mat of the image to adjust.
     * @return Mat of adjusted image.
     */
    private Mat AdjustBrightness(Mat input) {
        double maxVal;
        Core.MinMaxLocResult minMaxLocResult = Core.minMaxLoc(input);
        maxVal = minMaxLocResult.maxVal;

        Mat normalizedImage = new Mat();
        input.convertTo(normalizedImage, CvType.CV_8U, 255.0 / maxVal);
        
        return normalizedImage;
    }
}
