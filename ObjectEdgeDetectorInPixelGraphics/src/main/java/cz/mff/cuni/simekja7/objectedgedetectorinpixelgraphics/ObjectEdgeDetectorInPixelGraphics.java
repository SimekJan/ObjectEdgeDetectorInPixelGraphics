/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics;

import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author simek.jan
 */
public class ObjectEdgeDetectorInPixelGraphics {
    
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
    public static void main(String[] args) {        
        CannyEdgeExample(args);
    }
    
    public static void CannyEdgeExample(String[] args) {
        Mat src = Imgcodecs.imread("./london.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat edges = new Mat();
        Imgproc.Canny(src, edges, 200, 400);
        Imgcodecs.imwrite("./edges.jpg", edges);
    }
}

