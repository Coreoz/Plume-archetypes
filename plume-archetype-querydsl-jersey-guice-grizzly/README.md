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
`mvn archetype:generate -DarchetypeGroupId=com.coreoz -DarchetypeArtifactId=plume-archetype-querydsl-jersey-guice-grizzly -DarchetypeVersion=1.0.1`
2. See instructions in the generated project `README.md` file
