package ${package}.webservices.internal;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.Metric;
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
    private final Provider<HealthStatus> healthStatusProvider;
    private final Provider<Map<String, Metric>> metricsStatusProvider;

    private final BasicAuthenticator<String> basicAuthenticator;

    @Inject
    public MonitoringWs(
        ApplicationInfoProvider applicationInfoProvider,
        // TransactionManager transactionManager,
        InternalApiAuthenticator apiAuthenticator
    ) {
        this.applicationInfo = applicationInfoProvider.get();
        // Registering health checks
        this.healthStatusProvider = new HealthCheckBuilder()
            // .registerDatabaseHealthCheck(transactionManager)
            .build();

        // Registering metrics to monitor
        this.metricsStatusProvider = new MetricsCheckBuilder()
            .registerJvmMetrics()
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
        return this.healthStatusProvider.get();
    }

    @GET
    @Path("/metrics")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Metric> metrics(@Context ContainerRequestContext requestContext) {
        basicAuthenticator.requireAuthentication(requestContext);
        return metricsStatusProvider.get();
    }
}
