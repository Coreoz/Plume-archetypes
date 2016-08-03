package ${package}.webservices.internal;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Swagger;
import io.swagger.util.Json;

@Path("/swagger")
@Singleton
public class SwaggerWs {

	private final Swagger swagger;

	public SwaggerWs() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setResourcePackage("${package}.webservices.api");
		beanConfig.setBasePath("/api");
		beanConfig.setTitle("API ${artifactId}");
		beanConfig.setScan(true);

		this.swagger = beanConfig.getSwagger();
	}

	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public String get() throws JsonProcessingException {
		return Json.mapper().writeValueAsString(swagger);
	}

}
