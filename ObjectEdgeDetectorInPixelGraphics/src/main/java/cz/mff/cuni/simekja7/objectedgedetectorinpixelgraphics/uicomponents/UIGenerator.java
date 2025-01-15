/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.imageui.ImageLoaderComponent;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms.LaplacianPanel;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms.CombinePanel;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms.CannyPanel;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms.CustomCannyPanel;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms.SiftPanel;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms.SobelPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
        tabbedPane.addTab("Canny Edge", new CannyPanel(true));

        // Tab 2: Panel for Sobel library implementation
        tabbedPane.addTab("Sobel", new SobelPanel(true));

        // Tab 3: Panel for Laplacian library implementation
        tabbedPane.addTab("Laplacian", new LaplacianPanel(true));

        // Tab 4: Panel for what's left from SIFT attempt
        tabbedPane.addTab("SIFT", new SiftPanel(true));
        
        // Tab 5: Panel for custom Canny Edge implementation
        tabbedPane.addTab("Custom CE", new CustomCannyPanel(true));
        
        // Tab 5: Panel for mixing algorithms
        tabbedPane.addTab("Combine Alg", new CombinePanel());
        
        // Add the tabbed pane to the frame
        frame.add(tabbedPane, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
