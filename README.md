# Image Tools

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE.md)

A Java library for image manipulation that provides utilities for loading, resizing, and saving images in multiple formats and sizes. Perfect for creating thumbnails, processing user uploads, and generating responsive image sets.

## Features

- **Multi-Format Support**: Load PNG, WebP, JPEG, GIF, and BMP images
- **Automatic Resizing**: Generate small (256x256), medium (512x512), large (1024x1024), and original size versions
- **Smart Padding**: Automatically pads non-square images with transparent pixels to create square outputs
- **High Quality**: Uses bicubic interpolation for superior image quality
- **Batch Processing**: Process multiple images efficiently
- **WebP Support**: Full support for WebP format via TwelveMonkeys ImageIO

## Quick Start

```java
import org.windr.util.ImageManipulator;

public class Example {
    public static void main(String[] args) {
        ImageManipulator manipulator = new ImageManipulator();
        manipulator.load("input/photo.png");
        manipulator.saveAllImages("output/", "processed");
        // Creates: processed-256x256.png, processed-512x512.png,
        //          processed-1024x1024.png, processed-original.png
    }
}
```

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Documentation](#documentation)
- [Building from Source](#building-from-source)
- [Running Tests](#running-tests)
- [Contributing](#contributing)
- [License](#license)

## Installation

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Maven Dependency

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.windr.util</groupId>
    <artifactId>image-tools</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Usage

### Basic Usage

```java
ImageManipulator manipulator = new ImageManipulator();
manipulator.load("path/to/image.png");
manipulator.saveAllImages("output/", "my-image");
```

### Check if Image is Square

```java
ImageManipulator manipulator = new ImageManipulator();
manipulator.load("path/to/image.png");

if (manipulator.isSquare()) {
    System.out.println("Image is square or nearly square (within 5% tolerance)");
}
```

### Process WebP Images

```java
ImageManipulator manipulator = new ImageManipulator();
BufferedImage image = manipulator.load("photo.webp");

if (image != null) {
    manipulator.saveAllImages("output/", "converted");
}
```

### Batch Processing

```java
File dir = new File("input/");
File[] files = dir.listFiles();

for (File file : files) {
    ImageManipulator manipulator = new ImageManipulator();
    manipulator.load(file.getAbsolutePath());
    String baseName = file.getName().replaceFirst("[.][^.]+$", "");
    manipulator.saveAllImages("output/", baseName);
}
```

## Documentation

Comprehensive documentation is available in the `docs/` directory:

- **[API Reference](docs/api-reference.md)** - Detailed API documentation for all classes and methods
- **[Usage Guide](docs/usage-guide.md)** - Examples, use cases, and troubleshooting

## Building from Source

1. Clone the repository:
```bash
git clone https://github.com/windr-app/image-tools.git
cd image-tools
```

2. Build with Maven:
```bash
mvn clean install
```

The compiled JAR will be available in the `target/` directory.

## Running Tests

Execute the test suite:

```bash
mvn test
```

Run tests with coverage report:

```bash
mvn clean test -Pcoverage-per-test
```

## Project Structure

```
image-tools/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/windr/util/
│   │           └── ImageManipulator.java
│   └── test/
│       ├── java/
│       │   └── org/windr/util/
│       │       └── ImageManipulatorTest.java
│       └── resources/
│           ├── img-001.png
│           ├── img-002.webp
│           └── img-003.png
├── docs/
│   ├── api-reference.md
│   ├── usage-guide.md
│   └── index.md
├── pom.xml
├── checkstyle.xml
├── README.md
├── LICENSE.md
└── CONTRIBUTING.md
```

## Built With

- **[Maven](https://maven.apache.org/)** - Dependency Management
- **[JUnit 5](https://junit.org/junit5/)** - Testing Framework
- **[TwelveMonkeys ImageIO](https://github.com/haraldk/TwelveMonkeys)** - WebP and enhanced image format support
- **[JaCoCo](https://www.jacoco.org/)** - Code Coverage
- **[Checkstyle](https://checkstyle.org/)** - Code Quality

## Use Cases

- **E-commerce**: Generate product images in multiple sizes for search results, grids, and detail pages
- **User Profiles**: Process user-uploaded avatars and create thumbnails
- **Content Management**: Batch process images for responsive websites
- **Image Galleries**: Create optimized versions for different display contexts
- **Mobile Applications**: Generate appropriately sized images for different device resolutions

## Performance

- Uses high-quality bicubic interpolation for resizing
- Automatically creates output directories
- Supports transparent backgrounds (ARGB)
- Efficient memory usage for batch processing

## System Requirements

- **Java Version**: 21+
- **Memory**: Recommended 2GB+ heap for processing large images
- **Disk Space**: Adequate space for generated image versions

## Contributing

We welcome contributions! Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on:

- Code of conduct
- Development setup
- Coding standards
- Pull request process
- Testing requirements

## Versioning

This project uses [Semantic Versioning](https://semver.org/). For available versions, see the [releases page](https://github.com/windr-app/image-tools/releases).

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Support

- **Issues**: Report bugs or request features via [GitHub Issues](https://github.com/windr-app/image-tools/issues)
- **Documentation**: See the [docs](docs/) directory for detailed guides
- **Website**: [https://windr.org](https://windr.org)

## Acknowledgments

- TwelveMonkeys ImageIO team for WebP support
- Contributors and maintainers of this project
