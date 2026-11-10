# Maven `target/` folder and building the Spring Boot JAR

The Spring Boot application is in the repository's `compassMap/` directory. Its
Maven project is defined by `compassMap/pom.xml`; run Maven commands from that
directory, or pass the POM path when running from the repository root.

## What is the `target/` folder?

Maven creates `target/` inside the Spring Boot project when it compiles or
packages the application. It contains generated build output, not the
application's source files. Depending on which Maven goals have run, it can
include:

- `classes/` — compiled application classes and copied resources.
- `test-classes/` — compiled test classes.
- `generated-sources/` and `generated-test-sources/` — sources created by build
  tools or annotation processors.
- `demo-0.0.1-SNAPSHOT.jar` — the packaged, executable Spring Boot application.
- `demo-0.0.1-SNAPSHOT.jar.original` — the plain JAR saved by the Spring Boot
  Maven plugin when it repackages the application as an executable JAR.

The exact contents depend on the build. `mvn clean` removes the existing
`target/` directory, and Maven recreates it during the next build. It is
generated output and normally should not be committed to Git.

## Build the JAR from the command line

The project includes the Maven Wrapper, so a separate Maven installation is not
required. Install JDK 21 and run the following from the repository root:

```bash
cd compassMap
./mvnw clean package
```

`clean` removes previous build output; `package` compiles the code, runs the
tests, and packages the application. On Windows, use the wrapper batch file:

```bat
cd compassMap
mvnw.cmd clean package
```

Alternatively, run the wrapper from the repository root and identify the
Spring Boot POM:

```bash
./compassMap/mvnw -f compassMap/pom.xml clean package
```

After a successful build, the executable JAR is at:

```text
compassMap/target/demo-0.0.1-SNAPSHOT.jar
```

The filename is based on the Maven `artifactId` and `version` in
`compassMap/pom.xml`; it may change if either value changes.

## Run the packaged application

From `compassMap/`, run:

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

Then open <http://localhost:8080>. Stop the application with `Ctrl+C`.

## Build without running tests

For a local build where tests should intentionally be skipped:

```bash
./mvnw clean package -DskipTests
```

Use the normal `clean package` command for CI/CD builds so test failures prevent
the JAR from being produced as a successful build artifact.
