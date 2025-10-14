# Specimen

Specimen is a java commandline application that looks at the raw bytes of a given file.
From that bytecode, it can tell if its a Java class, PNG image, Zip Archive or just another file
It also does binary comparison and has a hexadecimal viewer.

## Features
Hexadecimal file inspection (16 bytes per row)
Has file detection including java class, png and zip files
Binary comparison that checks modified bytes and length differences
Output limited to the first 128 bytes to fit in the terminal.

## Requirements
Java JDK 25 — Compiles and runs Specimen.
Apache Maven 3.10+ — Manages the build and JUnit dependencies.

## Usage

- Building the project
using the command  -> mvn clean compile

- Inspecting a file
java -cp target/classes Specimen sample.txt  <- this command is used to view the file type and hexadecimal contents of the file. 

- Compare two files 
Identify modified bytes positions from the two given file and their length differences. 
java -cp target/classes Specimen before.txt after.txt

## Testing

Specimen uses JUnit 5 for automated testing of file detection,
binary comparison, and edge cases.

Run all tests with:

```bash
mvn test
```