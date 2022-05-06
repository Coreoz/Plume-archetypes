Plume Maven archetype with Querydsl, Jersey, Guice and Grizzly
==============================================================

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.coreoz/plume-archetype-querydsl-jersey-guice-grizzly/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.coreoz/plume-archetype-querydsl-jersey-guice-grizzly)

This Maven archetype will create an empty project with:
- A Grizzly webserver configured,
- Jersey webservices configured,
- Querydsl pre-configured,
- Guice to wire it up.

Getting started
---------------
1. Create a project with the
[Maven archetype](https://maven.apache.org/guides/introduction/introduction-to-archetypes.html) with the command:
`mvn archetype:generate -DarchetypeGroupId=com.coreoz -DarchetypeArtifactId=plume-archetype-querydsl-jersey-guice-grizzly -DarchetypeVersion=4.0.3`
2. See instructions in the generated project `README.md` file

Java version
------------
By default Java 17 is used, to use another version, change these lines in the `pom.xml` file:
```xml
<maven.compiler.source>17</maven.compiler.source>
<maven.compiler.target>17</maven.compiler.target>
```

So for example, to use Java 8 instead, you will need to set:
```xml
<maven.compiler.source>1.8</maven.compiler.source>
<maven.compiler.target>1.8</maven.compiler.target>
```
