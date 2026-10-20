package ${package}.db;

import java.io.File;
import java.sql.SQLException;

import com.coreoz.plume.conf.guice.GuiceConfModule;
import com.coreoz.plume.db.querydsl.generation.IdBeanSerializer;
import com.coreoz.plume.db.transaction.TransactionManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.codegen.MetaDataExporter;
import com.querydsl.sql.codegen.MetadataExporterConfigImpl;
import com.querydsl.sql.types.InstantType;
import com.querydsl.sql.types.LocalDateType;
import com.querydsl.sql.types.LocalTimeType;
import com.querydsl.sql.types.Type;
import com.querydsl.sql.types.ZonedDateTimeType;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Generate Querydsl classes for the database layer.
 *
 * Run the {@link #main()} method from your IDE to regenerate Querydsl classes.
 */
@Slf4j
public class QuerydslGenerator {
	static final String TABLES_PREFIX = "plm_";

	public static void main(String... args) {
		Configuration configuration = new Configuration(SQLTemplates.DEFAULT);
		configuration.register(classType(InstantType.class));
		configuration.register(classType(LocalDateType.class));
		configuration.register(classType(LocalTimeType.class));
		configuration.register(classType(ZonedDateTimeType.class));
		configuration.registerNumeric(1, 0, Boolean.class);
		configuration.registerNumeric(9, 0, Long.class);
		configuration.registerNumeric(19, 0, Long.class);

		MetadataExporterConfigImpl exporterConfig = new MetadataExporterConfigImpl();
		exporterConfig.setPackageName("${package}.db.generated");
		exporterConfig.setTargetFolder(new File("src/main/java"));
		exporterConfig.setTableNamePattern(TABLES_PREFIX + "%");
		exporterConfig.setNamingStrategyClass(ProjectNamingStrategy.class);
		exporterConfig.setBeanSerializerClass(IdBeanSerializer.class);
		exporterConfig.setExportBeans(true);
		exporterConfig.setBeanPrintSupertype(true);
		exporterConfig.setBeanAddToString(true);
		exporterConfig.setColumnAnnotations(true);
		exporterConfig.setGeneratedAnnotationClass("jakarta.annotation.Generated");
		MetaDataExporter exporter = new MetaDataExporter(exporterConfig);
		exporter.setConfiguration(configuration);

		Injector injector = Guice.createInjector(new GuiceConfModule());
		injector.getInstance(TransactionManager.class).execute(connection -> {
			try {
				exporter.export(connection.getMetaData());
			} catch (SQLException e) {
				logger.error("Querydsl database objects generation failed", e);
			}
		});
	}

	@SneakyThrows
	private static Type<?> classType(Class<?> classType) {
		return (Type<?>) classType.getConstructor().newInstance();
	}
}
