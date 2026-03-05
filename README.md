# VideoStreaming Project

## Overview
This project appears to be a Java-based video streaming application. It includes functionality for video processing, buffering, and database integration. The project also contains dependencies such as MySQL Connector/J and JavaCV for handling video and database operations.

## Features
- Video processing with buffering
- Database connection and management
- Integration with MySQL using Connector/J
- Utilizes JavaCV for video handling

## Project Structure
- `src/CanvasOutput.java`: Main Java source file for video processing and database operations
- `Packages/`: Contains external libraries and dependencies
  - `mysql-connector-j-9.1.0/`: MySQL JDBC driver
  - `javacv-platform-1.5.11-bin/` and `javacv-platform-1.5.11-src/`: JavaCV libraries

## Getting Started
1. **Clone the repository**
2. **Install dependencies**: Ensure Java is installed. Place required libraries in the `Packages` directory.
3. **Configure video path**: Update the `videoPath` variable in `CanvasOutput.java` to point to your video file.
4. **Run the application**: Compile and execute `CanvasOutput.java`.

## Dependencies
- Java (JDK 8 or higher recommended)
- MySQL Connector/J (included in Packages)
- JavaCV (included in Packages)

## Usage
- Update the video file path in `CanvasOutput.java`.
- Ensure MySQL is running and accessible if database features are used.
- Compile and run the main class:
  ```
  javac -cp "Packages/mysql-connector-j-9.1.0/mysql-connector-j-9.1.0.jar;Packages/javacv-platform-1.5.11-bin/*" src/CanvasOutput.java
  java -cp ".;src;Packages/mysql-connector-j-9.1.0/mysql-connector-j-9.1.0.jar;Packages/javacv-platform-1.5.11-bin/*" CanvasOutput
  ```

## License
Some dependencies (e.g., MySQL Connector/J) are licensed under the GNU General Public License v2.0 and the Universal FOSS Exception. Please review their respective licenses for more information.

## Contributing
Pull requests and issues are welcome. Please ensure code follows project conventions and includes appropriate documentation.

## Acknowledgements
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)
- [JavaCV](https://github.com/bytedeco/javacv)
