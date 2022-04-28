${artifactId}
=============

Welcome to your [Plume](https://github.com/Coreoz/Plume) project!
Here are some reminders to get your project up and running.

Configuring your IDE
--------------------
Install [Lombok](https://projectlombok.org/): http://jnb.ociweb.com/jnb/jnbJan2010.html#installation

Launching the server
--------------------
Configure and run Tomcat or any other servlet container with the application.
Remember to **remove the servlet context path**, it must be set to `/`.

You can then open your browser to <http://localhost:8080>.

Configuration
-------------
The configuration file is located in `src/main/resources/application.conf`.
If you have any doubt, check out the [configuration documentation](https://github.com/Coreoz/Plume/tree/master/plume-conf). 

Database
--------
To connect to a database, the Plume Querydsl module must be configured:
1. Setup the database connector in the `pom.xml` file (look for the "PUT YOUR DATABASE CONNECTOR HERE" comment
2. Setup the database connection parameters in the `application.conf` file,
see the [Plume Querydsl documentation](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl#configuration) for details
3. Add the Plume Querydsl module in the Guice configuration class `ApplicationModule`
by uncommenting the line `install(new GuiceQuerydslModule());`

To generate classes corresponding to the database tables,
you can run the `${package}.db.QuerydslGenerator.main()` method.
Before the first run, do not forget to configure
the `TABLES_PREFIX` constant in `QuerydslGenerator`, to match your tables prefix.
For example, if your tables are named `abc_film` and `abc_actor`, then your prefix will be `abc_`.

See the detailed documentations:
- [Plume Database](https://github.com/Coreoz/Plume/tree/master/plume-db)
- [Plume Querydsl](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl)
- [Plume Querydsl codegen](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl-codegen)

Removing Qurerydsl
------------------
To completly get rid of the database component:
1. Remove the line `install(new GuiceQuerydslModule());` in the `ApplicationModule` class,
2. Remove the package `db` in the project,
3. Remove the dependencies `plume-db-querydsl` and `plume-db-querydsl-codegen` in the `pom.xml` file.

Swagger
-------
Swagger is pre-configured to provide documentation about the project web-services.
This documentation is protected by credentials that should be configured in the `application.conf` file.

To access this documentation, start the project
and go to <http://localhost:8080/webjars/swagger-ui/4.1.2/index.html?url=/api/swagger>.
As a reminder, the default Swagger credentials are: `swagger//password`.

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
Run `mvn package` to produce a WAR file.

