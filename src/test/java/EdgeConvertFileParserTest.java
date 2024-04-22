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
    public void setUp() throws IOException{
        // Initialize a new EdgeConvertFileParser object before each test
        edgeFile = new File("test.edg");
        saveFile = new File("test.save");
        parser = new ParseEdgeFile(edgeFile);
        parser.parseSaveFile(); // Remove the parameter here
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
        parser = new ParseSaveFile(saveFile); // Use the save file for parsing
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

    @Test(expected = Exception.class)
    public void openFileTest() throws Exception {
        parser.openFile(edgeFile);
    }
    
}
