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
 *
 * @author simek.jan
 */
public class MyCannyEdgeAlgorithm extends EdgeAlgorithm {

    @Override
    public Mat run(String image_name) {
        // Step 1: Load the image
        BufferedImage image = loadImage(image_name);
        // TODO: make better
        /*if (image == null) {
            System.out.println("Error loading image.");
            return;
        }*/

        // Step 2: Convert to grayscale if necessary
        BufferedImage grayscaleImage = convertToGrayscale(image);

        // Step 3: Apply Gaussian smoothing
        double[][] smoothedImage = applyGaussianBlur(grayscaleImage);
        
        // Step 4: Compute gradient magnitude and direction
        GradientResult gradientResult = computeGradient(smoothedImage);

        // Step 5: Apply non-maximum suppression
        double[][] suppressedImage = nonMaximumSuppression(gradientResult.magnitude, gradientResult.direction);

        printDoubleArray(suppressedImage);
        
        // Step 6: Apply double thresholding
        int[][] edgeMap = doubleThreshold(suppressedImage, 50, 150);

        // Step 7: Perform edge tracking by hysteresis
        int[][] finalEdges = edgeTrackingByHysteresis(edgeMap);

        // Step 8: Convert result to BufferedImage and save
        BufferedImage outputImage = toBufferedImage(finalEdges);
        saveImage(outputImage, "test.jpg");

        System.out.println("Canny edge detection completed. Output saved to: " + "test.jpg");
        
        return new Mat();
    }
    
    // TODO: only for debuging, remove later
    public static void printDoubleArray(double[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.printf("%.2f ", array[i][j]); // Print each element with 2 decimal places
            }
            System.out.println(); // Move to the next line after each row
        }
    }

    // Load the image from file
    public static BufferedImage loadImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Convert an image to grayscale
    public static BufferedImage convertToGrayscale(BufferedImage coloredImage) {
        int width = coloredImage.getWidth();
        int height = coloredImage.getHeight();
        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = coloredImage.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                int gray = (red + green + blue) / 3;
                int newPixel = (gray << 16) | (gray << 8) | gray;
                grayscaleImage.setRGB(x, y, newPixel);
            }
        }
        return grayscaleImage;
    }

    // Apply Gaussian blur to smooth the image
    public static double[][] applyGaussianBlur(BufferedImage image) {
        double[][] kernel = generateGaussianKernel(5, 1.4); // Size 5x5, sigma 1.4
        return convolve(image, kernel);
    }

    // Generate a Gaussian kernel
    public static double[][] generateGaussianKernel(int size, double sigma) {
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

    // Convolve an image with a kernel
    public static double[][] convolve(BufferedImage image, double[][] kernel) {
        int width = image.getWidth();
        int height = image.getHeight();
        int kernelSize = kernel.length;
        int offset = kernelSize / 2;

        double[][] result = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double sum = 0.0;
                for (int i = -offset; i <= offset; i++) {
                    for (int j = -offset; j <= offset; j++) {
                        
                        int x_value = x + i; 
                        int y_value = y + j;
                        
                        if (x_value < 0) x_value = 0;
                        if (x_value > width-1) x_value = width-1;
                        if (y_value < 0) y_value = 0;
                        if (y_value > height-1) y_value = height-1;
                        
                        int pixel = new java.awt.Color(image.getRGB(x_value, y_value)).getRed();
                        sum += pixel * kernel[i + offset][j + offset];
                    }
                }
                result[x][y] = sum;
            }
        }
