Plume Maven archetype with Querydsl, Jersey, Guice and WAR packaging
====================================================================

This archetype has been removed to facilitate maintenance and because making the
[Grizzly base archetype](../plume-archetype-querydsl-jersey-guice-grizzly) function with WAR packaging is not too difficult: see below for details.

To create a new base project, follow instruction of the [Grizzly base archetype](../plume-archetype-querydsl-jersey-guice-grizzly).

## WAR migration from the Grizzly base archetype

### Changes in the `pom.xml` file
- Replace `<packaging>jar</packaging>` by `<packaging>war</packaging>`
- Delete:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>3.0.2</version>
    <configuration>
        <archive>
            <manifest>
                <mainClass>${package}.WebApplication</mainClass>
            </manifest>
        </archive>
    </configuration>
</plugin>
<!-- build a zip with all the dependencies and starting scripts -->
<plugin>
    <groupId>com.google.code.play2-maven-plugin</groupId>
    <artifactId>play2-maven-plugin</artifactId>
    <version>1.0.0-rc5</version>
    <executions>
        <execution>
            <goals>
                <goal>dist-exploded</goal>
            </goals>
            <phase>package</phase>
        </execution>
    </executions>
</plugin>
```

### Add the new `Factory.java` class
In the package `guice`, add the `Factory` class:
```java
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

/**
 * The global static instance of the application context
 */
public class Factory {
    private final static Injector injector = Guice.createInjector(
        Stage.PRODUCTION,
        new ApplicationModule()
    );

    public final static Injector injector() {
        return injector;
    }
}
```

### Delete Jersey configuration in Guice configuration class `ApplicationModule.java`
Delete these lines and the used imports:
```java
// Prepare Jersey configuration
bind(ResourceConfig.class).toProvider(JerseyConfigProvider.class);
```

### Update `JerseyConfigProvider.java`
- Extend `ResourceConfig` instead of implementing `Provider<ResourceConfig>` 
- Remove the `get()` method
- Remove the creation of the object `ResourceConfig`
- Remove all reference to the `config` field and the field itself
- In the constructor, replace `ObjectMapper objectMapper` by `ServiceLocator serviceLocator`
- Replace the line `jacksonProvider.setMapper(objectMapper);` by `jacksonProvider.setMapper(Factory.injector().getInstance(ObjectMapper.class));`
- Optionally, add a logger at the end: `logger.info("Jersey has been initialized");`
- Configure Guice bridge by adding bellow Jackson configuration:
```java
// Guice configuration to use it instead of HK2 to create instances of web-services
GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
guiceBridge.bridgeGuiceInjector(Factory.injector());
```

At the end, the file should look like:
```java
import javax.inject.Inject;
import javax.inject.Singleton;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.coreoz.guice.Factory;
import com.coreoz.plume.jersey.errors.WsJacksonJsonProvider;
import com.coreoz.plume.jersey.errors.WsResultExceptionMapper;
import com.coreoz.plume.jersey.java8.TimeParamProvider;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import com.coreoz.plume.jersey.security.permission.RequireExplicitAccessControlFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Jersey configuration
 */
@Slf4j
@Singleton
public class JerseyConfigProvider extends ResourceConfig {
	@Inject
	public JerseyConfigProvider(ServiceLocator serviceLocator) {
		// this package will be scanned by Jersey to discover web-service classes
		packages("com.coreoz.webservices");

		// filters configuration
		// handle errors and exceptions
		register(WsResultExceptionMapper.class);
		// require explicit access control on API
		register(RequireExplicitAccessControlFeature.accessControlAnnotations(PublicApi.class));
		// to debug web-service requests
		// register(LoggingFeature.class);

		// java 8
		register(TimeParamProvider.class);

		// WADL is like swagger for jersey
		// by default it should be disabled to prevent leaking API documentation
		property("jersey.server.wadl.disableWadl", true);

		// Disable automatique relative location URI resolution
		// By default it transform a relative location to an absolute location
		property("jersey.server.headers.location.relative.resolution.disabled", true);

		// jackson mapper configuration
		WsJacksonJsonProvider jacksonProvider = new WsJacksonJsonProvider();
		jacksonProvider.setMapper(Factory.injector().getInstance(ObjectMapper.class));
		register(jacksonProvider);

		// Guice configuration to use it instead of HK2 to create instances of web-services
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
		GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
		guiceBridge.bridgeGuiceInjector(Factory.injector());

		logger.info("Jersey has been initialized");
	}
}
```

### Create the `webapp` and `WEB-INF` folders
The hierarchy should be: `src/main/webapp/WEB-INF`.

### Move the `index.html` file
From `src/main/resources/statics/index.htm` to `src/main/webapp/index.htm`.

The folder `src/main/resources/statics` can then be deleted.

### Add the `web.xml` file
In the folder `src/main/webapp/WEB-INF` by replacing the correct values:
- For the display name, e.g. `<display-name>my-project</display-name>`
- For the project package to configure Jersey, e.g. `<param-value>com.company.jee.JerseyConfig</param-value>`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
	version="3.0">
	<display-name>${artifactId}</display-name>

	<!-- to make sure IE run in the latest version available -->
	<filter>
		<filter-name>ie-edge</filter-name>
		<filter-class>com.coreoz.plume.jersey.jee.IeFilter</filter-class>
		<async-supported>true</async-supported>
	</filter>
	<filter-mapping>
		<filter-name>ie-edge</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>

	<!-- Jersey -->
	<servlet>
		<servlet-name>Jersey Servlet</servlet-name>
		<servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
		<init-param>
			<param-name>javax.ws.rs.Application</param-name>
			<param-value>${package}.jee.JerseyConfig</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
		<async-supported>true</async-supported>
	</servlet>
	<servlet-mapping>
		<servlet-name>Jersey Servlet</servlet-name>
		<url-pattern>/api/*</url-pattern> <!-- webservices endpoint -->
	</servlet-mapping>

	<!-- to make sure the servlet container does not print its version -->
	<servlet>
		<servlet-name>Error Servlet</servlet-name>
		<servlet-class>com.coreoz.plume.jersey.jee.ErrorServlet</servlet-class>
		<async-supported>true</async-supported>
	</servlet>
	<servlet-mapping>
		<servlet-name>Error Servlet</servlet-name>
		<url-pattern>/error</url-pattern>
	</servlet-mapping>
	<error-page>
		<location>/error</location>
	</error-page>

	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
	</welcome-file-list>
</web-app>
```

### Delete the `WebApplication.java` file
