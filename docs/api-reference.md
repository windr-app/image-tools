# API Reference

## ImageManipulator Class

The `ImageManipulator` class provides utilities for loading, manipulating, and saving images in various formats and sizes.

**Package:** `org.windr.util`

### Constructor

```java
public ImageManipulator()
```

Creates a new instance of ImageManipulator.

### Public Methods

#### load(BufferedImage image)

```java
public BufferedImage load(BufferedImage image)
```

Loads a BufferedImage for manipulation.

**Parameters:**
- `image` - The BufferedImage to be manipulated

**Returns:**
- The loaded BufferedImage

**Example:**
```java
BufferedImage img = ImageIO.read(new File("path/to/image.png"));
ImageManipulator manipulator = new ImageManipulator();
manipulator.load(img);
```

#### load(String path)

```java
public BufferedImage load(String path)
```

Loads an image from the specified file path. Supports PNG, WebP, and other formats supported by ImageIO.

**Parameters:**
- `path` - The file path to the image

**Returns:**
- The loaded BufferedImage, or null if loading fails

**Supported Formats:**
- PNG
- WebP (via TwelveMonkeys ImageIO)
- JPEG
- GIF
- BMP

**Example:**
```java
ImageManipulator manipulator = new ImageManipulator();
BufferedImage image = manipulator.load("src/test/resources/img-001.png");
```

#### saveAllImages(String path, String baseName)

```java
public void saveAllImages(String path, String baseName)
```

Generates and saves multiple versions of the loaded image at different sizes. This method automatically:
1. Makes the image square by adding transparent padding if needed
2. Creates four versions: small (256x256), medium (512x512), large (1024x1024), and original
3. Saves all versions as PNG files with appropriate suffixes

**Parameters:**
- `path` - The directory path where images will be saved (created if it doesn't exist)
- `baseName` - The base name for the generated files

**Generated Files:**
- `{baseName}-256x256.png` - Small version (256x256 pixels)
- `{baseName}-512x512.png` - Medium version (512x512 pixels)
- `{baseName}-1024x1024.png` - Large version (1024x1024 pixels)
- `{baseName}-original.png` - Original size (padded to square if needed)

**Image Processing:**
- Non-square images are automatically padded with transparent pixels to make them square
- High-quality bicubic interpolation is used for resizing
- Maintains aspect ratio by centering the original image within the square canvas

**Example:**
```java
ImageManipulator manipulator = new ImageManipulator();
manipulator.load("path/to/image.png");
manipulator.saveAllImages("output/", "my-image");
// Creates: output/my-image-256x256.png
//          output/my-image-512x512.png
//          output/my-image-1024x1024.png
//          output/my-image-original.png
```

#### isSquare()

```java
public boolean isSquare()
```

Checks if the loaded image is square (or nearly square within a 5% tolerance).

**Returns:**
- `true` if the width and height are equal or differ by no more than 5% of the width
- `false` otherwise

**Example:**
```java
ImageManipulator manipulator = new ImageManipulator();
manipulator.load("path/to/image.png");
if (manipulator.isSquare()) {
    System.out.println("Image is square or nearly square");
}
```

### Image Size Specifications

The class supports the following predefined sizes:

| Size     | Dimensions | Suffix        | Use Case                    |
|----------|-----------|---------------|-----------------------------|
| Small    | 256x256   | -256x256     | Thumbnails, icons           |
| Medium   | 512x512   | -512x512     | Preview images              |
| Large    | 1024x1024 | -1024x1024   | High-resolution displays    |
| Original | Varies    | -original    | Preserves original size     |

### Image Quality

- **Interpolation:** Bicubic interpolation is used for high-quality image resizing
- **Format:** All output images are saved as PNG to preserve transparency
- **Color Space:** Supports ARGB (Alpha, Red, Green, Blue) for transparency

### Usage Example

Complete example of loading an image and generating all versions:

```java
import org.windr.util.ImageManipulator;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Example {
    public static void main(String[] args) {
        // Create manipulator instance
        ImageManipulator manipulator = new ImageManipulator();

        // Load image from file
        BufferedImage image = manipulator.load("input/photo.webp");

        if (image != null) {
            // Check if image is square
            if (!manipulator.isSquare()) {
                System.out.println("Image will be padded to make it square");
            }

            // Generate all versions
            manipulator.saveAllImages("output/", "my-photo");

            System.out.println("All versions generated successfully!");
        }
    }
}
```

### Error Handling

The class uses Java's built-in exception handling:

- **IOException:** Caught and stack trace printed for file I/O errors
- **Null Safety:** Returns null if image loading fails
- **Directory Creation:** Automatically creates output directories if they don't exist

### Logging

The class uses `java.util.logging.Logger` for informational messages:

- Image path logging when saving
- Size information when creating resized versions

Logger name: `org.windr.util.ImageManipulator`
