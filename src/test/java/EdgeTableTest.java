import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class EdgeTableTest {
    private EdgeTable edgeTable;
    private EdgeTable edgeTable2;

    @Before
    public void setUp() {
        edgeTable = new EdgeTable("1|Table");
        edgeTable2 = new EdgeTable("2|EdgeTable");

    }

    @Test
    public void testToString() {
        edgeTable.addNativeField(2);
        edgeTable.addRelatedTable(1);
        edgeTable.makeArrays();
        String expected = "Table: 1\r\n{\r\nTableName: Table\r\nNativeFields: 2\r\nRelatedTables: 1\r\nRelatedFields: 0\r\n}\r\n";
        assertEquals(expected, edgeTable.toString());
    }

    @Test
    public void testToString2() {
        edgeTable2.addNativeField(8);
        edgeTable2.addRelatedTable(3);
        edgeTable2.makeArrays();
        String expected = "Table: 2\r\n{\r\nTableName: EdgeTable\r\nNativeFields: 8\r\nRelatedTables: 3\r\nRelatedFields: 0\r\n}\r\n";
        assertEquals(expected, edgeTable2.toString());
    }

    @Test
    public void testGetNumFigure() {
        // Checks that the getNumFigure() method returns the correct value of numFigure
        assertEquals(1, edgeTable.getNumFigure());
    }

    @Test
    public void testGetNumFigure2() {
        // Checks that the getNumFigure() method returns the correct value of numFigure
        assertEquals(2, edgeTable2.getNumFigure());
    }

    @Test
    public void testGetName() {
        // Checks that the getName() method returns the correct name value
        assertEquals("Table", edgeTable.getName());
    }

    @Test
    public void testGetName2() {
        // Checks that the getName() method returns the correct name value
        assertEquals("EdgeTable", edgeTable2.getName());
    }


    // testing the addition of related tables
    @Test
    public void testAddRelatedTable(){
        EdgeTable edgeTable = new EdgeTable();
        try {
            edgeTable.addRelatedTable(4);
            edgeTable.addRelatedTable(7);
            assertArrayEquals(new int[]{4,7}, edgeTable.getRelatedTablesArray());
        } catch (Exception e) {
            System.out.println("Error " + e);
            return; 
        } 
    }

    @Test
    public void testAddRelatedTable2(){
        EdgeTable edgeTable = new EdgeTable();
        try {
            edgeTable.addRelatedTable(9);
            edgeTable.addRelatedTable(30);
            assertArrayEquals(new int[]{9,30}, edgeTable.getRelatedTablesArray());
        } catch (Exception e) {
            System.out.println("Error " + e);
            return; 
        } 
    }

    // testing the addition of native fields
    @Test
    public void testAddNativeField(){
        EdgeTable edgeTable = new EdgeTable();
        try {
            edgeTable.addNativeField(2);
            edgeTable.addNativeField(9);
            edgeTable.addNativeField(6);
            assertArrayEquals(new int[]{2,9,6}, edgeTable.getNativeFieldsArray());
        } catch (Exception e) {
            System.out.println("Error " + e);
            return; 
        } 

    }

    @Test
    public void testAddNativeField2(){
        EdgeTable edgeTable = new EdgeTable();
        try {
            edgeTable.addNativeField(3);
            edgeTable.addNativeField(2);
            edgeTable.addNativeField(1);
            assertArrayEquals(new int[]{3,2,1}, edgeTable.getNativeFieldsArray());
        } catch (Exception e) {
            System.out.println("Error " + e);
            return; 
        } 

    }

    // testing if setting the related field works
    @Test
    public void testSetRelatedField(){
        EdgeTable edgeTable = new EdgeTable();
        try {
           edgeTable.addRelatedTable(3);
            edgeTable.makeArrays();
            edgeTable.setRelatedField(0, 4);
            assertArrayEquals(new int[]{4}, edgeTable.getRelatedFieldsArray());
            edgeTable.setRelatedField(1, 5);
            assertArrayEquals(new int[]{4,5}, edgeTable.getRelatedFieldsArray()); 
        } catch (Exception e) {
            System.out.println("Error " + e);
            return; 
        } 
        
    }

    // testing the move of native field up at given index
    @Test
    public void testMoveFieldUp(){
        EdgeTable edgeTable = new EdgeTable();
        try {
            edgeTable.addNativeField(9);
            edgeTable.addNativeField(4);
            edgeTable.addNativeField(1);
            edgeTable.moveFieldUp(1);
            assertArrayEquals(new int[]{4,9,1}, edgeTable.getNativeFieldsArray());
        } catch (Exception e) {
            System.out.println("Error " + e);
            return;
        } 
 
    }  
    
    @Test
    public void testMoveFieldUp2(){
        EdgeTable edgeTable = new EdgeTable();
        try {
            edgeTable.addNativeField(7);
            edgeTable.addNativeField(2);
            edgeTable.addNativeField(3);
            edgeTable.moveFieldUp(2);
            assertArrayEquals(new int[]{7,3,2}, edgeTable.getNativeFieldsArray());
        } catch (Exception e) {
            System.out.println("Error " + e);
            return;
        } 
 
    }   

    // testing the move of native field down at given index
    @Test
    public void testMoveFieldDown(){
        EdgeTable edgeTable = new EdgeTable();
        try {
            edgeTable.addNativeField(3);
            edgeTable.addNativeField(6);
            edgeTable.addNativeField(8);
            edgeTable.moveFieldDown(1);
            assertArrayEquals(new int[]{3,8,6}, edgeTable.getNativeFieldsArray());
        } catch (Exception e) {
            System.out.println("Error " + e);
            return; 
        } 
              
    }  
    
    @Test
    public void testMoveFieldDown2(){
        EdgeTable edgeTable = new EdgeTable();
        try {
            edgeTable.addNativeField(1);
            edgeTable.addNativeField(3);
            edgeTable.addNativeField(2);
            edgeTable.moveFieldDown(1);
            assertArrayEquals(new int[]{1,2,3}, edgeTable.getNativeFieldsArray());
        } catch (Exception e) {
            System.out.println("Error " + e);
            return; 
        } 

    }   

    @Test
    public void testMakeArrays(){
        EdgeTable edgeTable = new EdgeTable();
        try{
            edgeTable.addNativeField(1);
            edgeTable.addRelatedTable(4);
            edgeTable.makeArrays();
            assertArrayEquals(new int[]{1}, edgeTable.getNativeFieldsArray());
            assertArrayEquals(new int[]{4}, edgeTable.getRelatedTablesArray());
        } catch (Exception e) {
            System.out.println("Error " + e);
            return; 
        } 
        fail("Failed: Exception thrown.");

    }
}