/*
        for (int x = offset; x < width - offset; x++) {
            for (int y = offset; y < height - offset; y++) {
                double sum = 0.0;
                for (int i = -offset; i <= offset; i++) {
                    for (int j = -offset; j <= offset; j++) {
                        int pixel = new java.awt.Color(image.getRGB(x + i, y + j)).getRed();
                        sum += pixel * kernel[i + offset][j + offset];
                    }
                }
                result[x][y] = sum;
            }
        }
*/
        return result;
    }

    // Compute gradient magnitude and direction using Sobel operators
    public static GradientResult computeGradient(double[][] image) {
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
        for (int x = 0; x < width; x++) {
            gradientMagnitude[x][0] = gradientMagnitude[x][1];
            gradientDirection[x][0] = gradientDirection[x][1];
            
            gradientMagnitude[x][height-1] = gradientMagnitude[x][height-2];
            gradientDirection[x][height-1] = gradientDirection[x][height-2];
        }
        for (int y = 0; y < height; y++) {
            gradientMagnitude[0][y] = gradientMagnitude[1][y];
            gradientDirection[0][y] = gradientDirection[1][y];
            
            gradientMagnitude[width-1][y] = gradientMagnitude[width-2][y];
            gradientDirection[width-1][y] = gradientDirection[width-2][y];
        }

        return new GradientResult(gradientMagnitude, gradientDirection);
    }

    // Non-maximum suppression to thin edges
    public static double[][] nonMaximumSuppression(double[][] magnitude, double[][] direction) {
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
        return suppressed;
    }

    // Apply double thresholding
    public static int[][] doubleThreshold(double[][] image, double lowThreshold, double highThreshold) {
        int width = image.length;
        int height = image[0].length;
        int[][] edgeMap = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double pixel = image[x][y];

                if (pixel >= highThreshold) {
                    edgeMap[x][y] = 2; // Strong edge
                } else if (pixel >= lowThreshold) {
                    edgeMap[x][y] = 1; // Weak edge
                } else {
                    edgeMap[x][y] = 0; // Non-edge
                }
            }
        }
        return edgeMap;
    }

    // Perform edge tracking by hysteresis
    public static int[][] edgeTrackingByHysteresis(int[][] edgeMap) {
    int width = edgeMap.length;
    int height = edgeMap[0].length;

    for (int x = 1; x < width - 1; x++) {
        for (int y = 1; y < height - 1; y++) {
            if (edgeMap[x][y] == 1) { // Weak edge
                // Check 8-connected neighborhood for a strong edge
                boolean connectedToStrongEdge = false;

                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (edgeMap[x + dx][y + dy] == 2) { // Strong edge
                            connectedToStrongEdge = true;
                            break;
                        }
                    }
                    if (connectedToStrongEdge) break;
                }

                // If connected to a strong edge, promote to strong; otherwise, suppress
                edgeMap[x][y] = connectedToStrongEdge ? 2 : 0;
            }
        }
    }

    // Convert edge map to binary (0 = no edge, 255 = edge)
    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            edgeMap[x][y] = (edgeMap[x][y] == 2) ? 255 : 0;
        }
    }

    return edgeMap;
}

    // Convert a binary edge map to a BufferedImage
    public static BufferedImage toBufferedImage(int[][] edges) {
        int width = edges.length;
        int height = edges[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int value = edges[x][y] > 0 ? 255 : 0;
                int newPixel = (value << 16) | (value << 8) | value;
                image.setRGB(x, y, newPixel);
            }
        }

        return image;
    }

    // Save an image to file
    public static void saveImage(BufferedImage image, String outputPath) {
        try {
            ImageIO.write(image, "jpg", new File(outputPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper class to store gradient results
    static class GradientResult {
        double[][] magnitude;
        double[][] direction;

        GradientResult(double[][] magnitude, double[][] direction) {
            this.magnitude = magnitude;
            this.direction = direction;
        }
    }
    
    public static double[][] computeGradientMagnitude(double[][] gx, double[][] gy) {
        int width = gx.length;
        int height = gx[0].length;
        double[][] magnitude = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                magnitude[x][y] = Math.sqrt(gx[x][y] * gx[x][y] + gy[x][y] * gy[x][y]);
            }
        }
        return magnitude;
    }

    public static double[][] computeGradientDirection(double[][] gx, double[][] gy) {
        int width = gx.length;
        int height = gx[0].length;
        double[][] direction = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                direction[x][y] = Math.atan2(gy[x][y], gx[x][y]);
            }
        }
        return direction;
    }
    
    public static int quantizeDirection(double angle) {
        angle = Math.toDegrees(angle);
        if (angle < 0) angle += 180;

        if ((angle >= 0 && angle < 22.5) || (angle >= 157.5 && angle <= 180)) return 0; // Horizontal
        if (angle >= 22.5 && angle < 67.5) return 45; // Diagonal /
        if (angle >= 67.5 && angle < 112.5) return 90; // Vertical
        return 135; // Diagonal \
    }
}
