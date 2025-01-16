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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Panel encapsulating top part of display where images are shown.
 * @author simek.jan
 */
public class ImageDisplayPanel extends JPanel {
    
    private ImageLoaderComponent imageLoader;
    private ImageResultDisplayComponent resultImagePanel;
    
    /**
     * Constructor that creates panel display input and output.
     */
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
        loadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("File Chooser Window");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 200);
                
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select a File to Load");

                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        imageLoader.loadImage(selectedFile);
                    }
                    catch (IOException ex) {}
                }
            }
        });
        loadPanel.add(loadImageButton, BorderLayout.SOUTH);
        middlePanel.add(loadPanel);
        
        JLabel arrowLabel = new JLabel("=>", SwingConstants.CENTER);
        arrowLabel.setFont(new Font("Arial", Font.BOLD, 72));
        middlePanel.add(arrowLabel);
        
        JPanel savePanel = new JPanel();
        savePanel.setLayout(new BorderLayout());
        JButton saveImageButton = new JButton("Save generated image");
        saveImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("File Chooser Window");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 200);
                
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Image As");

                int userSelection = fileChooser.showSaveDialog(frame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();

                    if (!fileToSave.getName().toLowerCase().endsWith(".jpg")) {
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".jpg");
                    }

                    try {
                        ImageIO.write(resultImagePanel.getImage(), "jpg", fileToSave);
                    } 
                    catch (IOException ex) {}
                }
            }
        });
        savePanel.add(saveImageButton, BorderLayout.NORTH);
        middlePanel.add(savePanel);

        gbc.gridx = 2; 
        gbc.weightx = 1.0;
        gbc.insets = new Insets(40, 0, 20, 40);
        resultImagePanel = new ImageResultDisplayComponent();
        add(resultImagePanel, gbc);
    }
    
    /**
     * Returns image provided by user to input field.
     * @return The input image.
     */
    public BufferedImage getInputImage() {
        return imageLoader.getImage();
    }
    
    /**
     * Place given image to the output field and display it.
     * @param image Image to display in output field.
     */
    public void setOutputImage(BufferedImage image) {
        resultImagePanel.setImage(image);
    }
}
