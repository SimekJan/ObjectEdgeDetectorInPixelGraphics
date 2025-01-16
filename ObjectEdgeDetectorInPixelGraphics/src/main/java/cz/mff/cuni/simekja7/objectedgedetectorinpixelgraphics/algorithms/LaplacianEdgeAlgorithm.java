/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.helpers.MatBufferedImageConvertor;
import java.awt.image.BufferedImage;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * LaplacianEdge algorithm using OpenCV methods.
 * @author simek.jan
 */
public class LaplacianEdgeAlgorithm extends EdgeAlgorithm {

    public static int blur_size = 3;
    public static int ksize = 3;
    
    /**
     * Runs Laplacian algorithm on given image.
     * @param inputImage
     * @return Laplacian output image.
     */
    @Override
    public BufferedImage run(BufferedImage inputImage) {
        Mat src = MatBufferedImageConvertor.bufferedImageToMat(inputImage);

        Imgproc.GaussianBlur(src, src, new Size(blur_size, blur_size), 0);

        Mat laplacian = new Mat();
        Imgproc.Laplacian(src, laplacian, CvType.CV_16S, ksize, 1, 0, Core.BORDER_DEFAULT);

        Mat absLaplacian = new Mat();
        Core.convertScaleAbs(laplacian, absLaplacian);
        
        BufferedImage toReturn = MatBufferedImageConvertor.matToBufferedImage(absLaplacian);
        
        return toReturn;
    }
    
}
