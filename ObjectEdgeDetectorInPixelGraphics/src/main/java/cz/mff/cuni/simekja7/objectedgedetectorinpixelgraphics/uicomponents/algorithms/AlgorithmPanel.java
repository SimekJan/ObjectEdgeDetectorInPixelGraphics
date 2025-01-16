/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.EdgeAlgorithm;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author simek.jan
 */
public abstract class AlgorithmPanel extends JPanel {
    
    public abstract BufferedImage runAlgorithm(BufferedImage inputImage);
    
    public abstract EdgeAlgorithm getAlgorithm();
}
