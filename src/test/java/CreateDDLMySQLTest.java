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
        // name is "MySQL"
        assertEquals("MySQL", productName);
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

    @Test(expected = NumberFormatException.class)
    public void testEdgeTableConstructorNumberFormatException() {
        // This test expects a NumberFormatException to be thrown
        
        // Construct a mock input string that causes the NumberFormatException
        String mockInputString = "NotAnInteger";

        // Create the EdgeTable object, which should throw a NumberFormatException
        EdgeTable edgeTable = new EdgeTable(mockInputString);
    }

    @Test
    public void testCreateDDL() {
        // table is has table1 and table2 as fields
        EdgeTable[] tables = {new EdgeTable("1|Table1"), new EdgeTable("2|Table2")};
        EdgeField[] fields = {new EdgeField("Field1"), new EdgeField("Field2")}; 

        // create DDL object with tables and fields
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL(tables, fields);

        // create DDL
        ddlCreator.createDDL();

        // getSQLString from DDL object
        String sqlString = ddlCreator.getSQLString();

        // assert not null
        assertNotNull(sqlString);

        // assertTrue if DDL object.getSQLString contains "CREATE DATABASE"
        assertTrue(sqlString.contains("CREATE DATABASE"));

        // assertTrue if DDL object.getSQLString contains "USE"
        assertTrue(sqlString.contains("USE"));

        // assertTrue if DDL object.getSQLString contains "CREATE TABLE"
        assertTrue(sqlString.contains("CREATE TABLE"));

        // assertTrue if DDL object.getSQLString contains table names
        assertTrue(sqlString.contains("Table1"));
        assertTrue(sqlString.contains("Table2"));

        // assertTrue if DDL object.getSQLString contains field names
        assertTrue(sqlString.contains("Field1"));
        assertTrue(sqlString.contains("Field2"));

        // assertTrue if DDL object.getSQLString contains data types
        assertTrue(sqlString.contains("VARCHAR"));
        assertTrue(sqlString.contains("BOOL"));
        assertTrue(sqlString.contains("INT"));
        assertTrue(sqlString.contains("DOUBLE"));

        // assertTrue if DDL object.getSQLString contains constraints
        assertTrue(sqlString.contains("PRIMARY KEY"));
        assertTrue(sqlString.contains("FOREIGN KEY"));
    }
    @Test
    public void testCreateDDLWithNoTables() {
        // No tables provided
        EdgeTable[] tables = {};
        EdgeField[] fields = {}; 

        // create DDL object with no tables and fields
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL(tables, fields);

        // create DDL
        ddlCreator.createDDL();

        // getSQLString from DDL object
        String sqlString = ddlCreator.getSQLString();

        // assert not null
        assertNotNull(sqlString);

        // assertTrue if DDL object.getSQLString contains "CREATE DATABASE"
        assertTrue(sqlString.contains("CREATE DATABASE"));

        // assertTrue if DDL object.getSQLString contains "USE"
        assertTrue(sqlString.contains("USE"));

        // assertFalse if DDL object.getSQLString contains "CREATE TABLE" since no tables are provided
        assertFalse(sqlString.contains("CREATE TABLE"));
    }

    @Test
    public void testCreateDDLWithNoFields() {
        // tables with no fields
        EdgeTable[] tables = {new EdgeTable("Table1"), new EdgeTable("Table2")};
        EdgeField[] fields = {}; 

        // create DDL object with tables but no fields
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL(tables, fields);

        // create DDL
        ddlCreator.createDDL();

        // getSQLString from DDL object
        String sqlString = ddlCreator.getSQLString();

        // assert not null
        assertNotNull(sqlString);

        // assertTrue if DDL object.getSQLString contains "CREATE DATABASE"
        assertTrue(sqlString.contains("CREATE DATABASE"));

        // assertTrue if DDL object.getSQLString contains "USE"
        assertTrue(sqlString.contains("USE"));

        // assertFalse if DDL object.getSQLString contains "CREATE TABLE" since no fields are provided
        assertFalse(sqlString.contains("CREATE TABLE"));
    }

    @Test
    public void testCreateDDLWithNoPrimaryKey() {
        // table has no primary key
        EdgeTable[] tables = {new EdgeTable("Table1")};
        EdgeField[] fields = {new EdgeField("Field1"), new EdgeField("Field2")}; 

        // create DDL object with table but no primary key
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL(tables, fields);

        // create DDL
        ddlCreator.createDDL();

        // getSQLString from DDL object
        String sqlString = ddlCreator.getSQLString();

        // assert not null
        assertNotNull(sqlString);

        // assertTrue if DDL object.getSQLString contains "CREATE TABLE"
        assertTrue(sqlString.contains("CREATE TABLE"));

        // assertFalse if DDL object.getSQLString contains "PRIMARY KEY" since no primary key is set
        assertFalse(sqlString.contains("PRIMARY KEY"));
    }

    @Test
    public void testCreateDDLWithForeignKey() {
        // table has foreign key
        EdgeTable[] tables = {new EdgeTable("Table1"), new EdgeTable("Table2")};
        EdgeField[] fields = {new EdgeField("Field1"), new EdgeField("Field2")}; 

        // set relationship between fields
        fields[0].setFieldBound(1);
        fields[1].setTableBound(1);

        // create DDL object with table and foreign key
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL(tables, fields);

        // create DDL
        ddlCreator.createDDL();

        // getSQLString from DDL object
        String sqlString = ddlCreator.getSQLString();

        // assert not null
        assertNotNull(sqlString);

        // assertTrue if DDL object.getSQLString contains "CREATE TABLE"
        assertTrue(sqlString.contains("CREATE TABLE"));

        // assertTrue if DDL object.getSQLString contains "FOREIGN KEY" since a foreign key is set
        assertTrue(sqlString.contains("FOREIGN KEY"));
    }
    @Test
    public void testGetSQLStringWithMultipleTables() {
        // tables with multiple fields
        EdgeTable[] tables = {
            new EdgeTable("Table1"),
            new EdgeTable("Table2")
        };

        // fields for each table
        EdgeField[] fields = {
            new EdgeField("Field1"),
            new EdgeField("Field2"),
            new EdgeField("Field3"),
            new EdgeField("Field4"),
            new EdgeField("Field5")
        }; 

        // setting field bounds for relationships
        fields[0].setFieldBound(4);
        fields[2].setFieldBound(1);

        // create DDL object with tables and fields
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL(tables, fields);

        // create DDL
        ddlCreator.createDDL();

        // getSQLString from DDL object
        String sqlString = ddlCreator.getSQLString();

        // assert not null
        assertNotNull(sqlString);

        // assertTrue if DDL object.getSQLString contains "CREATE DATABASE"
        assertTrue(sqlString.contains("CREATE DATABASE"));

        // assertTrue if DDL object.getSQLString contains "USE"
        assertTrue(sqlString.contains("USE"));

        // assertTrue if DDL object.getSQLString contains "CREATE TABLE" for each table
        assertTrue(sqlString.contains("CREATE TABLE Table1"));
        assertTrue(sqlString.contains("CREATE TABLE Table2"));

        // assertTrue if DDL object.getSQLString contains field names for each table
        assertTrue(sqlString.contains("Field1"));
        assertTrue(sqlString.contains("Field2"));
        assertTrue(sqlString.contains("Field3"));
        assertTrue(sqlString.contains("Field4"));
        assertTrue(sqlString.contains("Field5"));

        // assertTrue if DDL object.getSQLString contains "FOREIGN KEY" since relationships are set
        assertTrue(sqlString.contains("FOREIGN KEY"));
    }

    @Test
    public void testGetSQLStringWithEmptyDDL() {
        // No tables and fields provided
        EdgeTable[] tables = {};
        EdgeField[] fields = {}; 

        // create DDL object with no tables and fields
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL(tables, fields);

        // create DDL
        ddlCreator.createDDL();

        // getSQLString from DDL object
        String sqlString = ddlCreator.getSQLString();

        // assert not null
        assertNotNull(sqlString);

        // assertTrue if DDL object.getSQLString contains "CREATE DATABASE"
        assertTrue(sqlString.contains("CREATE DATABASE"));

        // assertTrue if DDL object.getSQLString contains "USE"
        assertTrue(sqlString.contains("USE"));

        // assertFalse if DDL object.getSQLString contains "CREATE TABLE" since no tables are provided
        assertFalse(sqlString.contains("CREATE TABLE"));
    }

    @Test
    public void testGetSQLStringWithNoFields() {
        // tables provided but no fields
        EdgeTable[] tables = {new EdgeTable("Table1"), new EdgeTable("Table2")};
        EdgeField[] fields = {}; 

        // create DDL object with tables but no fields
        CreateDDLMySQL ddlCreator = new CreateDDLMySQL(tables, fields);

        // create DDL
        ddlCreator.createDDL();

        // getSQLString from DDL object
        String sqlString = ddlCreator.getSQLString();

        // assert not null
        assertNotNull(sqlString);

        // assertTrue if DDL object.getSQLString contains "CREATE DATABASE"
        assertTrue(sqlString.contains("CREATE DATABASE"));

        // assertTrue if DDL object.getSQLString contains "USE"
        assertTrue(sqlString.contains("USE"));

        // assertFalse if DDL object.getSQLString contains "CREATE TABLE" since no fields are provided
        assertFalse(sqlString.contains("CREATE TABLE"));
    }
}
