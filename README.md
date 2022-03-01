Plume Maven archetypes
======================

This repository reference Maven archetypes for [Plume Framework](https://github.com/Coreoz/Plume).


Archetypes
----------

- [Querydsl with Jersey, Guice and Grizzly](plume-archetype-querydsl-jersey-guice-grizzly/): A Java application that starts with a plain static main function. It means that the packaged application is a **JAR file**.
- [Querydsl with Jersey and Guice](plume-archetype-querydsl-jersey-guice/): A Java application that starts inside an servlet container like Tomcat, WildFly etc. It means that the packaged application is a **WAR file**.

Getting started
---------------

There are many options you can choose when using Plume Framework.
If you do not have time to review the 2 archetypes above, just choose
[Querydsl/Jersey/Guice/Grizzly](plume-archetype-querydsl-jersey-guice-grizzly/) as it is the more common option.

Packages with Jersey include a [Swagger/OpenAPI](http://swagger.io/) endpoint,
`your_application_package.webservices.internal.SwaggerWs`,
that will help expose your API documentation.
Check [Swagger/OpenAPI Annotation documentation](https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Annotations)
to see how you should annotate your web-services classes to produce Swagger/OpenAPI documentation.
