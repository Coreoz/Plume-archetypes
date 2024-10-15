package ${package}.webservices.internal;

import java.util.List;
import java.util.Set;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import com.coreoz.plume.jersey.security.basic.BasicAuthenticator;
import com.coreoz.plume.jersey.security.permission.PublicApi;

import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

import lombok.SneakyThrows;

@Path("/swagger")
@Singleton
@PublicApi
public class SwaggerWs {
	private final String swaggerDefinition;
	private final BasicAuthenticator<String> basicAuthenticator;

    @SneakyThrows
	@Inject
	public SwaggerWs(InternalApiAuthenticator apiAuthenticator) {
		// Basic configuration
		SwaggerConfiguration openApiConfig = new SwaggerConfiguration()
			.resourcePackages(Set.of("${package}.webservices.api"))
			.sortOutput(true)
			.openAPI(new OpenAPI().servers(List.of(
				new Server()
					.url("/api")
					.description("API ${artifactId}")
			)));

		// Generation of the OpenApi object
		OpenApiContext context = new JaxrsOpenApiContextBuilder<>()
			.openApiConfiguration(openApiConfig)
			.buildContext(true);
		// the OpenAPI object can be changed to add security definition
		// or to alter the generated mapping
		OpenAPI openApi = context.read();

		// serialization of the Swagger definition
		this.swaggerDefinition = Yaml.mapper().writeValueAsString(openApi);

		// require authentication to access the API documentation
		this.basicAuthenticator = apiAuthenticator.get();
	}

	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public String get(@Context ContainerRequestContext requestContext) {
		basicAuthenticator.requireAuthentication(requestContext);

		return swaggerDefinition;
	}
}
