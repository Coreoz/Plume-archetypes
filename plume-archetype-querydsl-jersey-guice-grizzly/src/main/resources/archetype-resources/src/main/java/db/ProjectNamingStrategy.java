package ${package}.db;

import java.util.Locale;

import com.querydsl.codegen.EntityType;
import com.querydsl.sql.codegen.DefaultNamingStrategy;

public class ProjectNamingStrategy extends DefaultNamingStrategy {

	@Override
	public String getClassName(String tableName) {
		// uncomment if you are using plume file
//		if("plm_file".equalsIgnoreCase(tableName)) {
//			return FileEntityQuerydsl.class.getName();
//		}
		return super.getClassName(tableName.substring(QuerydslGenerator.TABLES_PREFIX.length()));
	}

	@Override
	public String getDefaultVariableName(EntityType entityType) {
		String variableName = getClassName(entityType.getData().get("table").toString());
		return variableName.substring(0, 1).toLowerCase(Locale.ENGLISH) + variableName.substring(1);
	}

}
