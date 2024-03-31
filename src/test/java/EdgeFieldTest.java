import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EdgeFieldTest {
    private EdgeField edgeField;

    @Before
    public void setUp() {
        // Initialize a new EdgeField object before each test
        edgeField = new EdgeField("1|Name|0|0|false|false");
    }

    @Test
    public void testToString() {
        // Test the toString() method
        assertEquals("1|Name|0|0|0|0|1|false|false|", edgeField.toString());
    }

    @Test
    public void testGetNumFigure() {
        // Test the getNumFigure() method
        assertEquals(1, edgeField.getNumFigure());
    }

    @Test
    public void testGetName() {
        // Test the getName() method
        assertEquals("Name", edgeField.getName());
    }

    @Test
    public void testSetAndGetTableID() {
        // Test the setTableID() and getTableID() methods
        edgeField.setTableID(2);
        assertEquals(2, edgeField.getTableID());
    }

    @Test
    public void testSetAndGetTableBound() {
        // Test the setTableBound() and getTableBound() methods
        edgeField.setTableBound(1);
        assertEquals(1, edgeField.getTableBound());
    }

    @Test
    public void testSetAndGetFieldBound() {
        // Test the setFieldBound() and getFieldBound() methods
        edgeField.setFieldBound(1);
        assertEquals(1, edgeField.getFieldBound());
    }

    @Test
    public void testSetAndGetDisallowNull() {
        // Test the setDisallowNull() and getDisallowNull() methods
        edgeField.setDisallowNull(true);
        assertTrue(edgeField.getDisallowNull());
    }

    @Test
    public void testSetAndGetIsPrimaryKey() {
        // Test the setIsPrimaryKey() and getIsPrimaryKey() methods
        edgeField.setIsPrimaryKey(true);
        assertTrue(edgeField.getIsPrimaryKey());
    }

    @Test
    public void testSetIsPrimaryKeyFalse() {
        // Test setting primary key without associated table ID
        edgeField.setTableID(0); // Table ID not set
        edgeField.setIsPrimaryKey(true); // Trying to set as primary key
        assertFalse(edgeField.getIsPrimaryKey()); // Should fail, primary key requires table ID
    }

    @Test
    public void testSetAndGetDefaultValue() {
        // Test the setDefaultValue() and getDefaultValue() methods
        edgeField.setDefaultValue("Default");
        assertEquals("Default", edgeField.getDefaultValue());
    }

    @Test
    public void testSetAndGetVarcharValue() {
        // Test the setVarcharValue() and getVarcharValue() methods
        edgeField.setVarcharValue(10);
        assertEquals(10, edgeField.getVarcharValue());
    }

    @Test
    public void testInvalidSetVarcharValue() {
        // Test setting an invalid VARCHAR value
        edgeField.setDataType(0); // Setting data type to VARCHAR
        edgeField.setVarcharValue(-5); // Negative value is invalid
        assertEquals(EdgeField.VARCHAR_DEFAULT_LENGTH, edgeField.getVarcharValue());
    }

    @Test
    public void testSetAndGetDataType() {
        // Test the setDataType() and getDataType() methods
        edgeField.setDataType(2);
        assertEquals(2, edgeField.getDataType());
    }



}