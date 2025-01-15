/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms;

import java.awt.image.BufferedImage;

/**
 * Abstract class for all edge-detecting algorithms.
 * @author simek.jan
 */
public abstract class EdgeAlgorithm {
    /**
     * Runs concrete algorithm.
     * @param image_name Path to image to run the algorithm on.
     * @return Mat with the edges.
     */
    public abstract BufferedImage run(String image_name);
}
