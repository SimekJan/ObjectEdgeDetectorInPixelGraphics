/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.EdgeAlgorithm;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.SobelEdgeAlgorithm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel class for encapsulating Sobel algorithm.
 * Displays components to change parameters of the algorithm.
 * @author simek.jan
 */
public class SobelPanel extends AlgorithmPanel {
    
    /**
     * Constructor that creates panel to set up Sobel.
     */
    public SobelPanel() {
        setLayout(new BorderLayout());
        JPanel pt = new JPanel();
        add(pt, BorderLayout.NORTH);
        pt.add(new JLabel("Size of kernel:"));
        
        JPanel pc = new JPanel();
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"3x3", "5x5", "7x7", "9x9"});
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SobelEdgeAlgorithm.ksize = Integer.parseInt(((String)comboBox.getSelectedItem()).substring(0, 1));
            }
        });
        add(pc, BorderLayout.CENTER);
        pc.add(comboBox);
    }

    /**
     * Runs Sobel algorithm for given image.
     * @param inputImage
     * @return Result of Sobel algorithm in image.
     */
    @Override
    public BufferedImage runAlgorithm(BufferedImage inputImage) {
        return new SobelEdgeAlgorithm().run(inputImage);
    }

    /**
     * Returns a new instance of Sobel algorithm.
     * @return New Sobel algorithm.
     */ 
    @Override
    public EdgeAlgorithm getAlgorithm() {
        return new SobelEdgeAlgorithm();
    }
}
