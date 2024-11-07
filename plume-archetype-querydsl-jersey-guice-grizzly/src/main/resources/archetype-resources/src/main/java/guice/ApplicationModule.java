package ${package}.guice;

import org.glassfish.jersey.server.ResourceConfig;

import ${package}.jersey.JerseyConfigProvider;

import com.coreoz.plume.conf.guice.GuiceConfModule;
import com.coreoz.plume.jersey.monitoring.guice.GuiceJacksonWithMetricsModule;
import com.google.inject.AbstractModule;

/**
 * Group the Guice modules to install in the application
 */
public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new GuiceConfModule());
		install(new GuiceJacksonWithMetricsModule());
		// Database & Querydsl installation
		// install(new GuiceQuerydslModule());

		// Prepare Jersey configuration
		bind(ResourceConfig.class).toProvider(JerseyConfigProvider.class);
	}

}
