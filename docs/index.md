# WindR Image Tools

This project provides utilities for image manipulation.

## Features

- Load images from various formats (PNG, WebP, etc.)
- Resize images to multiple predefined sizes
- Export images in PNG and SVG formats
- Automatic padding for non-square images

## Image Export Formats

### PNG Export
The default export format. Generates raster images in multiple sizes:
- Small: 256x256 pixels
- Medium: 512x512 pixels
- Large: 1024x1024 pixels
- Original: Maintains source dimensions

### SVG Export
Export images as Scalable Vector Graphics for improved portability and compatibility.

#### Benefits of SVG Export
- **Portability**: SVG is a widely-supported web standard format
- **Scalability**: Can be resized without quality loss
- **Compatibility**: Works with vector graphics applications, web browsers, and design tools
- **Accessibility**: Text-based format that can be edited and version-controlled

#### Usage

Export a single SVG file:
```java
ImageManipulator manipulator = new ImageManipulator();
manipulator.load("path/to/image.png");
manipulator.exportAsSVG("output/path/", "filename");
```

Export all sizes as SVG and PNG:
```java
manipulator.saveAllImages("output/path/", "base-name", true);
```

#### Technical Details
- SVG files embed the raster image as base64-encoded PNG data
- Maintains pixel-perfect fidelity to the original image
- Includes proper SVG namespace declarations and viewBox for responsive scaling
- Generated files are valid SVG 1.1 compliant

#### Limitations
- Raster images are embedded as base64 PNG data (not true vector graphics)
- File sizes may be larger than PNG due to base64 encoding overhead (~33% larger)
- Complex vector-specific features (paths, filters, animations) are not generated

#### Best Practices
- Use SVG export for images that need to be embedded in web applications
- Consider SVG when you need a single file format that scales well
- For true vector graphics, consider using dedicated vector editing tools
- SVG files can be further optimized using tools like SVGO if needed

## API Documentation

See [README.md](../README.md) for complete API usage examples.
