/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics;

import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Size;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.SIFT;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author simek.jan
 */
public class ObjectEdgeDetectorInPixelGraphics {
    
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
    public static void main(String[] args) {        
        // CannyEdgeExample();
        // SobelEdgeExample();
        // CannySobelMergeEdgeExample();
        SIFTEdgeExample();
        // LaplacianEdgeExample();
    }
    
    public static Mat CannyEdgeExample() {
        Mat src = Imgcodecs.imread("london.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat edges = new Mat();
        Imgproc.Canny(src, edges, 400, 200);
        Imgcodecs.imwrite("./canny_edges.jpg", edges);
        return edges;
    }
    
    public static Mat SobelEdgeExample() {
        Mat src = Imgcodecs.imread("./aircraft.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        
        Mat sobelX = new Mat();
        Imgproc.Sobel(src, sobelX, CvType.CV_16S, 1, 0, 3, 1, 0, Core.BORDER_DEFAULT);

        Mat sobelY = new Mat();
        Imgproc.Sobel(src, sobelY, CvType.CV_16S, 0, 1, 3, 1, 0, Core.BORDER_DEFAULT);

        Mat absSobelX = new Mat();
        Mat absSobelY = new Mat();
        Core.convertScaleAbs(sobelX, absSobelX);
        Core.convertScaleAbs(sobelY, absSobelY);

        Mat sobelCombined = new Mat();
        Core.addWeighted(absSobelX, 0.5, absSobelY, 0.5, 0, sobelCombined);

        // Imgcodecs.imwrite("sobel_x.jpg", absSobelX);
        // Imgcodecs.imwrite("sobel_y.jpg", absSobelY);
        Imgcodecs.imwrite("sobel_edges_aircraft.jpg", sobelCombined);
        
        Mat tresholdSobel = new Mat();
        Imgproc.threshold(sobelCombined, tresholdSobel, 100, 255, Imgproc.THRESH_BINARY);
        Imgcodecs.imwrite("sobel_treshold_aircraft.jpg", tresholdSobel);
                
        return sobelCombined;
    }
    
    public static void CannySobelMergeEdgeExample() {
        Mat sobel = SobelEdgeExample();
        Mat canny = CannyEdgeExample();
        
        Mat combined = new Mat();
        Core.bitwise_and(sobel, canny, combined);
        Mat result = new Mat();
        Imgproc.threshold(combined, result, 1, 255, Imgproc.THRESH_BINARY);
        Imgcodecs.imwrite("canny_sobel_edges.jpg", result);
    }
    
    public static Mat LaplacianEdgeExample() {
        String imagePath = "london.jpg";
        Mat src = Imgcodecs.imread(imagePath, Imgcodecs.IMREAD_GRAYSCALE);

        Imgproc.GaussianBlur(src, src, new Size(3, 3), 0);

        Mat laplacian = new Mat();
        Imgproc.Laplacian(src, laplacian, CvType.CV_16S, 3, 1, 0, Core.BORDER_DEFAULT);

        Mat absLaplacian = new Mat();
        Core.convertScaleAbs(laplacian, absLaplacian);

        String outputPath = "laplacian_edges.jpg";
        Imgcodecs.imwrite(outputPath, absLaplacian);
        
        Mat tresholdLaplacian = new Mat();
        Imgproc.threshold(absLaplacian, tresholdLaplacian, 100, 255, Imgproc.THRESH_BINARY);
        Imgcodecs.imwrite("laplacian_treshold.jpg", tresholdLaplacian);
        
        System.out.println("Laplacian edge detection completed. Result saved as: " + outputPath);
        
        return absLaplacian;
    }
    
    public static void SIFTKeypointsExample() {
        Mat image = Imgcodecs.imread("./london.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        //Mat edges = new Mat();
        SIFT sift = SIFT.create();
        //Imgproc.Canny(image, edges, 100, 200);
        MatOfKeyPoint keypoints = new MatOfKeyPoint();
        sift.detect(image, keypoints);
        Mat outputImage = new Mat();
        Features2d.drawKeypoints(image, keypoints, outputImage);
        Imgcodecs.imwrite("./sift_keypoints.jpg", outputImage);
    }
    
    public static void SIFTEdgeExample() {
        Mat image = Imgcodecs.imread("aircraft.jpg", Imgcodecs.IMREAD_GRAYSCALE);

        Mat lowBlur = new Mat();
        Mat highBlur = new Mat();
        Imgproc.GaussianBlur(image, lowBlur, new Size(5, 5), 1.0);
        Imgproc.GaussianBlur(image, highBlur, new Size(5, 5), 4.0);

        Mat combinedImage = new Mat();
        Core.subtract(lowBlur, highBlur, combinedImage);

        Imgproc.threshold(combinedImage, combinedImage, 10, 255, Imgproc.THRESH_BINARY);
        
        combinedImage = AdjustBrightness(combinedImage);
        Imgcodecs.imwrite("substracted_blurs_lm.jpg", combinedImage);
        
        Imgproc.GaussianBlur(combinedImage, combinedImage, new Size(5,5), 2.0);
        
        // Mat combinedImage2 = new Mat();
        // Imgproc.GaussianBlur(combinedImage, combinedImage2, new Size(5,5), 2.0);
        Mat canny = CannyEdgeExample();
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
        
        Imgcodecs.imwrite("combined_with_canny.jpg", combinedWithCanny);
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

