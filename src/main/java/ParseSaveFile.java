import java.io.IOException;
import java.util.StringTokenizer;
import java.io.File;
import javax.swing.JOptionPane;

public class ParseSaveFile extends EdgeConvertFileParser {
    public ParseSaveFile(File constructorFile) {
        super(constructorFile);
    }

    @Override
    public void parseSaveFile() throws IOException { //this method is unclear and confusing in places
        logger.info("Parsing file");
        StringTokenizer stTables, stNatFields, stRelFields, stNatRelFields, stField;
        EdgeTable tempTable;
        EdgeField tempField;
        currentLine = br.readLine();
        currentLine = br.readLine(); //this should be "Table: "
        while (currentLine.startsWith("Table: ")) {
           numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); //get the Table number
           currentLine = br.readLine(); //this should be "{"
           currentLine = br.readLine(); //this should be "TableName"
           tableName = currentLine.substring(currentLine.indexOf(" ") + 1);
           tempTable = new EdgeTable(numFigure + DELIM + tableName);
           
           currentLine = br.readLine(); //this should be the NativeFields list
           stNatFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
           numFields = stNatFields.countTokens();
           for (int i = 0; i < numFields; i++) {
              tempTable.addNativeField(Integer.parseInt(stNatFields.nextToken()));
           }
           
           currentLine = br.readLine(); //this should be the RelatedTables list
           stTables = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
           numTables = stTables.countTokens();
           for (int i = 0; i < numTables; i++) {
              tempTable.addRelatedTable(Integer.parseInt(stTables.nextToken()));
           }
           tempTable.makeArrays();
           
           currentLine = br.readLine(); //this should be the RelatedFields list
           stRelFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), DELIM);
           numFields = stRelFields.countTokens();
  
           for (int i = 0; i < numFields; i++) {
              tempTable.setRelatedField(i, Integer.parseInt(stRelFields.nextToken()));
           }
  
           alTables.add(tempTable);
           currentLine = br.readLine(); //this should be "}"
           currentLine = br.readLine(); //this should be "\n"
           currentLine = br.readLine(); //this should be either the next "Table: ", #Fields#
        }

        while ((currentLine = br.readLine()) != null) {
           stField = new StringTokenizer(currentLine, DELIM);
           numFigure = Integer.parseInt(stField.nextToken());
           fieldName = stField.nextToken();
           tempField = new EdgeField(numFigure + DELIM + fieldName);
           tempField.setTableID(Integer.parseInt(stField.nextToken()));
           tempField.setTableBound(Integer.parseInt(stField.nextToken()));
           tempField.setFieldBound(Integer.parseInt(stField.nextToken()));
           tempField.setDataType(Integer.parseInt(stField.nextToken()));
           tempField.setVarcharValue(Integer.parseInt(stField.nextToken()));
           tempField.setIsPrimaryKey(Boolean.valueOf(stField.nextToken()).booleanValue());
           tempField.setDisallowNull(Boolean.valueOf(stField.nextToken()).booleanValue());
           if (stField.hasMoreTokens()) { //Default Value may not be defined
              tempField.setDefaultValue(stField.nextToken());
           }

           alFields.add(tempField);

        }

     } // parseSaveFile()

     @Override
    protected void parseEdgeFile() throws IOException {
        // Implementation of the parseEdgeFile() method for ParseSaveFile class
    }

    // Other methods specific to Save files here
}
