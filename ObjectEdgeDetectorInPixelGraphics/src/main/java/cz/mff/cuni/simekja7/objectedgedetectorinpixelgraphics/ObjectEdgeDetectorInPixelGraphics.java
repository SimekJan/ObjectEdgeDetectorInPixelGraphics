/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics;

import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.core.MatOfKeyPoint;
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
        CannyEdgeExample();
        SIFTKeypointsExample();
    }
    
    public static void CannyEdgeExample() {
        Mat src = Imgcodecs.imread("./london.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat edges = new Mat();
        Imgproc.Canny(src, edges, 100, 200);
        Imgcodecs.imwrite("./edges_canny.jpg", edges);
    }
    
    public static void SIFTKeypointsExample() {
        Mat image = Imgcodecs.imread("./london.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat edges = new Mat();
        SIFT sift = SIFT.create();
        Imgproc.Canny(image, edges, 100, 200);
        MatOfKeyPoint keypoints = new MatOfKeyPoint();
        sift.detect(edges, keypoints);
        Mat outputImage = new Mat();
        Features2d.drawKeypoints(image, keypoints, outputImage);
        Imgcodecs.imwrite("./sift_keypoints.jpg", outputImage);
    }
}

