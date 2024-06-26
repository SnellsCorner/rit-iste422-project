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
        // Checks that the toString() method returns the expected string of EdgeField
        assertEquals("1|Name|0|0|0|0|1|false|false|", edgeField.toString());
    }

    @Test
    public void testGetNumFigure() {
        // Checks that the getNumFigure() method returns the correct value of numFigure
        assertEquals("Should return default 1 value for numFigure", 1, edgeField.getNumFigure());
    }

    @Test
    public void testGetName() {
        // Checks that the getName() method returns the correct name value
        assertEquals("Name", edgeField.getName());
    }

    @Test
    public void testSetAndGetTableID() {
        // Checks that the setTableID() and getTableID() methods get the same value that is set
        edgeField.setTableID(2);
        assertEquals(2, edgeField.getTableID());
    }

    @Test
    public void testSetAndGetTableBound() {
        // Checks that the methods have the same set and get value
        edgeField.setTableBound(1);
        assertEquals(1, edgeField.getTableBound());
    }

    @Test
    public void testSetAndGetFieldBound() {
        // Checks that the methods have the same set and get value
        edgeField.setFieldBound(1);
        assertEquals(1, edgeField.getFieldBound());
    }

    @Test
    public void testSetAndGetDisallowNull() {
        // Checks that the methods have the same set and get value
        edgeField.setDisallowNull(true);
        assertTrue(edgeField.getDisallowNull());
    }

    @Test
    public void testSetAndGetIsPrimaryKey() {
        // Checks that the methods have the same set and get value
        edgeField.setIsPrimaryKey(true);
        assertTrue(edgeField.getIsPrimaryKey());
    }

    @Test
    public void testSetAndGetDefaultValue() {
        // Checks that the methods have the same set and get value
        edgeField.setDefaultValue("Default");
        assertEquals("Default", edgeField.getDefaultValue());
    }

    @Test
    public void testSetAndGetVarcharValue() {
        // Checks that the methods have the same set and get value
        edgeField.setVarcharValue(10);
        assertEquals(10, edgeField.getVarcharValue());
    }

    @Test
    public void testInvalidSetVarcharValue() {
        // Checks that the setVarcharValue() method rejects negative values and keeps the default value
        edgeField.setDataType(0); 
        edgeField.setVarcharValue(-5); // Should not take in the negative value
        assertEquals(EdgeField.VARCHAR_DEFAULT_LENGTH, edgeField.getVarcharValue());
    }

    @Test
    public void testSetAndGetDataType() {
        // Checks that the methods have the same set and get value
        edgeField.setDataType(2);
        assertEquals(2, edgeField.getDataType());
    }

    @Test
    public void testSetInvalidDataType() {
        // Checks that the testSetInvalidDataType() method rejects the invalid value and keep the default value
        edgeField.setDataType(5); // datatype is out of bounds (0 to 3 are valid)
        assertEquals(0, edgeField.getDataType());
    }

    @Test
    public void testSetNegativeFieldBound() {
        // Checks that the setFieldBound() method does not allow setting a negative field bound and keeps the current value
        int originalFieldBound = edgeField.getFieldBound();
        edgeField.setFieldBound(-1); // Should not be set to the negative value
        assertNotEquals(originalFieldBound, edgeField.getFieldBound());
    }

    @Test
    public void testDefaultValueIsNull() {
        // Checks that there is nothing as the default value(nothing != null)
        assertEquals("", edgeField.getDefaultValue());
    }


    @Test
    public void testSetDefaultToNull() {
        // Chekcs that the setDefaultValue() method can set the default value to null(empty) and not just a string value
        edgeField.setDefaultValue(null);
        assertNull(edgeField.getDefaultValue());
    }

    @Test
    public void testGetStrDataType() {
        // Checks that the getStrDataType() method has the correct string data types and nothing more
        assertArrayEquals(new String[]{"Varchar", "Boolean", "Integer", "Double"}, EdgeField.getStrDataType());
    }

    @Test
    public void testDataTypeBoundary() {
        // Checks that the setDataType() method will not set max datatype value and keep the default
        edgeField.setDataType(Integer.MAX_VALUE); 
        assertEquals(0, edgeField.getDataType()); // Data type should stay the same
    }

    @Test
    public void testSetAndGetDataTypes() {
        // Checks that the setDataType() and getDataType() methods can take each data type and set it
        for (int i = 0; i < EdgeField.getStrDataType().length; i++) {
            edgeField.setDataType(i);
            assertEquals(i, edgeField.getDataType());
        }
    }

    @Test
    public void testToggleDisallowNullFlag() {
        // Ensure disallow null flag is initially false
        assertFalse(edgeField.getDisallowNull());
        
        // Toggle disallow null flag to true
        edgeField.setDisallowNull(true);
        assertTrue(edgeField.getDisallowNull());
    }

    @Test
    public void testSetAndGetTableIDBoundaryMAX() {
        //Checks if the tableID is able to go past the max value
        edgeField.setTableID(Integer.MAX_VALUE); 
        assertEquals(Integer.MAX_VALUE, edgeField.getTableID());

        int originalTableID = edgeField.getTableID();
        edgeField.setTableID(Integer.MAX_VALUE + 1);
        assertNotEquals(originalTableID, edgeField.getTableID()); // Check if the max value has been changed
    }

    @Test
    public void testSetMaxIntegerAsFieldBound() {
        // Checks if field bound can go past the max value
        edgeField.setFieldBound(Integer.MAX_VALUE);
        int original = edgeField.getFieldBound();

        edgeField.setFieldBound(Integer.MAX_VALUE + 1);
        assertNotEquals(original, edgeField.getFieldBound()); // Check if the original max value is different than current
    }

    @Test
    public void testSetNegativeVarcharValueAfterDataTypeChange() {
        // Checks if method will retain previous valid value if the previous value is invalid
        edgeField.setDataType(2);
        edgeField.setVarcharValue(10);

        int previous = edgeField.getVarcharValue();
        edgeField.setDataType(0);

        // invalid value -5
        edgeField.setVarcharValue(-5);
        assertEquals(previous, edgeField.getVarcharValue()); //value should remain the previous valid value
    }

    @Test
    public void testUpdateDefaultValueAfterSettingDataType() {
        // Set data type to Integer
        edgeField.setDataType(2);
        edgeField.setDefaultValue("100");
        
        // Change data type to Double
        edgeField.setDataType(3);
        edgeField.setDefaultValue("200");

        // Value should be able to be updated to 200 even if the data type changes
        assertEquals("200", edgeField.getDefaultValue());
    }

}