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
`mvn archetype:generate -DarchetypeGroupId=com.coreoz -DarchetypeArtifactId=plume-archetype-querydsl-jersey-guice -DarchetypeVersion=1.1.0`
2. See instructions in the generated project `README.md` file
