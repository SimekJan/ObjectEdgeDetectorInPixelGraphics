/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.helpers;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.CannyEdgeAlgorithm;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.EdgeAlgorithm;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.SobelEdgeAlgorithm;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author simek.jan
 */
public class AlgorithmCombiner extends EdgeAlgorithm {

    public EdgeAlgorithm alg1 = new CannyEdgeAlgorithm();
    public EdgeAlgorithm alg2 = new SobelEdgeAlgorithm();
    public Boolean useAnd = true;
    public int threshold = 1;
    
    @Override
    public BufferedImage run(String image_name) {
        BufferedImage alg1Result = alg1.run(image_name);
        BufferedImage alg2Result = alg2.run(image_name);
        
        Mat alg1Mat = bufferedImageToMat(alg1Result);
        Mat alg2Mat = bufferedImageToMat(alg2Result);
        
        Mat combined = new Mat();
        if(useAnd) {
            Core.bitwise_and(alg1Mat, alg2Mat, combined);
        }
        else { // useOr
            Core.bitwise_and(alg1Mat, alg2Mat, combined);
        }
            
        Mat resultMat = new Mat();
        Imgproc.threshold(combined, resultMat, threshold, 255, Imgproc.THRESH_BINARY);
        
        BufferedImage resultImage = matToBufferedImage(resultMat);
        
        return resultImage;
    }

    private Mat bufferedImageToMat(BufferedImage image) {
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }

    private BufferedImage matToBufferedImage(Mat mat) {
        int width = mat.width();
        int height = mat.height();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        byte[] data = new byte[width * height * (int) mat.elemSize()];
        mat.get(0, 0, data);
        image.getRaster().setDataElements(0, 0, width, height, data);
        return image;
    }
}
