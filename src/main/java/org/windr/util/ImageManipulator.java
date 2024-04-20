package org.windr.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * This class is used to manipulate images.
 */
public class ImageManipulator {
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger("org.windr.util.ImageManipulator");

    private BufferedImage image;

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
     * Checks if the image is a square.
     * @return
     */
    public boolean isSquare() {
        // return true if the width and height are equal or only different from 5%
        return Math.abs(this.image.getWidth() - this.image.getHeight()) <= this.image.getWidth() * 0.05;
    }


}