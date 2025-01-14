/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.SIFT;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Custom Edge algorithm using methods similar to SIFT detection.
 * @author simek.jan
 */
public class SiftEdgeAlgorithm extends EdgeAlgorithm {

    @Override
    public Mat run(String image_name) {
        Mat inputImage = Imgcodecs.imread(image_name);
        
        
        
        /*
        // Step 1: DoG
        List<Mat> diff_of_gs = ComputeDifferenceOfGaussians(PrepareBluredImages(image, 8));
                
        // Step 2: KeyPoints
        List<KeyPoint> keypoints = FindKeyPoints(diff_of_gs, 10); 
        
        System.out.println(keypoints.size());
        
        // Imgproc.threshold(combinedImage, combinedImage, 10, 255, Imgproc.THRESH_BINARY);
        
        /*combinedImage = AdjustBrightness(combinedImage);
        Imgcodecs.imwrite("substracted_blurs_lm.jpg", combinedImage);
        
        Imgproc.GaussianBlur(combinedImage, combinedImage, new Size(5,5), 2.0);
        
        // Mat combinedImage2 = new Mat();
        // Imgproc.GaussianBlur(combinedImage, combinedImage2, new Size(5,5), 2.0);
        Mat canny = new CannyEdgeAlgorithm().run(image_name);
        Imgproc.GaussianBlur(canny, canny, new Size(5,5), 2.0);*/
        
        Mat combinedWithCanny = new Mat();
        /*Core.addWeighted(canny, 0.5, combinedImage, 0.5, 0, combinedWithCanny);
        
        // combinedWithCanny = AdjustBrightness(combinedWithCanny);
        
        // Imgproc.threshold(combinedWithCanny, combinedWithCanny, 10, 255, Imgproc.THRESH_BINARY);
        
        Imgproc.GaussianBlur(combinedWithCanny, lowBlur, new Size(5, 5), 1.0);
        Imgproc.GaussianBlur(combinedWithCanny, highBlur, new Size(5, 5), 2.0);
        
        Core.subtract(lowBlur, highBlur, combinedWithCanny);

        combinedWithCanny = AdjustBrightness(combinedWithCanny);
        
        Imgproc.threshold(combinedWithCanny, combinedWithCanny, 10, 255, Imgproc.THRESH_BINARY);
        
        // Imgcodecs.imwrite("combined_with_canny.jpg", combinedWithCanny);*/
        
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
        for(int i = 0; i < count; i++) {
            Mat another_one = new Mat();
            Imgproc.GaussianBlur(input_image, another_one, new Size(5, 5), 0.5 * (i+1));
            blured_imgs.add(another_one);
        }
        return blured_imgs;
    }
    
    /**
     * Creates difference of Gaussians from given list of blurred images.
     * @param blured_images The list to process.
     * @return List with the differences of Gaussians.
     */
    private List<Mat> ComputeDifferenceOfGaussians(List<Mat> blured_images) {
        List<Mat> diff_of_gs = new ArrayList<>();
        for(int i = 1; i < blured_images.size(); i++) {
            Mat combined_image = new Mat();
            Core.subtract(blured_images.get(i), blured_images.get(i-1), combined_image);
            combined_image = AdjustBrightness(combined_image);
            diff_of_gs.add(combined_image);
        }
        return diff_of_gs;
    }
    
    /**
     * Creates list of edge-keypoints from DoG.
     * @param dog_list List with DoG Mats.
     * @param threshold Threshold for determining if is valid point.
     * @return List of edge-keypoints.
     */
    private List<KeyPoint> FindKeyPoints(List<Mat> dog_list, double threshold) {
        List<KeyPoint> keypoints = new ArrayList<>();
        
        for (int i = 1; i < dog_list.size() - 1; i++) {
            Mat current_dog = dog_list.get(i);
            Mat prev_dog = dog_list.get(i - 1);
            Mat next_dog = dog_list.get(i + 1);

            for (int y = 1; y < current_dog.rows() - 1; y++) {
                for (int x = 1; x < current_dog.cols() - 1; x++) {
                    double current_val = current_dog.get(y, x)[0];

                    boolean is_local_extrema = true;
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            if (current_val <= current_dog.get(y + dy, x + dx)[0]) {
                                is_local_extrema = false;
                                break;
                            }
                        }
                        if (!is_local_extrema) break;
                    }

                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            if (current_val <= prev_dog.get(y + dy, x + dx)[0] || current_val <= next_dog.get(y + dy, x + dx)[0]) {
                                is_local_extrema = false;
                                break;
                            }
                        }
                        if (!is_local_extrema) break;
                    }

                    if (is_local_extrema && Math.abs(current_val) > threshold) {
                        KeyPoint keypoint = new KeyPoint(x, y, 1);
                        keypoints.add(keypoint);
                    }
                }
            }
        }
        
        // TODO: remove later from here
        MatOfKeyPoint matOfKeyPoints = new MatOfKeyPoint();
        matOfKeyPoints.fromList(keypoints);

        // Draw the keypoints onto the blank image
        Mat outputImage = dog_list.get(0).clone();
        Imgproc.threshold(outputImage, outputImage, 0, 0, Imgproc.THRESH_BINARY);

        Features2d.drawKeypoints(outputImage, matOfKeyPoints, outputImage, new Scalar(0, 255, 0));

        // Save the output image with keypoints drawn
        Imgcodecs.imwrite("keypoints_output.png", outputImage);
        
        return keypoints;
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
