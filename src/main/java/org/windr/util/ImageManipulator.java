package org.windr.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.io.ByteArrayOutputStream;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * This class is used to manipulate images.
 */
public class ImageManipulator {
    Logger logger = Logger.getLogger("org.windr.util.ImageManipulator");

    private BufferedImage image;

    // Enum to store export formats
    public enum ExportFormat {
        PNG("png"),
        SVG("svg");

        private final String extension;

        ExportFormat(String extension) {
            this.extension = extension;
        }

        public String getExtension() {
            return extension;
        }
    }

    // Enum to store the prefix for the image sizes
    private enum Prefix {
        SMALL("-256x256"),
        MEDIUM("-512x512"),
        LARGE("-1024x1024"),
        ORIGINAL("-original");

        private final String prefix;

        Prefix(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }

    /**
     * Loads the image to be manipulated.
     * @param image
     * @return
     */
    public BufferedImage load(BufferedImage image) {
        this.image = image;
        return image;
    }

    /**
     * Loads the image to be manipulated.
     * @param path
     * @return
     */
    public BufferedImage load(String path) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = javax.imageio.ImageIO.read(new java.io.File(path));
            return load(bufferedImage);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }


    /**
     * Saves all the images generated from the original image.
     *
     * @param path The base path where the images will be saved.
     * @param baseName The base name for the images.
     */
    public void saveAllImages(String path, String baseName) {
        String originalPath = path + baseName + Prefix.ORIGINAL.getPrefix() + ".png";
        String smallPath = path + baseName + Prefix.SMALL.getPrefix() + ".png";
        String mediumPath = path + baseName + Prefix.MEDIUM.getPrefix() + ".png";
        String largePath = path + baseName + Prefix.LARGE.getPrefix() + ".png";

        // log all the paths
        logger.info("Original image path: " + originalPath);
        logger.info("Small image path: " + smallPath);
        logger.info("Medium image path: " + mediumPath);
        logger.info("Large image path: " + largePath);

        // create directory if not exists
        java.io.File directory = new java.io.File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // if image is not a square, then crop it to make it square by adding padding transparent pixels
        if (image.getWidth() != image.getHeight()) {
            int max = Math.max(image.getWidth(), image.getHeight());
            BufferedImage newImage = new BufferedImage(max, max, BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImage.getGraphics();
            g.setColor(new Color(0, 0, 0, 0));
            g.fillRect(0, 0, max, max);
            g.drawImage(image, (max - image.getWidth()) / 2, (max - image.getHeight()) / 2, null);
            g.dispose();
            image = newImage;
        }


        // create small image
        BufferedImage smallImage = createNewSize(Prefix.SMALL, image);
        BufferedImage mediumImage = createNewSize(Prefix.MEDIUM, image);
        BufferedImage largeImage = createNewSize(Prefix.LARGE, image);
        BufferedImage originalImage = createNewSize(Prefix.ORIGINAL, image);

        saveImage(smallPath, smallImage);
        saveImage(mediumPath, mediumImage);
        saveImage(largePath, largeImage);
        saveImage(originalPath, originalImage);



    }

    /**
     * Creates a new image with the specified size.
     * @param size
     * @param image
     * @return
     */
    private BufferedImage createNewSize( Prefix size, BufferedImage image ) {

        logger.info("Creating new image with size: " + size.getPrefix());

        int width = 0;
        int height = 0;
        switch (size) {
            case SMALL:
                width = 256;
                height = 256;
                break;
            case MEDIUM:
                width = 512;
                height = 512;
                break;
            case LARGE:
                width = 1024;
                height = 1024;
                break;
            default:
                width = image.getWidth();
                height = image.getHeight();
                break;
        }

        BufferedImage resizedImage = new BufferedImage(width, height, image.getType());

        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC
        );
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();

        return resizedImage;
    }

