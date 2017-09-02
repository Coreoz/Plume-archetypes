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
You can then open your browser to <http://localhost:8080>.

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
Run `mvn package` to produce a WAR file.

