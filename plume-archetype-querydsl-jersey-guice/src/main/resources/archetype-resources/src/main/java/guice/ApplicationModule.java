package ${package}.guice;

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
		// database & Querydsl installation
		//install(new GuiceQuerydslModule());
	}

}
