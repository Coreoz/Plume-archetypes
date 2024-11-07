package ${package}.jersey;

import java.io.IOException;
import java.net.URI;

import jakarta.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.coreoz.plume.jersey.grizzly.GrizzlyErrorPageHandler;
import com.coreoz.plume.jersey.grizzly.GrizzlyThreadPoolProbe;

/**
 * Configure and start a Grizzly server
 */
public class GrizzlySetup {

	private static final int DEFAULT_HTTP_PORT = 8080;
	private static final String DEFAULT_HTTP_HOST = "0.0.0.0";

	public static HttpServer start(ResourceConfig jerseyResourceConfig, GrizzlyThreadPoolProbe grizzlyThreadPoolProbe, String httpPort, String httpHost, Integer httpGrizzlyWorkerThreadsPoolSize)
			throws IOException {
		// replace JUL logger (used by Grizzly) by SLF4J logger
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		int httpPortToUse = httpPort == null ? DEFAULT_HTTP_PORT : Integer.parseInt(httpPort);
		String httpHostToUse = httpHost == null ? DEFAULT_HTTP_HOST : httpHost;

		// create the server
		URI baseUri = UriBuilder.fromUri("http://"+httpHostToUse+"/api").port(httpPortToUse).build();
		HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(
			baseUri,
			jerseyResourceConfig,
			// the server have to be started after the configuration is complete,
			// else the custom error page generator won't be used
			false
		);

        // thread pool information
        httpServer.getServerConfiguration().getMonitoringConfig().getThreadPoolConfig().addProbes(grizzlyThreadPoolProbe);

        // worker thread pool configuration
        if (httpGrizzlyWorkerThreadsPoolSize != null) {
            httpServer.getListeners().forEach(networkListener -> networkListener.getTransport().getWorkerThreadPoolConfig().setMaxPoolSize(httpGrizzlyWorkerThreadsPoolSize));
        }

		// minimal error page to avoid leaking server information
		httpServer.getServerConfiguration().setDefaultErrorPageGenerator(new GrizzlyErrorPageHandler());

		// webjars for swagger ui
		CLStaticHttpHandler webJarHandler = new CLStaticHttpHandler(
			GrizzlySetup.class.getClassLoader(),
			"META-INF/resources/webjars/"
		);
		webJarHandler.setFileCacheEnabled(false);
		httpServer.getServerConfiguration().addHttpHandler(webJarHandler, "/webjars/");

		// static resources
		CLStaticHttpHandler httpHandler = new CLStaticHttpHandler(
			GrizzlySetup.class.getClassLoader(),
			"/statics/"
		);
		// enable to view changes on a dev environnement when the project is run in debug mode ;
		// in production, statics assets should be served by apache or nginx
		httpHandler.setFileCacheEnabled(false);
		httpServer.getServerConfiguration().addHttpHandler(httpHandler);

		httpServer.start();

		return httpServer;
	}

}
