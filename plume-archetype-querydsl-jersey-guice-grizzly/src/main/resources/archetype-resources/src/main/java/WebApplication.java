package ${package};

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;

import ${package}.guice.ApplicationModule;
import ${package}.jersey.GrizzlySetup;
import ${package}.services.configuration.ConfigurationService;

import com.coreoz.plume.jersey.grizzly.GrizzlyThreadPoolProbe;
import com.coreoz.plume.jersey.guice.JerseyGuiceFeature;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

import lombok.extern.slf4j.Slf4j;

/**
 * The application entry point, where it all begins.
 */
@Slf4j
public class WebApplication {
	// Maximal waiting time for the last process to execute after the JVM received a kill signal
	public static final Duration GRACEFUL_SHUTDOWN_TIMEOUT = Duration.ofSeconds(60);

	public static void main(String[] args) {
		try {
			long startTimestamp = System.currentTimeMillis();

			// Initialize all application objects with Guice
			Injector injector = Guice.createInjector(Stage.PRODUCTION, new ApplicationModule());

			ResourceConfig jerseyResourceConfig = injector.getInstance(ResourceConfig.class);

			// Initialize database
			// injector.getInstance(DatabaseInitializer.class).setup();

			// Enable Jersey to create objects through Guice Injector instance
			jerseyResourceConfig.register(new JerseyGuiceFeature(injector));
			// Starts the server
			HttpServer httpServer = GrizzlySetup.start(
				jerseyResourceConfig,
                injector.getInstance(GrizzlyThreadPoolProbe.class),
				System.getProperty("http.port"),
				System.getProperty("http.address"),
                injector.getInstance(ConfigurationService.class).httpGrizzlyWorkerThreadsPoolSize()
			);

			// Add a shutdown hook to execute some code when the JVM receive a kill signal before it stops
			addShutDownListener(httpServer);
			// If Plume Scheduler / Wisp is used, uncomment next line
			// addShutDownListener(httpServer, injector.getInstance(Scheduler.class));

			logger.info("Server started in {} ms", System.currentTimeMillis() - startTimestamp);
		} catch (Throwable e) {
			logger.error("Failed to start server", e);
			// This line is important, because during initialization some libraries change the main thread type
			// to daemon, which mean that even if the project is completely down, the JVM is not stopped.
			// Stopping the JVM is important to enable production supervision tools to detect and restart the project.
			System.exit(1);
		}
	}

	private static void addShutDownListener(HttpServer httpServer) { // If scheduler is used, add arg: Scheduler scheduler
		Runtime.getRuntime().addShutdownHook(new Thread(
			() -> {
				logger.info("Stopping signal received, shutting down server and scheduler...");
				GrizzlyFuture<HttpServer> grizzlyServerShutdownFuture = httpServer.shutdown(GRACEFUL_SHUTDOWN_TIMEOUT.toSeconds(), TimeUnit.SECONDS);
                try {
                    logger.info("Waiting for server to shut down... Shutdown timeout is {} seconds", GRACEFUL_SHUTDOWN_TIMEOUT.toSeconds());
                    // If scheduler is used, uncomment next line
                    // scheduler.gracefullyShutdown(GRACEFUL_SHUTDOWN_TIMEOUT);
                    grizzlyServerShutdownFuture.get();
                    logger.info("Server and scheduler stopped.");
                } catch(Exception e) {
                    logger.error("Error while shutting down server.", e);
                    // It's not useful, but it makes Sonar happy
                    Thread.currentThread().interrupt();
                }
			},
			"shutdownHook"
		));
	}
}
