/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.*;

import javax.swing.*;
import org.opencv.core.Core;

/**
 * Main method to enter the app from.
 * @author simek.jan
 */
public class ObjectEdgeDetectorInPixelGraphics {
    
    // Load OpenCV library
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
    /**
     * Main method that opens the app window.
     * @param args Arguments of the program (should be empty)
     */
    public static void main(String[] args) {        
        SwingUtilities.invokeLater(UIGenerator::createAndShowGUI);        
    }
}
