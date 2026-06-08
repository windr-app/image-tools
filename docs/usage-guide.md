# Usage Guide

This guide provides detailed examples and use cases for the Image Tools library.

## Table of Contents

- [Installation](#installation)
- [Quick Start](#quick-start)
- [Common Use Cases](#common-use-cases)
- [Advanced Usage](#advanced-usage)
- [Troubleshooting](#troubleshooting)

## Installation

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Adding to Your Project

#### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.windr.util</groupId>
    <artifactId>image-tools</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

#### Building from Source

```bash
git clone https://github.com/windr-app/image-tools.git
cd image-tools
mvn clean install
```

## Quick Start

Here's a simple example to get started:

```java
import org.windr.util.ImageManipulator;

public class QuickStart {
    public static void main(String[] args) {
        ImageManipulator manipulator = new ImageManipulator();
        manipulator.load("input/photo.png");
        manipulator.saveAllImages("output/", "processed");
    }
}
```

This will create four versions of your image:
- `output/processed-256x256.png`
- `output/processed-512x512.png`
- `output/processed-1024x1024.png`
- `output/processed-original.png`

## Common Use Cases

### 1. Creating Thumbnails from User Uploads

```java
import org.windr.util.ImageManipulator;
import java.io.File;

public class ThumbnailGenerator {
    public void processUserUpload(File uploadedFile, String userId) {
        ImageManipulator manipulator = new ImageManipulator();

        // Load the uploaded image
        manipulator.load(uploadedFile.getAbsolutePath());

        // Generate thumbnails in user's directory
        String outputPath = "uploads/" + userId + "/";
        manipulator.saveAllImages(outputPath, "profile");

        System.out.println("Thumbnails created for user: " + userId);
    }
}
```

### 2. Processing WebP Images

The library supports WebP format through TwelveMonkeys ImageIO:

```java
import org.windr.util.ImageManipulator;
import java.awt.image.BufferedImage;

public class WebPProcessor {
    public void convertWebPToPNG(String webpPath) {
        ImageManipulator manipulator = new ImageManipulator();

        // Load WebP image
        BufferedImage image = manipulator.load(webpPath);

        if (image != null) {
            // Save as PNG in multiple sizes
            manipulator.saveAllImages("converted/", "from-webp");
            System.out.println("WebP converted successfully!");
        } else {
            System.err.println("Failed to load WebP image");
        }
    }
}
```

### 3. Batch Processing Multiple Images

```java
import org.windr.util.ImageManipulator;
import java.io.File;

public class BatchProcessor {
    public void processDirectory(String inputDir, String outputDir) {
        File dir = new File(inputDir);
        File[] files = dir.listFiles((d, name) ->
            name.toLowerCase().endsWith(".png") ||
            name.toLowerCase().endsWith(".jpg") ||
            name.toLowerCase().endsWith(".webp")
        );

        if (files != null) {
            for (File file : files) {
                ImageManipulator manipulator = new ImageManipulator();
                manipulator.load(file.getAbsolutePath());

                // Use filename without extension as base name
                String baseName = file.getName().replaceFirst("[.][^.]+$", "");
                manipulator.saveAllImages(outputDir, baseName);

                System.out.println("Processed: " + file.getName());
            }
        }
    }
}
```

### 4. Checking Image Dimensions

```java
import org.windr.util.ImageManipulator;
import java.awt.image.BufferedImage;

public class ImageValidator {
    public void validateImage(String imagePath) {
        ImageManipulator manipulator = new ImageManipulator();
        BufferedImage image = manipulator.load(imagePath);

        if (image != null) {
            if (manipulator.isSquare()) {
                System.out.println("Image is square - no padding needed");
            } else {
                System.out.println("Image will be padded to square");
                System.out.println("Original: " + image.getWidth() + "x" + image.getHeight());
            }
        }
    }
}
```

### 5. Creating Product Images for E-commerce

```java
import org.windr.util.ImageManipulator;

public class ProductImageProcessor {
    public void processProductImage(String productId, String imagePath) {
        ImageManipulator manipulator = new ImageManipulator();
        manipulator.load(imagePath);

        // Create standardized product images
        String outputPath = "products/" + productId + "/";
        manipulator.saveAllImages(outputPath, productId);

        // The different sizes can be used for:
        // -256x256: Search results thumbnail
        // -512x512: Product grid view
        // -1024x1024: Product detail page
        // -original: High-res zoom/download
    }
}
```

## Advanced Usage

### Working with BufferedImage Directly

```java
import org.windr.util.ImageManipulator;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class AdvancedUsage {
    public void processBufferedImage() throws Exception {
        // Load image with ImageIO
        BufferedImage original = ImageIO.read(new File("input.png"));

        // Apply custom processing (example: grayscale)
        BufferedImage processed = convertToGrayscale(original);

        // Use ImageManipulator for resizing
        ImageManipulator manipulator = new ImageManipulator();
        manipulator.load(processed);
        manipulator.saveAllImages("output/", "grayscale");
    }

    private BufferedImage convertToGrayscale(BufferedImage img) {
        BufferedImage grayscale = new BufferedImage(
            img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY
        );
        grayscale.getGraphics().drawImage(img, 0, 0, null);
        return grayscale;
    }
}
```

### Custom Output Directory Structure

```java
import org.windr.util.ImageManipulator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrganizedStorage {
    public void saveWithDateStructure(String imagePath, String baseName) {
        ImageManipulator manipulator = new ImageManipulator();
        manipulator.load(imagePath);

        // Create date-based directory structure
        LocalDate today = LocalDate.now();
        String year = today.format(DateTimeFormatter.ofPattern("yyyy"));
        String month = today.format(DateTimeFormatter.ofPattern("MM"));
        String day = today.format(DateTimeFormatter.ofPattern("dd"));

        String outputPath = String.format("images/%s/%s/%s/", year, month, day);
        manipulator.saveAllImages(outputPath, baseName);
    }
}
```

### Integration with Web Applications

```java
import org.windr.util.ImageManipulator;
import javax.servlet.http.Part;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class WebUploadHandler {
    public void handleImageUpload(Part filePart, String userId) {
        try {
            // Read uploaded file
            InputStream input = filePart.getInputStream();
            BufferedImage image = ImageIO.read(input);

            // Process with ImageManipulator
            ImageManipulator manipulator = new ImageManipulator();
            manipulator.load(image);

            // Save to user's directory
            String timestamp = String.valueOf(System.currentTimeMillis());
            manipulator.saveAllImages(
                "uploads/" + userId + "/",
                "img-" + timestamp
            );

            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## Troubleshooting

### Common Issues

#### Image Fails to Load

**Problem:** `load()` returns null

**Solutions:**
- Verify the file path is correct and the file exists
- Check file permissions
- Ensure the image format is supported
- For WebP images, verify TwelveMonkeys dependency is included

```java
BufferedImage image = manipulator.load(path);
if (image == null) {
    System.err.println("Failed to load image from: " + path);
    // Check if file exists
    File f = new File(path);
    if (!f.exists()) {
        System.err.println("File does not exist!");
    }
}
```

#### Out of Memory Errors

**Problem:** `OutOfMemoryError` when processing large images

**Solutions:**
- Increase JVM heap size: `java -Xmx2g -jar yourapp.jar`
- Process images sequentially rather than in parallel
- Dispose of BufferedImage objects when done

```java
// Good practice: process one at a time
for (File file : files) {
    ImageManipulator manipulator = new ImageManipulator();
    manipulator.load(file.getAbsolutePath());
    manipulator.saveAllImages("output/", file.getName());
    manipulator = null; // Allow garbage collection
    System.gc(); // Hint to GC (if needed for very large images)
}
```

#### Directory Not Created

**Problem:** Output directory doesn't exist

**Solution:** The library automatically creates directories, but ensure parent directories are accessible:

```java
String outputPath = "output/images/";
File dir = new File(outputPath);
if (!dir.exists()) {
    boolean created = dir.mkdirs();
    if (!created) {
        System.err.println("Could not create directory: " + outputPath);
    }
}
```

#### Poor Image Quality

**Problem:** Resized images look pixelated

**Explanation:** The library uses bicubic interpolation for high quality. If images still look poor:
- Source image may be too small for upscaling
- Consider using larger source images
- The library is optimized for downscaling

```java
// Check source image size before processing
BufferedImage image = manipulator.load(path);
if (image != null) {
    if (image.getWidth() < 1024 || image.getHeight() < 1024) {
        System.out.println("Warning: Source image is smaller than 1024x1024");
        System.out.println("Upscaling may reduce quality");
    }
}
```

### Performance Tips

1. **Reuse ImageManipulator instances** when processing similar images
2. **Process images in batches** during off-peak hours
3. **Use appropriate JVM memory settings** for your workload
4. **Consider caching** processed images to avoid redundant processing
5. **Monitor disk space** when processing large batches

### Getting Help

- **Issues:** Report bugs at [GitHub Issues](https://github.com/windr-app/image-tools/issues)
- **Documentation:** See [API Reference](api-reference.md)
- **Contributing:** Read [CONTRIBUTING.md](../CONTRIBUTING.md)
