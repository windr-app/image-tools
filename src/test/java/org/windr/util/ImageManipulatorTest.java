package org.windr.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImageManipulatorTest {

    @BeforeAll
    static void setup() {
        // delete cotnent of tmp folder (files and directories)
        File tmp = new File("tmp");
        if (tmp.exists()) {
            File[] files = tmp.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        File[] subFiles = file.listFiles();
                        if (subFiles != null) {
                            for (File subFile : subFiles) {
                                subFile.delete();
                            }
                        }
                    }
                    file.delete();
                }
            }
        }
    }


    @Test
    void testImageAPI() {
        String path = "src/test/resources/img-001.png";
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
            // resize image by 50% and save it as tmp/foo.png
            int newWidth = img.getWidth() / 10;
            int newHeight = img.getHeight() / 10;
            BufferedImage newImage = new BufferedImage(newWidth, newHeight, img.getType());
            newImage.getGraphics().drawImage(img, 0, 0, newWidth, newHeight, null);
            ImageIO.write(newImage, "png", new File("tmp/foo.png"));
        } catch (IOException e) {
            System.out.println("Error while loading image: " + e.getMessage());
        }
    }


    @Test
    void testSetSourcePNG() {
        ImageManipulator imageManipulator = new ImageManipulator();
        String path = "src/test/resources/img-001.png";
        BufferedImage image = imageManipulator.load(path);
        assertNotNull(image);
        int width = image.getWidth();
        int height = image.getHeight();
        assertEquals(604, width);
        assertEquals(1000, height);
        assertFalse(imageManipulator.isSquare(), "The image is square, should not be square");
        // saving the image in tmp folder
        imageManipulator.saveAllImages("tmp/img-001/", "windr-2024-demo" );
    }

    @Test
    void testSetSourceWebP() {
        ImageManipulator imageManipulator = new ImageManipulator();
        String path = "src/test/resources/img-002.webp";
        BufferedImage image = imageManipulator.load(path);
        assertNotNull(image);
        int width = image.getWidth();
        int height = image.getHeight();
        assertEquals(720, width);
        assertEquals(1080, height);
        assertFalse(imageManipulator.isSquare(), "The image is square, should not be square");
    }

    @Test
    void testSetSourcePNGSquare(){
        ImageManipulator imageManipulator = new ImageManipulator();
        String path = "src/test/resources/img-003.png";
        BufferedImage image = imageManipulator.load(path);
        assertNotNull(image);
        int width = image.getWidth();
        int height = image.getHeight();
        assertEquals(805, width);
        assertEquals(805, height);
        assertTrue(imageManipulator.isSquare(), "The image is not square, should be square");
        imageManipulator.saveAllImages("tmp/img-003/", "windr-2024-demo" );

    }


}