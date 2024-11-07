package ${package}.webservices.internal;

import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

import com.codahale.metrics.Metric;
import com.coreoz.plume.jersey.grizzly.GrizzlyThreadPoolProbe;
import com.coreoz.plume.jersey.monitoring.utils.health.HealthCheckBuilder;
import com.coreoz.plume.jersey.monitoring.utils.health.beans.HealthStatus;
import com.coreoz.plume.jersey.monitoring.utils.info.ApplicationInfoProvider;
import com.coreoz.plume.jersey.monitoring.utils.info.beans.ApplicationInfo;
import com.coreoz.plume.jersey.monitoring.utils.metrics.MetricsCheckBuilder;
import com.coreoz.plume.jersey.security.basic.BasicAuthenticator;
import com.coreoz.plume.jersey.security.permission.PublicApi;

@Path("/monitoring")
// Authentication is done directly by the web service without any annotation
@PublicApi
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class MonitoringWs {
    private final ApplicationInfo applicationInfo;
    private final Provider<HealthStatus> healthStatus;
    private final Provider<Map<String, Metric>> metrics;

    private final BasicAuthenticator<String> basicAuthenticator;

    @Inject
    public MonitoringWs(
        ApplicationInfoProvider applicationInfoProvider,
        // TransactionManager transactionManager,
        // HikariDataSource hikariDataSource,
        GrizzlyThreadPoolProbe grizzlyThreadPoolProbe,
        InternalApiAuthenticator apiAuthenticator
    ) {
        this.applicationInfo = applicationInfoProvider.get();
        // Registering health checks
        this.healthStatus = new HealthCheckBuilder()
            // .registerDatabaseHealthCheck(transactionManager)
            .build();

        // Registering metrics to monitor
        this.metrics = new MetricsCheckBuilder()
            .registerJvmMetrics()
            .registerGrizzlyMetrics(grizzlyThreadPoolProbe)
            // .registerHikariMetrics(hikariDataSource)
            .build();

        // Require authentication to access monitoring endpoints
        this.basicAuthenticator = apiAuthenticator.get();
    }

    @GET
    @Path("/info")
    public ApplicationInfo info(@Context ContainerRequestContext requestContext) {
        basicAuthenticator.requireAuthentication(requestContext);
        return this.applicationInfo;
    }

    @GET
    @Path("/health")
    public HealthStatus health(@Context ContainerRequestContext requestContext) {
        basicAuthenticator.requireAuthentication(requestContext);
        return this.healthStatus.get();
    }

    @GET
    @Path("/metrics")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Metric> metrics(@Context ContainerRequestContext requestContext) {
        basicAuthenticator.requireAuthentication(requestContext);
        return metrics.get();
    }
}
