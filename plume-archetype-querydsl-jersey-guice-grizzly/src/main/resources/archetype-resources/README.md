${artifactId}
=============

Welcome to your [Plume](https://github.com/Coreoz/Plume) project!
Here are some reminders to get your project up and running.

If you just created the project, you should have a look at the [finalizing project creation](https://github.com/Coreoz/Plume-archetypes/blob/master/plume-archetype-querydsl-jersey-guice-grizzly#finalizing-project-creation) section in the project Maven Archetype.

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
On Intellij the Maven `querydsl-code-generation` must be used for that, so these steps must be folowed in Intellij:
1. Click on the Maven pane on the right
2. In the Profiles block, select the `querydsl-code-generation` profile
3. Update the Maven project
4. Run the `${package}.db.QuerydslGenerator.main()` method

See the detailed documentations:
- [Plume Database](https://github.com/Coreoz/Plume/tree/master/plume-db)
- [Plume Querydsl](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl)
- [Plume Querydsl codegen](https://github.com/Coreoz/Plume/tree/master/plume-db-querydsl-codegen)

Documentation about initial database configuration is available in the [Plume Maven Archetype](https://github.com/Coreoz/Plume-archetypes/blob/master/plume-archetype-querydsl-jersey-guice-grizzly#database-configuration).

Configuring CI
--------------
- Gitlab CI configuration is made in the `.gitlab-ci.yml` file
- Sonar configuration is made in the `sonar-project.properties` file
- Github CI configuration is made in the `.github` folder

Swagger / OpenAPI
-----------------
Swagger is pre-configured to provide documentation about the project web-services.
This documentation is protected by Basic access authentication. This is configured in the `application.conf` file
in the `internal-api.auth-username` and `internal-api.auth-password` keys.

To access this documentation, start the project
and go to <http://localhost:8080/webjars/swagger-ui/4.1.2/index.html?url=/api/swagger>.

Deploying to production
-----------------------
1. Execute `mvn package`
2. Run `java -cp "target/dist/${artifactId}/lib/*" ${package}.WebApplication`. This can be adapted if the jar files are copied elsewhere.

If `appserver` is used, then this is automated with the server configured to `export SERVER=javazip`.

For further details on `WAR` packaging, see the [Plume Grizzly archetype](https://github.com/Coreoz/Plume-archetypes/tree/master/plume-archetype-querydsl-jersey-guice-grizzly).

Monitoring application
----------------------
Monitoring is available through these endpoints:
- `/monitoring/info`: provides information about the application name and version. Additionnal information can be added by providing an object for the configuration key `plm-web-jersey-info`. For example: `plm-web-jersey-info = { info1 = "value 1", info2 = "value 2" }`
- `/monitoring/health`: provides information about application health
- `/monitoring/metrics`: provides JVM and custom metrics info. This can be customized in the `MonitoringWs` class where the monitoring endpoints are configured

So by default, when running on localhost, metrics are available on: <http://localhost:8080/api/monitoring/metrics>

These endpoints are protected by Basic access authentication. This is configured in the `application.conf` file
in the `internal-api.auth-username` and `internal-api.auth-password` keys.

These endpoints should be compatible with [Spring Actuators endpoints](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints).

More modules
------------
- [Plume Mail](https://github.com/Coreoz/Plume/tree/master/plume-mail): send mails,
- [Plume Scheduler](https://github.com/Coreoz/Plume/tree/master/plume-scheduler): schedule recurring Java jobs/tasks,
- [Plume Admin](https://github.com/Coreoz/Plume-admin): web-service with a security layer to create an admin area,
- [Plume File](https://github.com/Coreoz/Plume-file): manage and serve files,
- [Plume frontend with React](https://github.com/Coreoz/create-plume-react-project): a frontend JS CLI to create admin or basic front React/TS application that integrate perfectly with a Plume backend (and not only!)

Check the [showcase project](https://github.com/Coreoz/Plume-showcase)
to see an example with these modules.
