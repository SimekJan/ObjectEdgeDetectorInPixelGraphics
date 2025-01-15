/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.SobelEdgeAlgorithm;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author simek.jan
 */
public class SobelPanel extends AlgorithmPanel {
    public SobelPanel() {
        setLayout(new BorderLayout());
        JPanel pt = new JPanel();
        add(pt, BorderLayout.NORTH);
        pt.add(new JLabel("Size of kernel:"));
        
        JPanel pc = new JPanel();
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"3x3", "5x5", "7x7", "9x9"});
        add(pc, BorderLayout.CENTER);
        pc.add(comboBox);
    }

    @Override
    public BufferedImage runAlgorithm(BufferedImage inputImage) {
        return new SobelEdgeAlgorithm().run(inputImage);
    }
}
