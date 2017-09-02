${artifactId}
=============

Welcome to your [Plume](https://github.com/Coreoz/Plume) project!
Here are some reminders to get your project up and running.

Configuring your IDE
--------------------
Install [Lombok](https://projectlombok.org/): http://jnb.ociweb.com/jnb/jnbJan2010.html#installation

Launching the server
--------------------
Just run the `${package}.WebApplication` class, you can then open your browser to <http://localhost:8080>.

Configuration
-------------
The configuration file is located in `src/main/resources/application.conf`.
If you have any doubt, check out the [configuration documentation](https://github.com/Coreoz/Plume/tree/master/plume-conf). 

Database
--------
To generate classes corresponding to the database tables,
you can run the `${package}.db.QuerydslGenerator.main()` method.
Before the first run, do not forget to configure:
- The `TABLES_PREFIX` constant in `QuerydslGenerator`, to match your tables prefix.
For example, if your table are named `abc_film` and `abc_actor`, then your prefix will be `abc_`
- The database connection in the [configuration file](#configuration).
- Add your database connector dependency in the `pom.xml` file, for an exemple see the commented mysql connector.

See the corresponding documentations:
- [Plume Database](https://github.com/Coreoz/Plume/tree/master/plume-db)
- [Plume Querydsl](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl)
- [Plume Querydsl codegen](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl-codegen)

More modules
------------
- [Plume Mail](https://github.com/Coreoz/Plume/tree/master/plume-mail): send mails,
- [Plume Scheduler](https://github.com/Coreoz/Plume/tree/master/plume-scheduler): schedule recurring Java jobs/tasks,
- [Plume Admin](https://github.com/Coreoz/Plume-admin): web-service with a security layer to create an admin area,
- [Plume Admin AngularJs](https://github.com/Coreoz/Plume-admin-ui-angularjs): an administration HTML/JS UI that use Plume Admin,
- [Plume File](https://github.com/Coreoz/Plume-file/tree/master/plume-file-core): manage and serve files,
- [Plume File Gallery](https://github.com/Coreoz/Plume-file/tree/master/plume-file-gallery): manage medias galleries.

Check the [demo project](https://github.com/Coreoz/Plume-demo/tree/master/plume-demo-full-guice-jersey)
to see an example with these modules.

Deploying to production
-----------------------
In the default mode, when `mvn package` is executed, a zip files is generated.
This file contains all the projects jar files and startup BAT and Bash files.
These startup files will not work since they are built only for Play Framework.

If you are using `appserver` you can use `export SERVER=javazip`, it will correctly build and launch the project.
If not there are 3 solutions:
- switch back to the WAR file generation: see the [Plume War archetype](../plume-archetype-querydsl-jersey-guice),
- create a maven plugin like `play2-maven-plugin` that produce Plume compatible startup scripts (if you choose this option, please share your work :),
- replace in the `pom.xml` file the `play2-maven-plugin` plugin by
```xml
<!-- single jar executable with all dependencies -->
<plugin>
	<artifactId>maven-assembly-plugin</artifactId>
	<version>3.0.0</version>
	<configuration>
		<archive>
			<manifest>
				<mainClass>${package}.WebApplication</mainClass>
			</manifest>
		</archive>
		<descriptorRefs>
			<descriptorRef>jar-with-dependencies</descriptorRef>
		</descriptorRefs>
		<finalName>${project.artifactId}-${project.version}</finalName>
		<appendAssemblyId>false</appendAssemblyId>
	</configuration>
	<executions>
		<execution>
			<id>make-assembly</id>
			<phase>package</phase>
			<goals>
				<goal>single</goal>
			</goals>
		</execution>
	</executions>
</plugin>
```
With this solution, `mvn package` will produce an executable jar file.
Note that this last solution may produce side effects: service loader files and other files 
that share the same name can be overriden.
However all Plume modules will work as expected with this solution.

