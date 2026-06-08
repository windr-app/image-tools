# Contributing to Image Tools

Thank you for your interest in contributing to Image Tools! This document provides guidelines and instructions for contributing to the project.

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [How to Contribute](#how-to-contribute)
- [Coding Standards](#coding-standards)
- [Testing Requirements](#testing-requirements)
- [Pull Request Process](#pull-request-process)
- [Reporting Issues](#reporting-issues)

## Code of Conduct

### Our Pledge

We are committed to providing a welcoming and inspiring community for all. Please be respectful and constructive in all interactions.

### Our Standards

**Positive behavior includes:**
- Using welcoming and inclusive language
- Being respectful of differing viewpoints and experiences
- Gracefully accepting constructive criticism
- Focusing on what is best for the community
- Showing empathy towards other community members

**Unacceptable behavior includes:**
- Harassment, trolling, or derogatory comments
- Public or private harassment
- Publishing others' private information without permission
- Other conduct that could reasonably be considered inappropriate

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- Java 21 or higher
- Maven 3.6 or higher
- Git
- A Java IDE (IntelliJ IDEA, Eclipse, or VS Code recommended)

### Fork and Clone

1. Fork the repository on GitHub
2. Clone your fork locally:

```bash
git clone https://github.com/YOUR-USERNAME/image-tools.git
cd image-tools
```

3. Add the upstream repository:

```bash
git remote add upstream https://github.com/windr-app/image-tools.git
```

## Development Setup

1. **Build the project:**

```bash
mvn clean install
```

2. **Run tests to ensure everything works:**

```bash
mvn test
```

3. **Import into your IDE:**
   - **IntelliJ IDEA:** File → Open → Select pom.xml
   - **Eclipse:** File → Import → Maven → Existing Maven Projects
   - **VS Code:** Open folder and install Java Extension Pack

## How to Contribute

### Types of Contributions

We welcome various types of contributions:

- **Bug Fixes:** Fix issues in the codebase
- **New Features:** Add new functionality
- **Documentation:** Improve or add documentation
- **Tests:** Add or improve test coverage
- **Performance:** Optimize existing code
- **Code Quality:** Refactoring and cleanup

### Finding Work

- Check the [Issues](https://github.com/windr-app/image-tools/issues) page for open tasks
- Look for issues labeled `good first issue` for beginner-friendly tasks
- Issues labeled `help wanted` are actively seeking contributors

### Before Starting Work

1. **Check existing issues:** Ensure the issue hasn't been reported or is being worked on
2. **Create an issue:** For new features or significant changes, create an issue first to discuss
3. **Comment on the issue:** Let others know you're working on it to avoid duplicate work

## Coding Standards

### Java Style Guide

We use **Checkstyle** to enforce coding standards. The configuration is in `checkstyle.xml`.

**Key standards:**

1. **Indentation:** Use 4 spaces (no tabs)
2. **Line Length:** Maximum 100 characters
3. **Naming Conventions:**
   - Classes: PascalCase (e.g., `ImageManipulator`)
   - Methods: camelCase (e.g., `saveAllImages`)
   - Constants: UPPER_SNAKE_CASE (e.g., `MAX_SIZE`)
   - Variables: camelCase (e.g., `bufferedImage`)

4. **Braces:** Opening brace on same line, closing brace on new line

```java
// Good
public void method() {
    if (condition) {
        doSomething();
    }
}

// Bad
public void method()
{
    if (condition)
    {
        doSomething();
    }
}
```

5. **Javadoc:** All public methods and classes must have Javadoc comments

```java
/**
 * Loads an image from the specified path.
 *
 * @param path The file path to the image
 * @return The loaded BufferedImage, or null if loading fails
 */
public BufferedImage load(String path) {
    // implementation
}
```

### Running Checkstyle

```bash
mvn checkstyle:check
```

Fix any violations before submitting your pull request.

## Testing Requirements

### Writing Tests

- All new features **must** include unit tests
- Bug fixes **should** include a test that reproduces the bug
- Aim for high code coverage (target: 80%+)

### Test Structure

Tests are located in `src/test/java/org/windr/util/`

**Test naming convention:**
```java
@Test
void testMethodName_Scenario_ExpectedBehavior() {
    // Arrange
    ImageManipulator manipulator = new ImageManipulator();

    // Act
    BufferedImage result = manipulator.load("test.png");

    // Assert
    assertNotNull(result);
}
```

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ImageManipulatorTest

# Run with coverage
mvn clean test -Pcoverage-per-test
```

### Test Coverage

After running tests with coverage, view the report:
```bash
open target/site/jacoco/index.html
```

## Pull Request Process

### Creating a Pull Request

1. **Create a feature branch:**

```bash
git checkout -b feature/your-feature-name
# or
git checkout -b fix/issue-number-description
```

2. **Make your changes:**
   - Write clean, well-documented code
   - Follow coding standards
   - Add tests for new functionality

3. **Commit your changes:**

```bash
git add .
git commit -m "Add feature: description of your changes"
```

Use clear, descriptive commit messages:
- `Add feature: image rotation support`
- `Fix: null pointer exception in load method`
- `Docs: update API reference for saveAllImages`
- `Test: add tests for WebP format support`

4. **Keep your branch updated:**

```bash
git fetch upstream
git rebase upstream/main
```

5. **Push to your fork:**

```bash
git push origin feature/your-feature-name
```

6. **Create the Pull Request:**
   - Go to GitHub and create a PR from your branch
   - Fill out the PR template completely
   - Link any related issues

### Pull Request Checklist

Before submitting, ensure:

- [ ] Code follows the project's coding standards
- [ ] All tests pass (`mvn test`)
- [ ] Checkstyle validation passes (`mvn checkstyle:check`)
- [ ] New functionality includes tests
- [ ] Documentation is updated (if applicable)
- [ ] Commit messages are clear and descriptive
- [ ] Branch is up-to-date with main

### PR Review Process

1. **Automated Checks:** CI will run tests and code quality checks
2. **Code Review:** Maintainers will review your code
3. **Feedback:** Address any requested changes
4. **Approval:** Once approved, your PR will be merged

**Be prepared to:**
- Respond to feedback
- Make requested changes
- Discuss implementation decisions

## Reporting Issues

### Bug Reports

When reporting a bug, include:

1. **Clear title:** Brief description of the issue
2. **Description:** Detailed explanation of the bug
3. **Steps to reproduce:**
   ```
   1. Load image with method X
   2. Call method Y
   3. Observe error Z
   ```
4. **Expected behavior:** What should happen
5. **Actual behavior:** What actually happens
6. **Environment:**
   - Java version
   - OS and version
   - Maven version
7. **Error messages:** Complete stack traces if applicable
8. **Sample code:** Minimal reproducible example

### Feature Requests

For feature requests, include:

1. **Clear title:** Brief description of the feature
2. **Use case:** Why is this feature needed?
3. **Proposed solution:** How should it work?
4. **Alternatives:** Other solutions you've considered
5. **Additional context:** Any other relevant information

### Questions

For questions:
- Check existing documentation first
- Search closed issues for similar questions
- Provide context about what you're trying to accomplish

## Development Tips

### Useful Maven Commands

```bash
# Clean build
mvn clean install

# Skip tests (use sparingly)
mvn install -DskipTests

# Run specific test
mvn test -Dtest=ImageManipulatorTest#testMethodName

# Generate Javadoc
mvn javadoc:javadoc

# Check for dependency updates
mvn versions:display-dependency-updates
```

### Debugging

Enable debug logging by configuring the logger in your test:

```java
Logger logger = Logger.getLogger("org.windr.util.ImageManipulator");
logger.setLevel(Level.ALL);
```

### Working with Images

Test images are in `src/test/resources/`:
- `img-001.png` - Non-square PNG (604x1000)
- `img-002.webp` - Non-square WebP (720x1080)
- `img-003.png` - Square PNG (805x805)

## Getting Help

- **Documentation:** Check the [docs](docs/) directory
- **Issues:** Search existing issues or create a new one
- **Discussions:** Use GitHub Discussions for general questions

## Recognition

Contributors will be recognized in:
- GitHub contributors page
- Release notes for significant contributions

## License

By contributing, you agree that your contributions will be licensed under the MIT License.

---

Thank you for contributing to Image Tools! Your efforts help make this project better for everyone.
