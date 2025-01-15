/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author simek.jan
 */
public class SobelPanel extends JPanel {
    public SobelPanel(boolean includeRunButton) {
        setLayout(new BorderLayout());
        JPanel pt = new JPanel();
        add(pt, BorderLayout.NORTH);
        pt.add(new JLabel("Size of kernel:"));
        
        JPanel pc = new JPanel();
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"3x3", "5x5", "7x7", "9x9"});
        add(pc, BorderLayout.CENTER);
        pc.add(comboBox);
        
        if (includeRunButton) {
            JButton runButton = new JButton("Run");
            add(runButton, BorderLayout.SOUTH);
        }
    }
}
