package ${package}.db;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javax.sql.DataSource;

/**
 * Initialize the database with SQL scripts placed in src/main/resources/db/migration
 */
@Singleton
public class DatabaseInitializer {

	private final DataSource dataSource;

	@Inject
	public DatabaseInitializer(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setup() {
        /*
		Flyway
			.configure()
			.dataSource(dataSource)
			.outOfOrder(true)
			.load()
            // use repair() when working locally on a migration file
            // => this enables Flyway to accept the changes made in the migration file
			.migrate();
         */
	}
}
