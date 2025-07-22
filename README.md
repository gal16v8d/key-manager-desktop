# key-manager-desktop

Util that helps you manage (locally) online accounts (similar to keepass).

# Pre-requisites

* Java 21
* Maven

# Build
This is a simple java desktop application.
To build it, you need to have maven installed.
To build the project, run the following command in the root directory:

```bash
mvn clean package
```
Then look for the fat jar in the `target` directory, which will be named something like `pc-stock-fat.jar`.

# Run
To run the application, you can use the following command:

```bash 
java -jar target/key-manager-desktop-fat.jar
```