    /**
     * Saves the image to the specified path.
     * @param path
     * @param image
     */
    private void saveImage(String path, BufferedImage image) {
        try {
            File directory = new File(path);
            if (!directory.exists()){
                directory.mkdirs(); // creates directory along with non-existent parent directories
            }

            // save image
            // prepare image and save it
            ImageIO.write(image, "png", new File(path));



        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a BufferedImage to SVG format and saves it to the specified path.
     * The SVG will embed the image as a base64-encoded PNG.
     * @param path The path where the SVG file will be saved
     * @param image The BufferedImage to convert
     * @throws IOException if there's an error writing the file
     */
    private void saveImageAsSVG(String path, BufferedImage image) throws IOException {
        logger.info("Saving image as SVG: " + path);
        
        // Create directory if it doesn't exist
        File file = new File(path);
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }

        // Convert BufferedImage to base64-encoded PNG
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Create SVG content
        StringBuilder svgContent = new StringBuilder();
        svgContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
        svgContent.append("<!-- Created by WindR Image Tools -->\n");
        svgContent.append("<svg xmlns=\"http://www.w3.org/2000/svg\" ");
        svgContent.append("xmlns:xlink=\"http://www.w3.org/1999/xlink\" ");
        svgContent.append("width=\"").append(image.getWidth()).append("\" ");
        svgContent.append("height=\"").append(image.getHeight()).append("\" ");
        svgContent.append("viewBox=\"0 0 ").append(image.getWidth()).append(" ").append(image.getHeight()).append("\">\n");
        svgContent.append("  <title>WindR Image Export</title>\n");
        svgContent.append("  <image x=\"0\" y=\"0\" ");
        svgContent.append("width=\"").append(image.getWidth()).append("\" ");
        svgContent.append("height=\"").append(image.getHeight()).append("\" ");
        svgContent.append("xlink:href=\"data:image/png;base64,").append(base64Image).append("\"/>\n");
        svgContent.append("</svg>");

        // Write SVG to file
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(svgContent.toString());
        }
    }

    /**
     * Exports the current image as SVG.
     * @param path The path where the SVG file will be saved
     * @param baseName The base name for the SVG file
     */
    public void exportAsSVG(String path, String baseName) {
        if (image == null) {
            logger.warning("No image loaded. Cannot export to SVG.");
            return;
        }

        try {
            String svgPath = path + baseName + ".svg";
            logger.info("Exporting image as SVG: " + svgPath);
            
            // Create directory if it doesn't exist
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            saveImageAsSVG(svgPath, image);
            logger.info("SVG export completed successfully: " + svgPath);
        } catch (IOException e) {
            logger.severe("Error exporting image as SVG: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Saves all images including SVG export.
     * @param path The base path where the images will be saved
     * @param baseName The base name for the images
     * @param includeSVG Whether to include SVG export
     */
    public void saveAllImages(String path, String baseName, boolean includeSVG) {
        saveAllImages(path, baseName);
        
        if (includeSVG) {
            try {
                // Export original size as SVG
                String svgPath = path + baseName + Prefix.ORIGINAL.getPrefix() + ".svg";
                logger.info("SVG export path: " + svgPath);
                
                BufferedImage originalImage = createNewSize(Prefix.ORIGINAL, image);
                saveImageAsSVG(svgPath, originalImage);
                
                // Also export other sizes as SVG
                String smallSvgPath = path + baseName + Prefix.SMALL.getPrefix() + ".svg";
                String mediumSvgPath = path + baseName + Prefix.MEDIUM.getPrefix() + ".svg";
                String largeSvgPath = path + baseName + Prefix.LARGE.getPrefix() + ".svg";
                
                logger.info("Small SVG path: " + smallSvgPath);
                logger.info("Medium SVG path: " + mediumSvgPath);
                logger.info("Large SVG path: " + largeSvgPath);
                
                BufferedImage smallImage = createNewSize(Prefix.SMALL, image);
                BufferedImage mediumImage = createNewSize(Prefix.MEDIUM, image);
                BufferedImage largeImage = createNewSize(Prefix.LARGE, image);
                
                saveImageAsSVG(smallSvgPath, smallImage);
                saveImageAsSVG(mediumSvgPath, mediumImage);
                saveImageAsSVG(largeSvgPath, largeImage);
                
            } catch (IOException e) {
                logger.severe("Error exporting SVG files: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }


    /**
     * Checks if the image is a square.
     * @return
     */
    public boolean isSquare() {
        // return true if the width and height are equal or only different from 5%
        return Math.abs(this.image.getWidth() - this.image.getHeight()) <= this.image.getWidth() * 0.05;
    }


}