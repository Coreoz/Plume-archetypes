Plume Maven archetypes
======================

This repository reference Maven archetypes for [Plume Framework](https://github.com/Coreoz/Plume).


Archetypes
----------

- [Querydsl with Jersey, Guice and Grizzly](plume-archetype-querydsl-jersey-guice-grizzly/)
- [Querydsl with Jersey and Guice](plume-archetype-querydsl-jersey-guice/)
- [Hibernate with Jersey and Guice](plume-archetype-hibernate-jersey-guice/)

Getting started
---------------

There are many options you can choose when using Plume Framework.
If you do not have time to review these options, just choose
[Querydsl/Jersey/Guice/Grizzly](plume-archetype-querydsl-jersey-guice-grizzly/),
it is the more common option.

Packages with Jersey include a [Swagger](http://swagger.io/) endpoint,
`your_application_package.webservices.internal.SwaggerWs`,
that will help expose your API documentation.
Check [Swagger Annotation documentation](https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X)
to see how you should annotate your web-services classes to produce Swagger documentation.
