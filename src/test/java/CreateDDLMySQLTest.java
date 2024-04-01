import static org.junit.Assert.*;
import org.junit.*;

public class CreateDDLMySQLTest {
    
    @Test
    public void testGenerateDatabaseName() {
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL();
        String dbName = ddlCreator.generateDatabaseName();
        assertNotNull(dbName);
        assertNotEquals("", dbName);
    }
    
    @Test
    public void testConvertStrBooleanToInt() {
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL();
        assertEquals(1, ddlCreator.convertStrBooleanToInt("true"));
        assertEquals(0, ddlCreator.convertStrBooleanToInt("false"));
    }

    @Test
    public void testGetSQLString() {
        EdgeTable[] tables = {new EdgeTable("Table1"), new EdgeTable("Table2")};
        EdgeField[] fields = {new EdgeField("Field1"), new EdgeField("Field2")}; 
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL(tables, fields);
        String sqlString = ddlCreator.getSQLString();
        assertNotNull(sqlString);
        assertTrue(sqlString.contains("CREATE DATABASE"));
        assertTrue(sqlString.contains("CREATE TABLE"));
    }
}
