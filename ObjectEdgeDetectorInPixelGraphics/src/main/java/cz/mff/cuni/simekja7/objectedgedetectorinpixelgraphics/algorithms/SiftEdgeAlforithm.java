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
 *
 * @author simek.jan
 */
public class SiftEdgeAlforithm extends EdgeAlgorithm {

    @Override
    public Mat run(String image_name) {
        Mat image = Imgcodecs.imread(image_name, Imgcodecs.IMREAD_GRAYSCALE);

        Mat lowBlur = new Mat();
        Mat highBlur = new Mat();
        Imgproc.GaussianBlur(image, lowBlur, new Size(5, 5), 1.0);
        Imgproc.GaussianBlur(image, highBlur, new Size(5, 5), 4.0);

        Mat combinedImage = new Mat();
        Core.subtract(lowBlur, highBlur, combinedImage);

        Imgproc.threshold(combinedImage, combinedImage, 10, 255, Imgproc.THRESH_BINARY);
        
        combinedImage = AdjustBrightness(combinedImage);
        // Imgcodecs.imwrite("substracted_blurs_lm.jpg", combinedImage);
        
        Imgproc.GaussianBlur(combinedImage, combinedImage, new Size(5,5), 2.0);
        
        // Mat combinedImage2 = new Mat();
        // Imgproc.GaussianBlur(combinedImage, combinedImage2, new Size(5,5), 2.0);
        Mat canny = new CannyEdgeAlgorithm().run(image_name);
        Imgproc.GaussianBlur(canny, canny, new Size(5,5), 2.0);
        
        Mat combinedWithCanny = new Mat();
        Core.addWeighted(canny, 0.5, combinedImage, 0.5, 0, combinedWithCanny);
        
        // combinedWithCanny = AdjustBrightness(combinedWithCanny);
        
        // Imgproc.threshold(combinedWithCanny, combinedWithCanny, 10, 255, Imgproc.THRESH_BINARY);
        
        Imgproc.GaussianBlur(combinedWithCanny, lowBlur, new Size(5, 5), 1.0);
        Imgproc.GaussianBlur(combinedWithCanny, highBlur, new Size(5, 5), 2.0);
        
        Core.subtract(lowBlur, highBlur, combinedWithCanny);

        combinedWithCanny = AdjustBrightness(combinedWithCanny);
        
        Imgproc.threshold(combinedWithCanny, combinedWithCanny, 10, 255, Imgproc.THRESH_BINARY);
        
        // Imgcodecs.imwrite("combined_with_canny.jpg", combinedWithCanny);
        
        return combinedWithCanny;
    }
    
    public static Mat AdjustBrightness(Mat input) {
        double minVal, maxVal;
        Core.MinMaxLocResult minMaxLocResult = Core.minMaxLoc(input);
        minVal = minMaxLocResult.minVal;
        maxVal = minMaxLocResult.maxVal;

        Mat normalizedImage = new Mat();
        input.convertTo(normalizedImage, CvType.CV_8U, 255.0 / maxVal);
        
        return normalizedImage;
    }
}
