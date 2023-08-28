package ${package}.webservices.internal;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.coreoz.plume.jersey.security.basic.BasicAuthenticator;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

@Path("/swagger")
@Singleton
@PublicApi
public class SwaggerWs {
	private final String swaggerDefinition;
	private final BasicAuthenticator<String> basicAuthenticator;

	@Inject
	public SwaggerWs(InternalApiAuthenticator apiAuthenticator) throws Exception {
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
	public String get(@Context ContainerRequestContext requestContext) throws JsonProcessingException {
		basicAuthenticator.requireAuthentication(requestContext);

		return swaggerDefinition;
	}
}
