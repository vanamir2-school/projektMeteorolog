package ppj.vana.projekt.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.util.List;

public class SqlProvider {

    private static final Logger log = LoggerFactory.getLogger(SqlProvider.class);

    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Autowired
    private DataSource dataSource;

    public void doProvision() {
        // TODO - IT IS NOT ADAPTABLE BY CURRENT SCHEMA
        List<String> allTables = namedParameterJdbcOperations.getJdbcOperations().queryForList("SELECT TABLE_NAME FROM  INFORMATION_SCHEMA.TABLES", String.class);
        if (!allTables.contains("city") || !allTables.contains("country")) {
            log.warn("DB Provisioner: No tables exist and will be created");
            createDb();
            allTables = namedParameterJdbcOperations.getJdbcOperations().queryForList("SELECT TABLE_NAME FROM  INFORMATION_SCHEMA.TABLES", String.class);
            log.debug(allTables.toString());
        } else
            log.info("DB Provisioner: Table OFFERS exists, all existing tables: " + allTables);
    }

    public void createDb() {
        Resource createScript = new ClassPathResource("sql/createScript.sql");
        Resource insertScript = new ClassPathResource("sql/insertScript.sql");
        try {
            ScriptUtils.executeSqlScript(dataSource.getConnection(), createScript);
            ScriptUtils.executeSqlScript(dataSource.getConnection(), insertScript);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

