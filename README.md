# Image Tools

This project provides utilities for image manipulation.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 21
- Maven

### Installing

1. Clone the repository
2. Navigate to the project directory
3. Run `mvn install`

## Usage

### Basic Image Manipulation

The `ImageManipulator` class provides various image manipulation capabilities:

```java
ImageManipulator manipulator = new ImageManipulator();
BufferedImage image = manipulator.load("path/to/image.png");

// Save in multiple sizes (PNG format)
manipulator.saveAllImages("output/path/", "base-name");
```

### Export to SVG

The tool supports exporting images to SVG format for improved portability and compatibility with vector graphics applications.

#### Export Single SVG

To export the current image as a single SVG file:

```java
ImageManipulator manipulator = new ImageManipulator();
manipulator.load("path/to/image.png");
manipulator.exportAsSVG("output/path/", "filename");
```

This will create `output/path/filename.svg`.

#### Export All Sizes Including SVG

To export images in multiple sizes as both PNG and SVG:

```java
ImageManipulator manipulator = new ImageManipulator();
manipulator.load("path/to/image.png");

// Export with SVG enabled
manipulator.saveAllImages("output/path/", "base-name", true);
```

This will create:
- PNG files: `base-name-256x256.png`, `base-name-512x512.png`, `base-name-1024x1024.png`, `base-name-original.png`
- SVG files: `base-name-256x256.svg`, `base-name-512x512.svg`, `base-name-1024x1024.svg`, `base-name-original.svg`

#### Export Without SVG (Backward Compatibility)

To maintain backward compatibility, you can export only PNG files:

```java
manipulator.saveAllImages("output/path/", "base-name", false);
```

Or use the original method signature:

```java
manipulator.saveAllImages("output/path/", "base-name");
```

### SVG Export Details

The SVG export functionality:
- **High Fidelity**: Maintains visual accuracy by embedding the raster image as a base64-encoded PNG within the SVG
- **Scalable**: SVG files can be scaled without loss of quality
- **Compatible**: Works with all standard SVG viewers and editors
- **Portable**: SVG is a widely-supported web standard

#### SVG Structure

Generated SVG files include:
- Proper XML and SVG namespace declarations
- Width, height, and viewBox attributes for proper scaling
- Embedded base64-encoded PNG data for pixel-perfect rendering
- Title and comment metadata

## Running the tests

To run the tests, use the following command:

```sh
mvn test
```

Built With
Maven - Dependency Management
JUnit 5 - Testing Framework
TwelveMonkeys ImageIO - Used for WebP image support
Contributing
Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull requests to us.

License
This project is licensed under the MIT License - see the LICENSE.md file for details
