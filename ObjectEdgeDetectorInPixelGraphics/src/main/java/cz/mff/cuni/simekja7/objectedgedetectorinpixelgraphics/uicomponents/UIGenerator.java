/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents;

import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.imageui.*;
import cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.algorithms.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author simek.jan
 */
public class UIGenerator {
    
    static ImageDisplayPanel displayPanel;
    static CannyPanel cannyPanel;
    static SobelPanel sobelPanel;
    static LaplacianPanel laplacianPanel;
    static SiftPanel siftPanel;
    static CustomCannyPanel customCannyPanel;
    static CombinePanel combinePanel;
    
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Object Edge Detector In Pixel Graphics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1,1));
        displayPanel = new ImageDisplayPanel();
        topPanel.add(displayPanel);
        frame.add(topPanel, BorderLayout.CENTER);
        
        // Create the tabbed pane
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Panel for CannyEedge library implementation
        cannyPanel = new CannyPanel();
        tabbedPane.addTab("Canny Edge", cannyPanel);

        // Tab 2: Panel for Sobel library implementation
        sobelPanel =  new SobelPanel();
        tabbedPane.addTab("Sobel", sobelPanel);

        // Tab 3: Panel for Laplacian library implementation
        laplacianPanel = new LaplacianPanel();
        tabbedPane.addTab("Laplacian", laplacianPanel);

        // Tab 4: Panel for what's left from SIFT attempt
        siftPanel = new SiftPanel();
        tabbedPane.addTab("SIFT", siftPanel);
        
        // Tab 5: Panel for custom Canny Edge implementation
        customCannyPanel = new CustomCannyPanel();
        tabbedPane.addTab("Custom CE", customCannyPanel);
        
        // Tab 6: Panel for mixing algorithms
        combinePanel = new CombinePanel();
        tabbedPane.addTab("Combine Alg", combinePanel);
        
        JButton runButton = new JButton("Run");
        runButton.addActionListener(e -> {
            AlgorithmPanel selectedPanel = (AlgorithmPanel) tabbedPane.getSelectedComponent();
            BufferedImage input = displayPanel.getInputImage();
            BufferedImage result = selectedPanel.runAlgorithm(input);
            displayPanel.setOutputImage(result);
        });
        
        tablePanel.add(tabbedPane, BorderLayout.CENTER);
        tablePanel.add(runButton, BorderLayout.SOUTH);
        
        // Add the tabbed pane to the frame
        frame.add(tablePanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
