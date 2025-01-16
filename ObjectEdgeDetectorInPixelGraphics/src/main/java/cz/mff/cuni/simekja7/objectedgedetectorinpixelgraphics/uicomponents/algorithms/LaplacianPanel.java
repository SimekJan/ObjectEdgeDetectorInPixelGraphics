/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.EdgeAlgorithm;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms.LaplacianEdgeAlgorithm;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author simek.jan
 */
public class LaplacianPanel extends AlgorithmPanel {
    public LaplacianPanel() {
        setLayout(new BorderLayout());
        JPanel pt = new JPanel();
        add(pt, BorderLayout.NORTH);
        JLabel l1 = new JLabel("Size of blur area:");
        l1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        pt.add(l1);
        JLabel l2 = new JLabel("Size of kernel:");
        l2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        pt.add(l2);
        
        JPanel pc = new JPanel();
        JComboBox<String> comboBox1 = new JComboBox<>(new String[]{"3x3", "5x5", "7x7"});
        comboBox1.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 15));
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LaplacianEdgeAlgorithm.blur_size = Integer.parseInt(((String)comboBox1.getSelectedItem()).substring(0, 1));
            }
        });
        add(pc, BorderLayout.CENTER);
        pc.add(comboBox1);
        
        JComboBox<String> comboBox2 = new JComboBox<>(new String[]{"3x3", "5x5", "7x7", "9x9"});
        comboBox2.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 25));
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LaplacianEdgeAlgorithm.ksize = Integer.parseInt(((String)comboBox2.getSelectedItem()).substring(0, 1));
            }
        });
        add(pc, BorderLayout.CENTER);
        pc.add(comboBox2);
    }

    @Override
    public BufferedImage runAlgorithm(BufferedImage inputImage) {
        return new LaplacianEdgeAlgorithm().run(inputImage);
    }

    @Override
    public EdgeAlgorithm getAlgorithm() {
        return new LaplacianEdgeAlgorithm();
    }
}
