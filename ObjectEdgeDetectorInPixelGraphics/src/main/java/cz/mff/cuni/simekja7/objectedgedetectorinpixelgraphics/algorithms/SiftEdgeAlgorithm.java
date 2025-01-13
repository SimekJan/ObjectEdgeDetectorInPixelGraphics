/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public Mat run(String image_name) {
        Mat image = Imgcodecs.imread(image_name, Imgcodecs.IMREAD_GRAYSCALE);        
        image = AdjustBrightness(image);
        
        Mat lowBlur = new Mat();
        Mat highBlur = new Mat();
        Imgproc.GaussianBlur(image, lowBlur, new Size(5, 5), 1.0);
        Imgproc.GaussianBlur(image, highBlur, new Size(5, 5), 4.0);

        Mat combinedImage = new Mat();
        Core.subtract(lowBlur, highBlur, combinedImage);

        // Imgproc.threshold(combinedImage, combinedImage, 10, 255, Imgproc.THRESH_BINARY);
        
        combinedImage = AdjustBrightness(combinedImage);
        Imgcodecs.imwrite("substracted_blurs_lm.jpg", combinedImage);
        
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
    
    /**
     * Creates a list of different blurred versions of given image.
     * @param input_image The image to prepare list for.
     * @param count The number of versions to be done.
     * @return List of blurred versions.
     */
    private List<Mat> PrepareBluredImages(Mat input_image, int count) {
        List<Mat> blured_imgs = new ArrayList<>();
        // TODO
        return blured_imgs;
    }
    
    /**
     * Creates difference of Gaussians from given list of blurred images.
     * @param blured_images The list to process.
     * @return List with the differences of Gaussians.
     */
    private List<Mat> ComputeDifferenceOfGaussians(List<Mat> blured_images) {
        List<Mat> diff_of_gs = new ArrayList<>();
        // TODO
        return diff_of_gs;
    }
    
    /**
     * Adjust the brighthness of a picture so that the brightest point is now white.
     * @param input Mat of the image to adjust.
     * @return Mat of adjusted image.
     */
    private Mat AdjustBrightness(Mat input) {
        double minVal, maxVal;
        Core.MinMaxLocResult minMaxLocResult = Core.minMaxLoc(input);
        minVal = minMaxLocResult.minVal;
        maxVal = minMaxLocResult.maxVal;

        Mat normalizedImage = new Mat();
        input.convertTo(normalizedImage, CvType.CV_8U, 255.0 / maxVal);
        
        return normalizedImage;
    }
}
