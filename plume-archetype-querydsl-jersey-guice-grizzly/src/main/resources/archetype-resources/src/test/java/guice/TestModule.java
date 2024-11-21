package ${package}.guice;

import com.coreoz.plume.mocks.MockedClock;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import java.time.Clock;

/**
 * The Guice module that will be used for integration tests.
 *
 * In this module, it is possible to override the behaviors of some services as it is shown with the {@link TimeProvider}
 * module.
 */
public class TestModule extends AbstractModule {
	@Override
	protected void configure() {
		install(Modules.override(new ApplicationModule()).with(new AbstractModule() {
			@Override
			protected void configure() {
				bind(Clock.class).to(MockedClock.class);
			}
		}));
        // To run database Flyway scripts before running tests
        // install(new GuiceDbTestModule());
	}
}
