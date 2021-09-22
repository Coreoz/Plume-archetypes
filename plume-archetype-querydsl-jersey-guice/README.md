Plume Maven archetype with Querydsl, Jersey and Guice
=====================================================

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.coreoz/plume-archetype-querydsl-jersey-guice/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.coreoz/plume-archetype-querydsl-jersey-guice)

This Maven archetype will create an empty project with:
- a WAR file building configuration,
- Jersey webservices configured,
- Querydsl pre-configured,
- Guice to wire it up.

Getting started
---------------
1. Create a project with the
[Maven](https://maven.apache.org/guides/introduction/introduction-to-archetypes.html) archetype,
execute the command:
`mvn archetype:generate -DarchetypeGroupId=com.coreoz -DarchetypeArtifactId=plume-archetype-querydsl-jersey-guice -DarchetypeVersion=3.0.0`
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
