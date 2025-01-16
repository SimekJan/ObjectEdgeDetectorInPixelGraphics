/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.CannyEdgeAlgorithm;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.EdgeAlgorithm;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author simek.jan
 */
public class CannyPanel extends AlgorithmPanel{
    public CannyPanel() {
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());  

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));  
        JLabel label1 = new JLabel("Threshold 1:   200");
        JSlider s1 = new JSlider(50, 550, 200);
        s1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        s1.setMajorTickSpacing(100);
        s1.setMinorTickSpacing(50);
        s1.setPaintTicks(true);
        s1.setPaintLabels(true);
        s1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                label1.setText(String.valueOf("Threshold 1:   " + s1.getValue()));
                CannyEdgeAlgorithm.treshold1 = s1.getValue();
            }
        });
        leftPanel.add(label1);  
        leftPanel.add(s1);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JLabel label2 = new JLabel("Threshold 2:   200");
        JSlider s2 = new JSlider(50, 550, 200);
        s2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        s2.setMajorTickSpacing(100);
        s2.setMinorTickSpacing(50);
        s2.setPaintTicks(true);
        s2.setPaintLabels(true);
        s2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                label2.setText(String.valueOf("Threshold 2:   " + s2.getValue()));
                CannyEdgeAlgorithm.treshold2 = s2.getValue();
            }
        });
        rightPanel.add(label2);    
        rightPanel.add(s2); 
        
        topPanel.add(leftPanel);
        topPanel.add(rightPanel);
 
        add(topPanel, BorderLayout.CENTER);
    }

    @Override
    public BufferedImage runAlgorithm(BufferedImage inputImage) {
        return new CannyEdgeAlgorithm().run(inputImage);
    }

    @Override
    public EdgeAlgorithm getAlgorithm() {
        return new CannyEdgeAlgorithm();
    }
}
