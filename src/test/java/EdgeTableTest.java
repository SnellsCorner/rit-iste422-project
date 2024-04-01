import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class EdgeTableTest {
    // testing the addition of related tables
    @Test
    public void addRelatedTableTest(){
        EdgeTable edgeTable = new EdgeTable();
        try {
            edgeTable.addRelatedTable(4);
            edgeTable.addRelatedTable(7);
            assertArrayEquals(new int[]{4,7}, edgeTable.getRelatedTablesArray());
        } catch (Exception e) {
            System.out.println("Error" + e);
            return; 
        } 
    }

    // testing the addition of native fields
    @Test
    public void addNativeFieldTest(){
        EdgeTable edgeTable = new EdgeTable();
        try {
            edgeTable.addNativeField(2);
            edgeTable.addNativeField(9);
            edgeTable.addNativeField(6);
            assertArrayEquals(new int[]{2,9,6}, edgeTable.getNativeFieldsArray());
        } catch (Exception e) {
            System.out.println("Error" + e);
            return; 
        } 

    }

    // testing if setting the related field works
    public void setRelatedFieldTest(){
        EdgeTable edgeTable = new EdgeTable();
        try {
           edgeTable.addRelatedTable(3);
            edgeTable.makeArrays();
            edgeTable.setRelatedField(0, 4);
            assertArrayEquals(new int[]{4}, edgeTable.getRelatedFieldsArray());
            edgeTable.setRelatedField(1, 5);
            assertArrayEquals(new int[]{4,5}, edgeTable.getRelatedFieldsArray()); 
        } catch (Exception e) {
            System.out.println("Error" + e);
            return; 
        } 
        
        
    }

    // testing the move of native field up at given index
    @Test
    public void moveFieldUpTest(){
        EdgeTable edgeTable = new EdgeTable();
        try {
            edgeTable.addNativeField(9);
            edgeTable.addNativeField(4);
            edgeTable.addNativeField(1);
            edgeTable.moveFieldUp(1);
            assertArrayEquals(new int[]{4,9,1}, edgeTable.getNativeFieldsArray());
        } catch (Exception e) {
            System.out.println("Error" + e);
            return;
        } 

        
    }   

    // testing the move of native field down at given index
    @Test
    public void moveFieldDownTest(){
        EdgeTable edgeTable = new EdgeTable();
        try {
            edgeTable.addNativeField(3);
            edgeTable.addNativeField(6);
            edgeTable.addNativeField(8);
            edgeTable.moveFieldDown(1);
            assertArrayEquals(new int[]{3,8,6}, edgeTable.getNativeFieldsArray());
        } catch (Exception e) {
            System.out.println("Error" + e);
            return; 
        } 
              
    }   

    @Test
    public void makeArraysTest(){
        EdgeTable edgeTable = new EdgeTable();
        try{
            edgeTable.addNativeField(1);
            edgeTable.addRelatedTable(4);
            edgeTable.makeArrays();
            assertArrayEquals(new int[]{1}, edgeTable.getNativeFieldsArray());
            assertArrayEquals(new int[]{4}, edgeTable.getRelatedTablesArray());
        } catch (Exception e) {
            System.out.println("Error" + e);
            return; 
        } 
        fail("Failed: Exception thrown.");

    }

}
