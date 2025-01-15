/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.imageui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author simek.jan
 */
public class ImageDisplayPanel extends JPanel {
    
    private ImageLoaderComponent imageLoader;
    private ImageResultDisplayComponent resultImagePanel;
    
    public ImageDisplayPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(40, 40, 20, 0);
        imageLoader = new ImageLoaderComponent();
        add(imageLoader, gbc);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(3, 1));
        gbc.gridx = 1;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(40, 40, 20, 40);
        add(middlePanel, gbc);
        
        JPanel loadPanel = new JPanel();
        loadPanel.setLayout(new BorderLayout());
        JButton loadImageButton = new JButton("Load image manually");
        loadPanel.add(loadImageButton, BorderLayout.SOUTH);
        middlePanel.add(loadPanel);
        
        JLabel arrowLabel = new JLabel("=>", SwingConstants.CENTER);
        arrowLabel.setFont(new Font("Arial", Font.BOLD, 72));
        middlePanel.add(arrowLabel);
        
        JPanel savePanel = new JPanel();
        savePanel.setLayout(new BorderLayout());
        JButton saveImageButton = new JButton("Save generated image");
        savePanel.add(saveImageButton, BorderLayout.NORTH);
        middlePanel.add(savePanel);

        gbc.gridx = 2; 
        gbc.weightx = 1.0;
        gbc.insets = new Insets(40, 0, 20, 40);
        resultImagePanel = new ImageResultDisplayComponent();
        add(resultImagePanel, gbc);
    }
    
    public BufferedImage getInputImage() {
        return imageLoader.getImage();
    }
    
    public void setOutputImage(BufferedImage image) {
        resultImagePanel.setImage(image);
    }
}
