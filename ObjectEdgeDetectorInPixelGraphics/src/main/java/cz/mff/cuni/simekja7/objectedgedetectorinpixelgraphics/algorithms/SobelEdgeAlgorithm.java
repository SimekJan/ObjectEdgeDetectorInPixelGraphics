/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author simek.jan
 */
public class SobelEdgeAlgorithm extends EdgeAlgorithm {

    static int ksize = 3;
    
    @Override
    public Mat run(String image_name) {
        Mat src = Imgcodecs.imread(image_name, Imgcodecs.IMREAD_GRAYSCALE);
        
        Mat sobelX = new Mat();
        Imgproc.Sobel(src, sobelX, CvType.CV_16S, 1, 0, ksize, 1, 0, Core.BORDER_DEFAULT);

        Mat sobelY = new Mat();
        Imgproc.Sobel(src, sobelY, CvType.CV_16S, 0, 1, ksize, 1, 0, Core.BORDER_DEFAULT);

        Mat absSobelX = new Mat();
        Mat absSobelY = new Mat();
        Core.convertScaleAbs(sobelX, absSobelX);
        Core.convertScaleAbs(sobelY, absSobelY);

        Mat sobelCombined = new Mat();
        Core.addWeighted(absSobelX, 0.5, absSobelY, 0.5, 0, sobelCombined);
                        
        return sobelCombined;
    }
}
