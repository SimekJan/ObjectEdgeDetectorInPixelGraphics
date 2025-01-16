/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.EdgeAlgorithm;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Abstract class for all panels including parameters selection for algorithms.
 * @author simek.jan
 */
public abstract class AlgorithmPanel extends JPanel {
    
    /**
     * Runs corresponding algorithm on given Image.
     * @param inputImage
     * @return Image result of algorithm this is for.
     */
    public abstract BufferedImage runAlgorithm(BufferedImage inputImage);
   
    /**
     * Gets instance of algorithm this is for.
     * @return The algorithm this panel serves to operate.
     */
    public abstract EdgeAlgorithm getAlgorithm();
}
