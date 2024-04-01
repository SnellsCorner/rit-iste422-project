import static org.junit.Assert.*;
import org.junit.*;

public class CreateDDLMySQLTest {
    
    @Test
    public void testGetDatabaseName() {
        // set name
        String dbName = "TestDB";

        // create DDL object
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL();

        // set database name
        ddlCreator.databaseName = dbName;

        // run getDatabaseName
        String retrievedDBName = ddlCreator.getDatabaseName();

        // assert retrieved database name matches the set name
        assertEquals(dbName, retrievedDBName);
    }
    @Test
    public void testGetProductName() {
        // create DDL object
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL();

        // run getProductName
        String productName = ddlCreator.getProductName();

        // assert 
        // name is "TestDB"
        assertEquals("TestDB", productName);
    }

    @Test
    public void testConvertStrBooleanToInt() {
        // create DDL object
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL();

        // assert expected 1 if .convertStrBooleanToInt is true
        assertEquals(1, ddlCreator.convertStrBooleanToInt("true"));

        // assert expected 0 if .convertStrBooleanToInt is false
        assertEquals(0, ddlCreator.convertStrBooleanToInt("false"));
    }

    @Test
    public void testGetSQLString() {
        // table is has table1 and table2 as fields
        EdgeTable[] tables = {new EdgeTable("Table1"), new EdgeTable("Table2")};
        EdgeField[] fields = {new EdgeField("Field1"), new EdgeField("Field2")}; 

        // create DDL object with tables and feilds
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL(tables, fields);

        // getSQLString from DDL object
        String sqlString = ddlCreator.getSQLString();

        // assert not null
        assertNotNull(sqlString);

        // assertTrue if DDL object.getSQLString contains "CREATE DATABASE"
        assertTrue(sqlString.contains("CREATE DATABASE"));

        // assertTrue if DDL object.getSQLString contains "CREATE TABLE"
        assertTrue(sqlString.contains("CREATE TABLE"));
    }
}
