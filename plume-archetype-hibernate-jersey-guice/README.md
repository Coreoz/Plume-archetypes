Plume Maven archetype with Hibernate, Jersey and Guice
======================================================

Note that even though the Hibernate Plume package is maintained, it is not a priority project
for the Plume Framework team.
If you are starting a new project, you should consider using
the [Querydsl Plume package](../plume-archetype-querydsl-jersey-guice-grizzly) instead.

Configuring your IDE
--------------------
1. Install [Lombok](https://projectlombok.org/): http://jnb.ociweb.com/jnb/jnbJan2010.html#installation
2. If you are using Eclipse, configure it to run with the JDK (and not the JRE).
To do that, you need to change your `eclipse.ini` file according to
https://wiki.eclipse.org/Eclipse.ini#Specifying_the_JVM 

Getting started
---------------
1. Create a project with the
[Maven](https://maven.apache.org/guides/introduction/introduction-to-archetypes.html) archetype,
execute the command:
`mvn archetype:generate -DarchetypeGroupId=com.coreoz -DarchetypeArtifactId=plume-archetype-hibernate-jersey-guice -DarchetypeVersion=1.1.0`
2. Setup your database connector in the `pom.xml` file (look for the "PUT YOUR DATABASE CONNECTOR HERE" comment :)
3. Setup your database connection parameters in the `application.conf` file.
If you are using MySQL you should uncomment all the line beginning with `db`,
else uncomment them anyway and use the links provided in the configuration file
to replace the MySQL configuration to your database need.
4. While you are editing the `application.conf` file, change the Swagger credentials
5. Run the project on Tomcat or any servlet container you like.
If you are willing to use Tomcat, use either the latest version available or a version that support Java 8.
You should also configure Tomcat to remove the context path on your application.
If you don't remove the context path, you will need to update the end of swagger URL provided
in the `index.html` file to add the context path.
6. Go to <http://localhost:8080>