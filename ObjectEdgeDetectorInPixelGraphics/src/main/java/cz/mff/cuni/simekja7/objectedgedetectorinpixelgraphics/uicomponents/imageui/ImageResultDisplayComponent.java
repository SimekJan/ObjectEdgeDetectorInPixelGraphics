/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.imageui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Panel that displays output image.
 * @author simek.jan
 */
public class ImageResultDisplayComponent extends JPanel {

    private BufferedImage image;

    /**
     * Constructor that creates panel to show output image.
     */
    public ImageResultDisplayComponent() {
        setBackground(Color.LIGHT_GRAY);
    }

    /**
     * Sets the image to be displayed and repaints the panel.
     * @param image The image to display.
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }
    
    /**
     * Provides the output image.
     * @return Output image being displayed.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Method to redraw panel with the output image.
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            double aspectRatio = (double) imageWidth / imageHeight;

            int newWidth = panelWidth;
            int newHeight = (int) (panelWidth / aspectRatio);

            if (newHeight > panelHeight) {
                newHeight = panelHeight;
                newWidth = (int) (panelHeight * aspectRatio);
            }

            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newHeight) / 2;

            g.drawImage(image, x, y, newWidth, newHeight, this);
        }
    }
}