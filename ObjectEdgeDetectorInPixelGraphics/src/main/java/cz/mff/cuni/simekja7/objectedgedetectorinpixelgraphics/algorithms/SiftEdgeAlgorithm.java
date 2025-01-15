/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Custom Edge algorithm using methods similar to SIFT detection.
 * @author simek.jan
 */
public class SiftEdgeAlgorithm extends EdgeAlgorithm {

    double sigma1 = 1.0;
    double sigma2 = 4.0;
    int threshold = 40;
    
    @Override
    public Mat run(String image_name) {
        Mat image = Imgcodecs.imread(image_name, Imgcodecs.IMREAD_GRAYSCALE);
        
        Mat lowBlur = new Mat();
        Mat highBlur = new Mat();
        
        Imgproc.GaussianBlur(image, lowBlur, new Size(5, 5), sigma1);
        Imgproc.GaussianBlur(image, highBlur, new Size(5, 5), sigma2);
        
        Core.subtract(lowBlur, highBlur, image);

        image = AdjustBrightness(image);
        
        Imgproc.threshold(image, image, threshold, 255, Imgproc.THRESH_BINARY);
                
        return image;
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
