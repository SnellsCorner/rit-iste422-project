import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;


public class EdgeConvertFileParserTest {
    private EdgeConvertFileParser parser;
    private File edgeFile;
    private File saveFile;

    @Before
    public void setUp() {
        // Initialize a new EdgeConvertFileParser object before each test
        edgeFile = new File("test.edg");
        saveFile = new File("test.save");
        parser = new EdgeConvertFileParser(edgeFile);
    }

    // Test to check that the parseEdgeFile method works
    @Test
    public void parseEdgeFileTest() throws IOException {
        parser.parseEdgeFile();
        
        // Verify that tables and fields are parsed after parsing the Edge file
        EdgeTable[] tables = parser.getEdgeTables();
        EdgeField[] fields = parser.getEdgeFields();
        assertNotNull(tables);
        assertNotNull(fields);
    }

    // Test to check that the parseSaveFile method works
    @Test
    public void parseSaveFileTest() throws IOException {
        parser = new EdgeConvertFileParser(saveFile); // Use the save file for parsing
        parser.parseSaveFile();

        //makes sure file is parsed by making sure that it is not null. 
        assertNotNull(parser.getEdgeTables());
        assertNotNull(parser.getEdgeFields());
    }


    @Test 
    public void getEdgeTablesTest(){
        assertNotNull(parser.getEdgeTables());
    }


    @Test 
    public void getEdgeFieldsTest(){
        assertNotNull(parser.getEdgeFields());
    }


    @Test
    public void openFileTest(){
        try{
        parser.openFile(edgeFile);
        }catch(Exception e){
            fail("Error: File Couldnt not be opened.");
        }
    }
    
}