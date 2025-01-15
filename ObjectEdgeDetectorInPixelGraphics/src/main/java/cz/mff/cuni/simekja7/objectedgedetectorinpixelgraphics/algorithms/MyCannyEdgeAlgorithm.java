/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.mff.cuni.simekja7.objectedgedetectorinpixelgraphics.algorithms;

import org.opencv.core.Mat;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Custom implementation of Canny edge algorithm.
 * @author simek.jan
 */
public class MyCannyEdgeAlgorithm extends EdgeAlgorithm {

    static int blur_kernel_size = 5;
    static double blur_sigma = 1.4;
    
    int low_threshold = 50;
    int high_threshold = 150;
    
    @Override
    public Mat run(String image_name) {
        // Step 1: Load the image
        BufferedImage image = loadImage(image_name);

        // Step 2: Convert to grayscale if necessary
        double[][] grayscaleImage = convertToGrayscale(image);

        // Step 3: Apply Gaussian smoothing
        double[][] smoothedImage = applyGaussianBlur(grayscaleImage);
        
        // Step 4: Compute gradient magnitude and direction
        GradientResult gradientResult = computeGradient(smoothedImage);

        // Step 5: Apply non-maximum suppression
        double[][] suppressedImage = nonMaximumSuppression(gradientResult.magnitude, gradientResult.direction);
        
        // Step 6: Apply double thresholding
        EdgeType[][] edgeMap = doubleThreshold(suppressedImage, low_threshold, high_threshold);

        // Step 7: Perform edge tracking by hysteresis
        EdgeType[][] finalEdges = edgeTrackingByHysteresis(edgeMap);

        // Step 8: Convert result to BufferedImage and save
        BufferedImage outputImage = toBufferedImage(finalEdges);
        saveImage(outputImage, "test.jpg");
        
        return new Mat();
    }

