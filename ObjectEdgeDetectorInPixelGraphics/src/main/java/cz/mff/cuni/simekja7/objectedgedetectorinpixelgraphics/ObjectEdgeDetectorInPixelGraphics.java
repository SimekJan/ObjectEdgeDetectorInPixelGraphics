/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.*;
import org.opencv.core.Core;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Main method to enter the app from.
 * @author simek.jan
 */
public class ObjectEdgeDetectorInPixelGraphics {
    
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
    public static void main(String[] args) {        
        
        EdgeAlgorithm a = new SiftEdgeAlgorithm();
        
        Imgcodecs.imwrite("SIFT_test.jpg", a.run("london.jpg"));
    }
}
