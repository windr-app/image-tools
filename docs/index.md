# WindR Image Tools

A Java library for image manipulation that provides utilities for loading, resizing, and saving images in multiple formats and sizes.

## Documentation

- **[API Reference](api-reference.md)** - Complete API documentation for all classes and methods
- **[Usage Guide](usage-guide.md)** - Detailed examples, use cases, and troubleshooting

## Quick Links

- [GitHub Repository](https://github.com/windr-app/image-tools)
- [Report Issues](https://github.com/windr-app/image-tools/issues)
- [Contributing Guidelines](../CONTRIBUTING.md)
- [License](../LICENSE.md)

## Features

- Multi-format support (PNG, WebP, JPEG, GIF, BMP)
- Automatic image resizing (256x256, 512x512, 1024x1024, original)
- Smart padding for non-square images
- High-quality bicubic interpolation
- Batch processing capabilities
- Full WebP support

## Getting Started

```java
import org.windr.util.ImageManipulator;

public class Example {
    public static void main(String[] args) {
        ImageManipulator manipulator = new ImageManipulator();
        manipulator.load("input/photo.png");
        manipulator.saveAllImages("output/", "processed");
    }
}
```

## Installation

### Maven

```xml
<dependency>
    <groupId>org.windr.util</groupId>
    <artifactId>image-tools</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### Requirements

- Java 21 or higher
- Maven 3.6 or higher

