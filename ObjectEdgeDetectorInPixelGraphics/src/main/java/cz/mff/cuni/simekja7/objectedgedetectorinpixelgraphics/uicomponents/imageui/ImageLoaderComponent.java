/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents.imageui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panel that allows drag-and-drop for loading images and displays them.
 * @author simek.jan
 */
public class ImageLoaderComponent extends JPanel {

    private BufferedImage image;

    /**
     * Constructor that creates panel to load and display image.
     */
    public ImageLoaderComponent() {
        setBackground(Color.LIGHT_GRAY);

        // Enable drag-and-drop functionality
        new DropTarget(this, new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {}

            @Override
            public void dragOver(DropTargetDragEvent dtde) {}

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {}

            @Override
            public void dragExit(DropTargetEvent dte) {}

            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable transferable = dtde.getTransferable();
                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        java.util.List<File> files = (java.util.List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                        if (!files.isEmpty()) {
                            File file = files.get(0);
                            loadImage(file);
                        }
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Loads the image from the given file and repaints the panel.
     * @param file The image file to load.
     * @throws IOException If an error occurs while reading the file.
     */
    public void loadImage(File file) throws IOException {
        image = ImageIO.read(file);
        repaint();
    }
    
    /**
     * Provides the image being shown in input field.
     * @return The input image.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Method to redraw panel with the input image.
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