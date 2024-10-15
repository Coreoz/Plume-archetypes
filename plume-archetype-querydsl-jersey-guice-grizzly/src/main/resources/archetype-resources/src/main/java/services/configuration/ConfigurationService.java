package ${package}.services.configuration;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.typesafe.config.Config;

@Singleton
public class ConfigurationService {
	private final Config config;

	@Inject
	public ConfigurationService(Config config) {
		this.config = config;
	}

	public String hello() {
		return config.getString("hello");
	}

	public String internalApiAuthUsername() {
		return config.getString("internal-api.auth-username");
	}

	public String internalApiAuthPassword() {
		return config.getString("internal-api.auth-password");
	}

    public Integer httpGrizzlyWorkerThreadsPoolSize() {
        if (!config.hasPath("http-grizzly.worker-threads-pool-size")) {
            return null;
        }
        return config.getInt("http-grizzly.worker-threads-pool-size");
    }
}
