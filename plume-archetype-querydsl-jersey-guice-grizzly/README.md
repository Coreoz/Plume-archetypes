Plume Maven archetype with Querydsl, Jersey, Guice and Grizzly
==============================================================

This Maven archetype will create an empty project with:
- A Grizzly webserver configured,
- Jersey webservices configured,
- Querydsl pre-configured,
- Guice to wire it up.

Configuring your IDE
--------------------
Install [Lombok](https://projectlombok.org/): http://jnb.ociweb.com/jnb/jnbJan2010.html#installation

Getting started
---------------
1. Create a project with the
[Maven archetype](https://maven.apache.org/guides/introduction/introduction-to-archetypes.html),
execute the command:
`mvn archetype:generate -DarchetypeGroupId=com.coreoz -DarchetypeArtifactId=plume-archetype-querydsl-jersey-guice-grizzly -DarchetypeVersion=1.0.0-rc3`
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
6. Run the `WebApplication` class that is in the root of your project.
7. Go to <http://localhost:8080>

Removing Qurerydsl
------------------
If you just want to see your project running without having to configure a database yet,
you can disable the Querydsl module:
comment the line `install(new GuiceQuerydslModule());` in the `ApplicationModule` class.

If you want to completly get rid of the database component:

1. Remove the line `install(new GuiceQuerydslModule());` in the `ApplicationModule` class,
2. Remove the package `db` in your project,
3. Remove the dependencies `plume-db-querydsl` and `plume-db-querydsl-codegen` in the `pom.xml` file.

