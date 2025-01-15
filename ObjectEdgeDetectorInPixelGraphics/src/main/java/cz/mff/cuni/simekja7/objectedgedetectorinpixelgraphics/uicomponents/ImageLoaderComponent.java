package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.uicomponents;

import java.awt.Color;
import java.awt.FlowLayout;
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
 * A custom JPanel that allows drag-and-drop for loading images and displays them.
 */
public class ImageLoaderComponent extends JPanel {

    private BufferedImage image; // Store the loaded image

    public ImageLoaderComponent() {
        // Set the background color of the panel
        setBackground(Color.LIGHT_GRAY);
        setSize(200,200);
        setLayout(new FlowLayout());

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
     * 
     * @param file The image file to load.
     * @throws IOException If an error occurs while reading the file.
     */
    private void loadImage(File file) throws IOException {
        image = ImageIO.read(file);
        repaint(); // Refresh the panel to display the new image
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // Draw the image, scaling it to fit the panel
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}