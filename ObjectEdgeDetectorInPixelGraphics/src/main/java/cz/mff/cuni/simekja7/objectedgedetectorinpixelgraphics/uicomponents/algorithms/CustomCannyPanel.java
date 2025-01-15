/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.MyCannyEdgeAlgorithm;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.SiftEdgeAlgorithm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author simek.jan
 */
public class CustomCannyPanel extends AlgorithmPanel {
    public CustomCannyPanel() {
        setLayout(new BorderLayout());
        
        JPanel pt = new JPanel();
        add(pt, BorderLayout.NORTH);
        
        
        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        JLabel l1 = new JLabel("Blur kernel size:");
        l1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p1.add(l1, BorderLayout.NORTH);
        JComboBox<String> comboBox1 = new JComboBox<>(new String[]{"3x3", "5x5", "7x7"});
        comboBox1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyCannyEdgeAlgorithm.blur_kernel_size = Integer.parseInt(((String)comboBox1.getSelectedItem()).substring(0, 1));
            }
        });
        p1.add(comboBox1, BorderLayout.CENTER);
        
        
        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        JLabel l2 = new JLabel("Blur sigma:");
        l2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p2.add(l2, BorderLayout.NORTH);
        JComboBox<Double> comboBox2 = new JComboBox<>(new Double[]{1.0, 1.2, 1.4, 1.6, 1.8, 2.0});
        comboBox2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyCannyEdgeAlgorithm.blur_sigma = (Double)comboBox2.getSelectedItem();
            }
        });
        p2.add(comboBox2, BorderLayout.CENTER);

        
        JPanel p3 = new JPanel();
        p3.setLayout(new BorderLayout());
        JLabel l3 = new JLabel("     Threshold 1:   50");
        l3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p3.add(l3, BorderLayout.NORTH);
        JSlider s1 = new JSlider(0, 500, 50);
        s1.setMajorTickSpacing(100);
        s1.setMinorTickSpacing(50);
        s1.setPaintTicks(true);
        s1.setPaintLabels(true);
        s1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                l3.setText(String.valueOf("     Threshold 1:   " + s1.getValue()));
                MyCannyEdgeAlgorithm.low_threshold = s1.getValue();
            }
        });
        s1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p3.add(s1, BorderLayout.CENTER);
        
        
        JPanel p4 = new JPanel();
        p4.setLayout(new BorderLayout());
        JLabel l4 = new JLabel("     Threshold 2:   150");
        l4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p4.add(l4, BorderLayout.NORTH);
        JSlider s2 = new JSlider(0, 500, 150);
        s2.setMajorTickSpacing(100);
        s2.setMinorTickSpacing(50);
        s2.setPaintTicks(true);
        s2.setPaintLabels(true);
        s2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                l3.setText(String.valueOf("     Threshold 2:   " + s2.getValue())); 
                MyCannyEdgeAlgorithm.high_threshold = s2.getValue();
            }
        });
        s2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p4.add(s2, BorderLayout.CENTER);
        
        
        pt.add(p1);
        pt.add(p2);
        pt.add(p3);
        pt.add(p4);
    }

    @Override
    public BufferedImage runAlgorithm(BufferedImage inputImage) {
        return new MyCannyEdgeAlgorithm().run(inputImage);
    }
}
