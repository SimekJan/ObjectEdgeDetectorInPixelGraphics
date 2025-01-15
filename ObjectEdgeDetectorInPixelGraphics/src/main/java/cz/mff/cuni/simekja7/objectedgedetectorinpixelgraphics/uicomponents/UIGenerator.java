/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;

/**
 *
 * @author simek.jan
 */
public class UIGenerator {
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Object Edge Detector In Pixel Graphics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel topPanel = new JPanel();
        topPanel.add(new ImageLoaderComponent());
        frame.add(topPanel, BorderLayout.CENTER);
        
        // Create the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Panel for CannyEedge library implementation
        tabbedPane.addTab("Canny Edge", new CannyPanel());

        // Tab 2: Panel for Sobel library implementation
        tabbedPane.addTab("Sobel", new SobelPanel());

        // Tab 3: Panel for Laplacian library implementation
        tabbedPane.addTab("Laplacian", new LaplacianPanel());

        // Tab 4: Panel for what's left from SIFT attempt
        tabbedPane.addTab("SIFT", new SiftPanel());
        
        // Tab 5: Panel for custom Canny Edge implementation
        tabbedPane.addTab("Custom CE", new CustomCannyPanel());
        
        // Tab 5: Panel for mixing algorithms
        tabbedPane.addTab("Combine Alg", new CombinePanel());
        
        // Add the tabbed pane to the frame
        frame.add(tabbedPane, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
