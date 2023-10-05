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
TODO reference the main README file and replace this section by "Finalizing project creation" <= this will need to be the first section

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
6. TODO put in archetype gitlab and github config

To generate classes corresponding to the database tables,
you can run the `${package}.db.QuerydslGenerator.main()` method.
Before the first run, do not forget to configure
the `TABLES_PREFIX` constant in `QuerydslGenerator`, to match your tables prefix.
For example, if your tables are named `abc_film` and `abc_actor`, then your prefix will be `abc_`.

See the detailed documentations:
- [Plume Database](https://github.com/Coreoz/Plume/tree/master/plume-db)
- [Plume Querydsl](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl)
- [Plume Querydsl codegen](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl-codegen)

Configuring CI
--------------
TODO reference main README file

Removing Qurerydsl
------------------
To completly get rid of the database component:
1. Remove the line `install(new GuiceQuerydslModule());` in the `ApplicationModule` class,
2. Remove the package `db` in the project,
3. Remove the dependencies `plume-db-querydsl` and `plume-db-querydsl-codegen` in the `pom.xml` file.

Swagger / OpenAPI
-----------------
Swagger is pre-configured to provide documentation about the project web-services.
This documentation is protected by credentials that are configured in the `application.conf` file.

To access this documentation, start the project
and go to <http://localhost:8080/webjars/swagger-ui/4.1.2/index.html?url=/api/swagger>.

More modules
------------
- [Plume Mail](https://github.com/Coreoz/Plume/tree/master/plume-mail): send mails,
- [Plume Scheduler](https://github.com/Coreoz/Plume/tree/master/plume-scheduler): schedule recurring Java jobs/tasks,
- [Plume Admin](https://github.com/Coreoz/Plume-admin): web-service with a security layer to create an admin area,
- [Plume File](https://github.com/Coreoz/Plume-file): manage and serve files,
- [Plume frontend with React](https://github.com/Coreoz/create-plume-react-project): a frontend JS CLI to create admin or basic front React/TS application that integrate perfectly with a Plume backend (and not only!)

Check the [showcase project](https://github.com/Coreoz/Plume-showcase)
to see an example with these modules.

Deploying to production
-----------------------
1. Execute `mvn package`
2. Run `java -cp "target/dist/${artifactId}/lib/*" ${package}.WebApplication`. This can be adapted if the jar files are copied elsewhere.

If `appserver` is used, then this is automated with the server configured to `export SERVER=javazip`.

For further detailed and `WAR` packaging, the [Plume Grizzly archetype](https://github.com/Coreoz/Plume-archetypes/tree/master/plume-archetype-querydsl-jersey-guice-grizzly) contains more information.
