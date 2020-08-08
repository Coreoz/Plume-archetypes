package ${package}.jee;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ${package}.guice.Factory;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.coreoz.plume.jersey.errors.WsJacksonJsonProvider;
import com.coreoz.plume.jersey.errors.WsResultExceptionMapper;
import com.coreoz.plume.jersey.java8.TimeParamProvider;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import com.coreoz.plume.jersey.security.permission.RequireExplicitAccessControlFeature;

/**
 * Jersey configuration
 */
public class JerseyConfig extends ResourceConfig {

	private static final Logger logger = LoggerFactory.getLogger(JerseyConfig.class);

	@Inject
	public JerseyConfig(ServiceLocator serviceLocator) {
		packages("${package}.webservices");

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
		property("jersey.config.server.wadl.disableWadl", true);

		// Disable automatique relative location URI resolution
		// By default it transform a relative location to an absolute location
		property("jersey.config.server.headers.location.relative.resolution.disabled", true);

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