package org.windr.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    @Test
    void testExportAsSVG() {
        ImageManipulator imageManipulator = new ImageManipulator();
        String path = "src/test/resources/img-001.png";
        BufferedImage image = imageManipulator.load(path);
        assertNotNull(image);

        // Export as SVG
        String outputPath = "tmp/svg-test/";
        String baseName = "test-export";
        imageManipulator.exportAsSVG(outputPath, baseName);

        // Verify SVG file was created
        File svgFile = new File(outputPath + baseName + ".svg");
        assertTrue(svgFile.exists(), "SVG file should be created");
        assertTrue(svgFile.length() > 0, "SVG file should not be empty");

        // Verify SVG content
        try {
            String content = Files.readString(Paths.get(svgFile.getPath()));
            assertTrue(content.contains("<?xml version=\"1.0\""), "SVG should have XML declaration");
            assertTrue(content.contains("<svg"), "SVG should have svg tag");
            assertTrue(content.contains("xmlns=\"http://www.w3.org/2000/svg\""), "SVG should have correct namespace");
            assertTrue(content.contains("<image"), "SVG should have image element");
            assertTrue(content.contains("data:image/png;base64,"), "SVG should have embedded base64 image");
            assertTrue(content.contains("width=\"" + image.getWidth() + "\""), "SVG should have correct width");
            assertTrue(content.contains("height=\"" + image.getHeight() + "\""), "SVG should have correct height");
        } catch (IOException e) {
            fail("Failed to read SVG file content: " + e.getMessage());
        }
    }

    @Test
    void testSaveAllImagesWithSVG() {
        ImageManipulator imageManipulator = new ImageManipulator();
        String path = "src/test/resources/img-003.png";
        BufferedImage image = imageManipulator.load(path);
        assertNotNull(image);

        // Save all images including SVG
        String outputPath = "tmp/svg-all-test/";
        String baseName = "test-all-formats";
        imageManipulator.saveAllImages(outputPath, baseName, true);

        // Verify PNG files exist
        assertTrue(new File(outputPath + baseName + "-original.png").exists(), "Original PNG should exist");
        assertTrue(new File(outputPath + baseName + "-256x256.png").exists(), "Small PNG should exist");
        assertTrue(new File(outputPath + baseName + "-512x512.png").exists(), "Medium PNG should exist");
        assertTrue(new File(outputPath + baseName + "-1024x1024.png").exists(), "Large PNG should exist");

        // Verify SVG files exist
        File originalSvg = new File(outputPath + baseName + "-original.svg");
        File smallSvg = new File(outputPath + baseName + "-256x256.svg");
        File mediumSvg = new File(outputPath + baseName + "-512x512.svg");
        File largeSvg = new File(outputPath + baseName + "-1024x1024.svg");

        assertTrue(originalSvg.exists(), "Original SVG should exist");
        assertTrue(smallSvg.exists(), "Small SVG should exist");
        assertTrue(mediumSvg.exists(), "Medium SVG should exist");
        assertTrue(largeSvg.exists(), "Large SVG should exist");

        // Verify SVG files are not empty
        assertTrue(originalSvg.length() > 0, "Original SVG should not be empty");
        assertTrue(smallSvg.length() > 0, "Small SVG should not be empty");
        assertTrue(mediumSvg.length() > 0, "Medium SVG should not be empty");
        assertTrue(largeSvg.length() > 0, "Large SVG should not be empty");

        // Verify small SVG has correct dimensions
        try {
            String content = Files.readString(Paths.get(smallSvg.getPath()));
            assertTrue(content.contains("width=\"256\""), "Small SVG should have width 256");
            assertTrue(content.contains("height=\"256\""), "Small SVG should have height 256");
        } catch (IOException e) {
            fail("Failed to read small SVG file content: " + e.getMessage());
        }
    }

    @Test
    void testSaveAllImagesWithoutSVG() {
        ImageManipulator imageManipulator = new ImageManipulator();
        String path = "src/test/resources/img-003.png";
        BufferedImage image = imageManipulator.load(path);
        assertNotNull(image);

        // Save all images without SVG (backward compatibility)
        String outputPath = "tmp/no-svg-test/";
        String baseName = "test-no-svg";
        imageManipulator.saveAllImages(outputPath, baseName, false);

        // Verify PNG files exist
        assertTrue(new File(outputPath + baseName + "-original.png").exists(), "Original PNG should exist");
        assertTrue(new File(outputPath + baseName + "-256x256.png").exists(), "Small PNG should exist");
        assertTrue(new File(outputPath + baseName + "-512x512.png").exists(), "Medium PNG should exist");
        assertTrue(new File(outputPath + baseName + "-1024x1024.png").exists(), "Large PNG should exist");

        // Verify SVG files do NOT exist
        assertFalse(new File(outputPath + baseName + "-original.svg").exists(), "Original SVG should not exist");
        assertFalse(new File(outputPath + baseName + "-256x256.svg").exists(), "Small SVG should not exist");
        assertFalse(new File(outputPath + baseName + "-512x512.svg").exists(), "Medium SVG should not exist");
        assertFalse(new File(outputPath + baseName + "-1024x1024.svg").exists(), "Large SVG should not exist");
    }

    @Test
    void testExportAsSVGWithNullImage() {
        ImageManipulator imageManipulator = new ImageManipulator();
        
        // Try to export without loading an image
        String outputPath = "tmp/null-test/";
        String baseName = "test-null";
        imageManipulator.exportAsSVG(outputPath, baseName);

        // Verify no SVG file was created
        File svgFile = new File(outputPath + baseName + ".svg");
        assertFalse(svgFile.exists(), "SVG file should not be created when image is null");
    }

    @Test
    void testSVGContentStructure() {
        ImageManipulator imageManipulator = new ImageManipulator();
        String path = "src/test/resources/img-002.webp";
        BufferedImage image = imageManipulator.load(path);
        assertNotNull(image);

        // Export as SVG
        String outputPath = "tmp/svg-structure-test/";
        String baseName = "structure-test";
        imageManipulator.exportAsSVG(outputPath, baseName);

        // Verify SVG file structure in detail
        File svgFile = new File(outputPath + baseName + ".svg");
        assertTrue(svgFile.exists(), "SVG file should be created");

        try {
            String content = Files.readString(Paths.get(svgFile.getPath()));
            
            // Check for proper SVG structure
            assertTrue(content.contains("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"), 
                      "SVG should have proper XML declaration");
            assertTrue(content.contains("<!-- Created by WindR Image Tools -->"), 
                      "SVG should have creation comment");
            assertTrue(content.contains("xmlns:xlink=\"http://www.w3.org/1999/xlink\""), 
                      "SVG should have xlink namespace");
            assertTrue(content.contains("viewBox=\"0 0 " + image.getWidth() + " " + image.getHeight() + "\""), 
                      "SVG should have correct viewBox");
            assertTrue(content.contains("<title>WindR Image Export</title>"), 
                      "SVG should have title element");
            assertTrue(content.contains("xlink:href=\"data:image/png;base64,"), 
                      "SVG should use xlink:href for image data");
            assertTrue(content.endsWith("</svg>"), "SVG should end with closing tag");
        } catch (IOException e) {
            fail("Failed to read SVG file content: " + e.getMessage());
        }
    }


}