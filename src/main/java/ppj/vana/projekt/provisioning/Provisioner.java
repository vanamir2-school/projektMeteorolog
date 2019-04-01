package ppj.vana.projekt.provisioning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.List;

public class Provisioner {

    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    public void testDB(){
        List<String> allTables  = namedParameterJdbcOperations.getJdbcOperations().queryForList("SELECT TABLE_NAME FROM  INFORMATION_SCHEMA.TABLES", String.class);
        System.out.println( allTables );

    }
}
