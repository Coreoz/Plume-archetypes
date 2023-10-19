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
2. See next section to [finalize project creation](#finalizing-project-creation)

Finalizing project creation
---------------------------

### Java version
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

### Database configuration
To connect to a database, the Plume Querydsl module must be configured:
1. Setup the database connector in the `pom.xml` file (look for the `PUT YOUR DATABASE CONNECTOR HERE` comment
2. Setup the database connection parameters in the `application.conf` file,
   see the [Plume Querydsl documentation](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl#configuration) for details
3. Add the Plume Querydsl module in the Guice configuration class `ApplicationModule`
   by uncommenting the line `install(new GuiceQuerydslModule());`
4. Add database monitoring in `MonitoringWs` API by uncommenting the line `.registerDatabaseHealthCheck(transactionManager)` and the corresponding one in the constructor for `transactionManager`
5. A good pratice is to use [Flyway](https://github.com/flyway/flyway) for database migration. A usage example can be found in the [Plume Showcase project](https://github.com/Coreoz/Plume-showcase). Flyway is  preconfigured, to enable it:
    1. In the `pom.xml` file, uncomment the lines after `Uncomment to use flyway`. Reference the correct Flyway module if MySQL/MariaDB is not your database
    2. In the `WebApplication` class, uncomment the line `injector.getInstance(DatabaseInitializer.class).setup();`
    3. In the `V1__setup_database.sql` file, insert the SQL code for the first initialization of your database
    4. In the `TestModule` class, uncomment the line `install(new GuiceDbTestModule());`
    5. In the `DatabaseInitializer` class, uncomment the code in the `setup()` method

To generate classes corresponding to the database tables,
you can run the `${package}.db.QuerydslGenerator.main()` method.
Before the first run, do not forget to configure
the `TABLES_PREFIX` constant in `QuerydslGenerator`, to match your tables prefix.
For example, if your tables are named `abc_film` and `abc_actor`, then your prefix will be `abc_`.

See the detailed documentations:
- [Plume Database](https://github.com/Coreoz/Plume/tree/master/plume-db)
- [Plume Querydsl](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl)
- [Plume Querydsl codegen](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl-codegen)

### Removing database elements
To completely get rid of the database component:
1. Remove the line `install(new GuiceQuerydslModule());` in the `ApplicationModule` class,
2. Remove the package `db` in the project,
3. Remove the dependencies `plume-db-querydsl` and `plume-db-querydsl-codegen` as long as the commented lines in the `pom.xml` file
4. Remove the `src/main/resources` folder
5. In the configurations files, `src/main/resources/application.conf` and `src/test/resources/application.conf`, remove database configuration

### Configuring CI
Continuous integration enables to automatically run all code based tests each time that the code is pushed remotely. It is very useful to avoid making regressions while pushing new code.

The archetype contains Gitlab and Github configuration files to enable this continuous integration out of the box. After a project is created, the used CI files should be deleted: so if Gitlab is used, Github files should be deleted.

These CI configuration files are:
- Gitlab CI configuration is made in the `.gitlab-ci.yml` file
- Sonar configuration is made in the `sonar-project.properties` file for code quality checks
- Github CI configuration is made in the `.github` folder

Need to migrate to WAR packaging?
----------------------------------
See the [WAR migration guide](../plume-archetype-querydsl-jersey-guice).

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
However, all Plume modules will work as expected with this solution.
