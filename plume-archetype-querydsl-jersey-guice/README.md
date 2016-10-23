Plume Maven archetype with Querydsl, Jersey and Guice
=====================================================

Configuring your IDE
--------------------
1. Install [Lombok](https://projectlombok.org/): http://jnb.ociweb.com/jnb/jnbJan2010.html#installation

Getting started
---------------
1. Create a project with the
[Maven](https://maven.apache.org/guides/introduction/introduction-to-archetypes.html) archetype,
execute the command:
`mvn archetype:generate -DarchetypeGroupId=com.coreoz -DarchetypeArtifactId=plume-archetype-querydsl-jersey-guice -DarchetypeVersion=1.0.0-SNAPSHOT`
2. Setup your database connector in the `pom.xml` file (look for the "PUT YOUR DATABASE CONNECTOR HERE" comment :)
3. Setup your database connection parameters in the `application.conf` file.
If you are using MySQL you should uncomment all the line beginning with `db`,
else uncomment them anyway and use the links provided in the configuration file
to replace the MySQL configuration to your database need.
4. While you are editing the `application.conf` file, change the Swagger credentials
5. Generate the Querydsl classes corresponding to your database.
You should only need to specify your tables prefix in the `QuerydslGenerator` class in your project.
Then you can launch the `QuerydslGenerator.main()` method inside your IDE.
This will generate the package `your_project_package.db.generated`.
About the tables prefix, if you are wondering, you should really use one,
it will help you target your project tables in your database.
Indeed, if you are using
[Plume Admin](https://github.com/Coreoz/Plume-admin)
or [Plume File](https://github.com/Coreoz/Plume-file),
you will need to create `plm_` prefixed tables.
So in this case prefixing your tables will save you some pain :).
6. Run the project on Tomcat or any servlet container you like.
If you are willing to use Tomcat, use either the latest version available or a version that support Java 8.
You should also configure Tomcat to remove the context path on your application.
If you don't remove the context path, you will need to update the end of swagger URL provided
in the `index.html` file to add the context path.
7. Go to <http://localhost:8080>