/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
public class SiftPanel extends JPanel {
    public SiftPanel() {
        setLayout(new BorderLayout());
        
        JPanel pt = new JPanel();
        add(pt, BorderLayout.NORTH);
        
        
        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        JLabel l1 = new JLabel("Sigma 1:");
        l1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p1.add(l1, BorderLayout.NORTH);
        JComboBox<Double> comboBox1 = new JComboBox<>(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0});
        comboBox1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p1.add(comboBox1, BorderLayout.CENTER);
        
        
        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        JLabel l2 = new JLabel("Sigma 2:");
        l2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p2.add(l2, BorderLayout.NORTH);
        JComboBox<Double> comboBox2 = new JComboBox<>(new Double[]{2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0});
        comboBox2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p2.add(comboBox2, BorderLayout.CENTER);

        
        JPanel p3 = new JPanel();
        p3.setLayout(new BorderLayout());
        JLabel l3 = new JLabel("     Threshold:   40");
        l3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p3.add(l3, BorderLayout.NORTH);
        JSlider s = new JSlider(10, 100, 40);
        s.setMajorTickSpacing(20);
        s.setMinorTickSpacing(10);
        s.setPaintTicks(true);
        s.setPaintLabels(true);
        s.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                l3.setText(String.valueOf("     Threshold:   " + s.getValue())); 
            }
        });
        s.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p3.add(s, BorderLayout.CENTER);
        
        
        pt.add(p1);
        pt.add(p2);
        pt.add(p3);
        
        
        JButton runButton = new JButton("Run");
        add(runButton, BorderLayout.SOUTH);
    }
}
