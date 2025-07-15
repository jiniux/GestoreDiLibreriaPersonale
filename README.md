# GestoreDiLibreriaPersonale

This project is a personal library manager application built with Java, JavaFX, and Gradle.

## License

This project is licensed under the GNU General Public License v3.0. See the [LICENSE](LICENSE) file for details.

## Prerequisites

- **JDK 23** (required)
- **Gradle** (the project includes the Gradle Wrapper, so you do not need to install Gradle manually)
- **Internet connection** (for downloading dependencies on the first build)

## Building the Project

1. **Clone the repository** (if you haven't already):
   ```sh
   git clone <repository-url>
   cd GestoreDiLibreriaPersonale
   ```

2. **Ensure you are using JDK 23**:
   - Check your Java version:
     ```sh
     java -version
     ```
     - If not JDK 23, download and install one of the following distributions: [Oracle](https://www.oracle.com/java/technologies/downloads/) or [Amazon Corretto](https://aws.amazon.com/corretto/) or [Liberica](https://bell-sw.com/pages/downloads/). This project was successfully built with BellSoft Liberica Full 23.0.1 and Amazon Corretto 23.0.2.

3. **Build the project using Gradle Wrapper**:
   ```sh
   ./gradlew build
   ```
   This will compile the code, run the tests, and package the application.

## Running the Application

After building, you can run the application with:

```sh
./gradlew run
```

Or, to run the generated JAR (after building):

```sh
java -jar build/libs/GestoreDiLibreriaPersonale-1.0-SNAPSHOT.jar
```

## Notes
- The application uses JavaFX, Lombok, and Jackson for its core functionality.
- Make sure your JAVA_HOME environment variable points to your JDK 23 installation.

## Troubleshooting
- If you encounter Java version errors, double-check that you are using JDK 23.
- For any dependency issues, try running `./gradlew clean build`.
