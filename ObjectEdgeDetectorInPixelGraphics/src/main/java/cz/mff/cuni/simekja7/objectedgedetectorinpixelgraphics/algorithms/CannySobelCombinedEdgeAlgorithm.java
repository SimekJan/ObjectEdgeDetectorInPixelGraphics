/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 * Combined CannyEdge and SobelEdge algorithms using bitwise and.
 * @author simek.jan
 */
public class CannySobelCombinedEdgeAlgorithm extends EdgeAlgorithm {

    @Override
    public Mat run(String image_name) {
        Mat canny = new CannyEdgeAlgorithm().run(image_name);
        Mat sobel = new SobelEdgeAlgorithm().run(image_name);
        
        Mat combined = new Mat();
        Core.bitwise_and(sobel, canny, combined);
        Mat result = new Mat();
        Imgproc.threshold(combined, result, 1, 255, Imgproc.THRESH_BINARY);
        
        return result;
    }
}
