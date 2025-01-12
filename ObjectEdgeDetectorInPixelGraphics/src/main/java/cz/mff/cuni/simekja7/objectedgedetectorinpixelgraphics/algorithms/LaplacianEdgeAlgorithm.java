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
 * LaplacianEdge algorithm using OpenCV methods.
 * @author simek.jan
 */
public class LaplacianEdgeAlgorithm extends EdgeAlgorithm {

    @Override
    public Mat run(String image_name) {
        Mat src = Imgcodecs.imread(image_name, Imgcodecs.IMREAD_GRAYSCALE);

        Imgproc.GaussianBlur(src, src, new Size(3, 3), 0);

        Mat laplacian = new Mat();
        Imgproc.Laplacian(src, laplacian, CvType.CV_16S, 3, 1, 0, Core.BORDER_DEFAULT);

        Mat absLaplacian = new Mat();
        Core.convertScaleAbs(laplacian, absLaplacian);
        
        return absLaplacian;
    }
    
}
