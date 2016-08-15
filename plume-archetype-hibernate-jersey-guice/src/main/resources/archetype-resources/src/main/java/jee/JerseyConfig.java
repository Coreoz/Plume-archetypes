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
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import com.coreoz.plume.jersey.errors.WsResultExceptionMapper;
import com.coreoz.plume.jersey.java8.TimeParamProvider;

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
		// to debug web-service requests
		// register(LoggingFilter.class);
		
		// java 8
		register(TimeParamProvider.class);

		// jackson mapper configuration
		JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(Factory.injector().getInstance(ObjectMapper.class));
		register(provider);

		// Guice configuration to use it instead of HK2 to create instances of web-services
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
		GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
		guiceBridge.bridgeGuiceInjector(Factory.injector());
		
		logger.info("Jersey has been initialized");
	}

}