    /**
     * Load image from file to BufferedImage.
     * @param filePath Path to the image.
     * @return Processed image to use.
     */
    private static BufferedImage loadImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Convert image to matrix of gray-scale values.
     * @param coloredImage Image to process.
     * @return Image represented by double values in intensity.
     */
    private static double[][] convertToGrayscale(BufferedImage coloredImage) {
        int width = coloredImage.getWidth();
        int height = coloredImage.getHeight();

        double[][] grayscaleImage = new double[width][height];
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = coloredImage.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                int gray = (red + green + blue) / 3;
                grayscaleImage[x][y] = gray;
            }
        }
        return grayscaleImage;
    }

    /**
     * Apply Gaussian blur to image in double representation.
     * @param image The image to blur.
     * @return Blurred image.
     */
    private static double[][] applyGaussianBlur(double[][] image) {
        double[][] kernel = generateGaussianKernel(blur_kernel_size, blur_sigma);
        return convolve(image, kernel);
    }

    /**
     * Generate a Gaussian kernel of given size and sigma value.
     * @param size The size of kernel to generate.
     * @param sigma The sigma value of kernel to generate.
     * @return The kernel with given values.
     */
    private static double[][] generateGaussianKernel(int size, double sigma) {
        double[][] kernel = new double[size][size];
        double sum = 0.0;
        int halfSize = size / 2;

        for (int x = -halfSize; x <= halfSize; x++) {
            for (int y = -halfSize; y <= halfSize; y++) {
                double value = (1 / (2 * Math.PI * sigma * sigma)) *
                        Math.exp(-(x * x + y * y) / (2 * sigma * sigma));
                kernel[x + halfSize][y + halfSize] = value;
                sum += value;
            }
        }

        // Normalize kernel
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                kernel[i][j] /= sum;
            }
        }
        return kernel;
    }

    /**
     * Convolve an image with a kernel.
     * @param image The image to process.
     * @param kernel The kernel to use.
     * @return Blurred image.
     */
    private static double[][] convolve(double[][] image, double[][] kernel) {
        int width = image.length;
        int height = image[0].length;
        int kernelSize = kernel.length;
        int offset = kernelSize / 2;

        double[][] result = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double sum = 0.0;
                for (int i = -offset; i <= offset; i++) {
                    for (int j = -offset; j <= offset; j++) {
                        // ensure edges of picture are in boundaries
                        int x_value = x + i; 
                        int y_value = y + j;
                        if (x_value < 0) x_value = 0;
                        if (x_value > width-1) x_value = width-1;
                        if (y_value < 0) y_value = 0;
                        if (y_value > height-1) y_value = height-1;
                        // add the value
                        double value = image[x_value][y_value];
                        sum += value * kernel[i + offset][j + offset];
                    }
                }
                result[x][y] = sum;
            }
        }
        return result;
    }

    /**
     * Helper class to store gradient results
     */
    private static class GradientResult {
        double[][] magnitude;
        double[][] direction;

        GradientResult(double[][] magnitude, double[][] direction) {
            this.magnitude = magnitude;
            this.direction = direction;
        }
    }
    
    /**
     * Compute gradient magnitude and direction using Sobel operators.
     * @param image The image to make gradient on.
     * @return Gradient for the image given.
     */
    private static GradientResult computeGradient(double[][] image) {
        int[][] sobelX = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
        };

        int[][] sobelY = {
            {1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1}
        };

        int width = image.length;
        int height = image[0].length;

        double[][] gradientMagnitude = new double[width][height];
        double[][] gradientDirection = new double[width][height];

        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                double gx = 0, gy = 0;

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        gx += image[x + i][y + j] * sobelX[i + 1][j + 1];
                        gy += image[x + i][y + j] * sobelY[i + 1][j + 1];
                    }
                }

                gradientMagnitude[x][y] = Math.sqrt(gx * gx + gy * gy);
                gradientDirection[x][y] = Math.atan2(gy, gx);
            }
        }

        // ensure edges aren't empty
        fillEdgesWithCopiesOfInnerNeighbour(gradientMagnitude);
        fillEdgesWithCopiesOfInnerNeighbour(gradientDirection);

        return new GradientResult(gradientMagnitude, gradientDirection);
    }
    
    /**
     * Helper method to duplicate nearest pixel value to the edge.
     * Works only on one pixel wide layer on edge.
     * @param image Image to process.
     */
    private static void fillEdgesWithCopiesOfInnerNeighbour(double[][] image) {
        int width = image.length;
        int height = image[0].length;
        
        for (int x = 0; x < width; x++) {
            image[x][0] = image[x][1];
            image[x][height-1] = image[x][height-2];
        }
        for (int y = 0; y < height; y++) {
            image[0][y] = image[1][y];
            image[width-1][y] = image[width-2][y];
        }

    }

    /**
     * Non-maximum suppression to thin edges.
     * @param magnitude Gradient magnitude to use.
     * @param direction Gradient direction to use.
     * @return Image with created values.
     */
    private static double[][] nonMaximumSuppression(double[][] magnitude, double[][] direction) {
        int width = magnitude.length;
        int height = magnitude[0].length;
        double[][] suppressed = new double[width][height];

        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                double angle = direction[x][y];
                double magnitudeCurrent = magnitude[x][y];
                double magnitude1 = 0, magnitude2 = 0;

                // Normalize angle to [0, 180)
                angle = (angle < 0) ? angle + Math.PI : angle;
                angle = Math.toDegrees(angle) % 180;

                // Interpolate magnitudes along gradient direction
                if ((angle >= 0 && angle < 22.5) || (angle >= 157.5 && angle < 180)) {
                    magnitude1 = magnitude[x][y - 1]; // Left
                    magnitude2 = magnitude[x][y + 1]; // Right
                } else if (angle >= 22.5 && angle < 67.5) {
                    magnitude1 = magnitude[x - 1][y + 1]; // Bottom-left
                    magnitude2 = magnitude[x + 1][y - 1]; // Top-right
                } else if (angle >= 67.5 && angle < 112.5) {
                    magnitude1 = magnitude[x - 1][y]; // Top
                    magnitude2 = magnitude[x + 1][y]; // Bottom
                } else if (angle >= 112.5 && angle < 157.5) {
                    magnitude1 = magnitude[x - 1][y - 1]; // Top-left
                    magnitude2 = magnitude[x + 1][y + 1]; // Bottom-right
                }

                // Suppress non-maxima
                if (magnitudeCurrent >= magnitude1 && magnitudeCurrent >= magnitude2) {
                    suppressed[x][y] = magnitudeCurrent;
                } else {
                    suppressed[x][y] = 0;
                }
            }
        }
        
        // ensure edges aren't empty
        fillEdgesWithCopiesOfInnerNeighbour(suppressed);
        
        return suppressed;
    }
    
    /**
     * Helper enum for differentiated types of edges.
     */
    enum EdgeType {STRONG, WEAK, NONE}
    
    /**
     * Apply double thresholding to EdgeType matrix.
     * @param image Image to process.
     * @param lowThreshold Threshold for weak edges.
     * @param highThreshold Threshold for strong edges.
     * @return Processed matrix of EdgeTypes.
     */
    private static EdgeType[][] doubleThreshold(double[][] image, double lowThreshold, double highThreshold) {
        int width = image.length;
        int height = image[0].length;
        EdgeType[][] edgeMap = new EdgeType[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double pixel = image[x][y];

                if (pixel >= highThreshold) {
                    edgeMap[x][y] = EdgeType.STRONG;
                } else if (pixel >= lowThreshold) {
                    edgeMap[x][y] = EdgeType.WEAK;
                } else {
                    edgeMap[x][y] = EdgeType.NONE;
                }
            }
        }
        return edgeMap;
    }

    /**
     * Perform edge tracking by hysteresis.
     * Removes weak edges not close to strong edges, the rest is promoted to strong.
     * @param edgeMap Matrix of EdgeTypes to process.
     * @return Processed EdgeType matrix with only strong and none types.
     */
    private static EdgeType[][] edgeTrackingByHysteresis(EdgeType[][] edgeMap) {
    int width = edgeMap.length;
    int height = edgeMap[0].length;

    for (int x = 1; x < width - 1; x++) {
        for (int y = 1; y < height - 1; y++) {
            if (edgeMap[x][y] == EdgeType.WEAK) {
                // Check 8-connected neighborhood for a strong edge
                boolean connectedToStrongEdge = false;

                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (edgeMap[x + dx][y + dy] == EdgeType.STRONG) { // Strong edge
                            connectedToStrongEdge = true;
                            break;
                        }
                    }
                    if (connectedToStrongEdge) break;
                }

                // If connected to a strong edge, promote to strong; otherwise, suppress
                edgeMap[x][y] = connectedToStrongEdge ? EdgeType.STRONG : EdgeType.NONE;
            }
        }
    }

    return edgeMap;
}

    /**
     * Convert a binary edge map to a black and white binary image.
     * @param edges EdgeType matrix to process.
     * @return Black and white binary image. 
     */
    private static BufferedImage toBufferedImage(EdgeType[][] edges) {
        int width = edges.length;
        int height = edges[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int value = edges[x][y] == EdgeType.STRONG ? 255 : 0;
                int newPixel = (value << 16) | (value << 8) | value;
                image.setRGB(x, y, newPixel);
            }
        }

        return image;
    }

    // TODO: remove later
    private static void saveImage(BufferedImage image, String outputPath) {
        try {
            ImageIO.write(image, "jpg", new File(outputPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
