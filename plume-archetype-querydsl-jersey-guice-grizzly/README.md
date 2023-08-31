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
`mvn archetype:generate -DarchetypeGroupId=com.coreoz -DarchetypeArtifactId=plume-archetype-querydsl-jersey-guice-grizzly -DarchetypeVersion=4.1.1`
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

Deploying to production
-----------------------
In the default mode, when `mvn package` is executed, all the projects jar files and startup BAT/Bash files are generated.
These startup files will not work since they are built only for Play Framework.
This solution is not ideal, eventually we should make our own Maven plugin
so that the startup files works correctly with Plume Framework.

If `appserver` is used, then configure the server with `export SERVER=javazip`,
it will correctly build and launch the project.

If not there are 3 solutions:
- switch back to the WAR file generation: see the [Plume War archetype](https://github.com/Coreoz/Plume-archetypes/tree/master/plume-archetype-querydsl-jersey-guice),
- create a maven plugin like `play2-maven-plugin` that produce Plume compatible startup scripts (if you choose this option, please share your work :),
- replace in the `pom.xml` file the `play2-maven-plugin` and the `maven-jar-plugin` plugins by
```xml
<!-- single jar executable with all dependencies -->
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-shade-plugin</artifactId>
	<version>3.1.0</version>
	<configuration>
		<filters>
			<filter>
				<artifact>*:*</artifact>
				<excludes>
					<exclude>META-INF/*.SF</exclude>
					<exclude>META-INF/*.DSA</exclude>
					<exclude>META-INF/*.RSA</exclude>
				</excludes>
			</filter>
		</filters>
	</configuration>
	<executions>
		<execution>
			<phase>package</phase>
			<goals>
				<goal>shade</goal>
			</goals>
			<configuration>
				<transformers>
					<transformer
						implementation="org.apache.maven.plugins.shade.resource.ServicesResourceTransformer" />
					<transformer
						implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
						<mainClass>${package}.WebApplication</mainClass>
					</transformer>
				</transformers>
			</configuration>
		</execution>
	</executions>
</plugin>
```
With this solution, `mvn package` will produce an executable jar file.
Note that this last solution may produce side effects: files that share the same name can be overridden.
However all Plume modules will work as expected with this solution.

Need to migrate to WAR packaging ?
----------------------------------
See the [WAR migration guide](../plume-archetype-querydsl-jersey-guice).
