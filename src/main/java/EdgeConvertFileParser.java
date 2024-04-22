import java.io.*;
import java.util.*;
import javax.swing.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.File;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;




public abstract class EdgeConvertFileParser {
    protected static final Logger logger = Logger.getLogger(EdgeConvertFileParser.class.getName());
    protected File parseFile;
    protected FileReader fr;
    protected BufferedReader br;
    protected String currentLine;
    protected ArrayList<EdgeTable> alTables;
    protected ArrayList<EdgeField> alFields;
    protected ArrayList<EdgeConnector> alConnectors;
    protected EdgeTable[] tables;
    protected EdgeField[] fields;
    protected EdgeField tempField;
    protected EdgeConnector[] connectors;
    protected String style;
    protected String text;
    protected String tableName;
    protected String fieldName;
    protected boolean isEntity, isAttribute, isUnderlined = false;
    protected int numFigure, numConnector, numFields, numTables, numNativeRelatedFields;
    protected int endPoint1, endPoint2;
    protected String endStyle1, endStyle2;
    public static final String DELIM = "|";
    protected int numLine = 0;
    protected static final String EDGE_ID = "EDGE";
    protected static final String SAVE_ID = "SAVE";


    public EdgeConvertFileParser(File constructorFile) {
        numFigure = 0;
        numConnector = 0;
        alTables = new ArrayList<>();
        alFields = new ArrayList<>();
        alConnectors = new ArrayList<>();
        isEntity = false;
        isAttribute = false;
        parseFile = constructorFile;
        numLine = 0;
        this.openFile(parseFile);
    }

    protected abstract void parseEdgeFile() throws IOException;

    protected abstract void parseSaveFile() throws IOException;
   
   protected void resolveConnectors() { //Identify nature of Connector endpoints
      int endPoint1, endPoint2;
      int fieldIndex = 0, table1Index = 0, table2Index = 0;
      for (int cIndex = 0; cIndex < connectors.length; cIndex++) {
         endPoint1 = connectors[cIndex].getEndPoint1();
         endPoint2 = connectors[cIndex].getEndPoint2();
         logger.info("Resolving connectors between "+ endPoint1 + " and " + endPoint2);
         fieldIndex = -1;
         for (int fIndex = 0; fIndex < fields.length; fIndex++) { //search fields array for endpoints
            if (endPoint1 == fields[fIndex].getNumFigure()) { //found endPoint1 in fields array
               connectors[cIndex].setIsEP1Field(true); //set appropriate flag
               fieldIndex = fIndex; //identify which element of the fields array that endPoint1 was found in
            }
            if (endPoint2 == fields[fIndex].getNumFigure()) { //found endPoint2 in fields array
               connectors[cIndex].setIsEP2Field(true); //set appropriate flag
               fieldIndex = fIndex; //identify which element of the fields array that endPoint2 was found in
            }
         }

         for (int tIndex = 0; tIndex < tables.length; tIndex++) { //search tables array for endpoints
            if (endPoint1 == tables[tIndex].getNumFigure()) { //found endPoint1 in tables array
               connectors[cIndex].setIsEP1Table(true); //set appropriate flag
               table1Index = tIndex; //identify which element of the tables array that endPoint1 was found in
            }
            if (endPoint2 == tables[tIndex].getNumFigure()) { //found endPoint1 in tables array
               connectors[cIndex].setIsEP2Table(true); //set appropriate flag
               table2Index = tIndex; //identify which element of the tables array that endPoint2 was found in
            }
         }
         
         if (connectors[cIndex].getIsEP1Field() && connectors[cIndex].getIsEP2Field()) { //both endpoints are fields, implies lack of normalization
            JOptionPane.showMessageDialog(null, "The Edge Diagrammer file\n" + parseFile + "\ncontains composite attributes. Please resolve them and try again.");
            EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
            break; //stop processing list of Connectors
         }

         if (connectors[cIndex].getIsEP1Table() && connectors[cIndex].getIsEP2Table()) { //both endpoints are tables
            if ((connectors[cIndex].getEndStyle1().indexOf("many") >= 0) &&
                (connectors[cIndex].getEndStyle2().indexOf("many") >= 0)) { //the connector represents a many-many relationship, implies lack of normalization
               JOptionPane.showMessageDialog(null, "There is a many-many relationship between tables\n\"" + tables[table1Index].getName() + "\" and \"" + tables[table2Index].getName() + "\"" + "\nPlease resolve this and try again.");
               EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
               break; //stop processing list of Connectors
            } else { //add Figure number to each table's list of related tables
               tables[table1Index].addRelatedTable(tables[table2Index].getNumFigure());
               tables[table2Index].addRelatedTable(tables[table1Index].getNumFigure());
               continue; //next Connector
            }
         }
         
         if (fieldIndex >=0 && fields[fieldIndex].getTableID() == 0) { //field has not been assigned to a table yet
            if (connectors[cIndex].getIsEP1Table()) { //endpoint1 is the table
                tables[table1Index].addNativeField(fields[fieldIndex].getNumFigure()); //add to the appropriate table's field list
                fields[fieldIndex].setTableID(tables[table1Index].getNumFigure()); //tell the field what table it belongs to
            } else { //endpoint2 is the table
                tables[table2Index].addNativeField(fields[fieldIndex].getNumFigure()); //add to the appropriate table's field list
                fields[fieldIndex].setTableID(tables[table2Index].getNumFigure()); //tell the field what table it belongs to
            }
        } else if (fieldIndex >=0) { //field has already been assigned to a table
            JOptionPane.showMessageDialog(null, "The attribute " + fields[fieldIndex].getName() + " is connected to multiple tables.\nPlease resolve this and try again.");
            EdgeConvertGUI.setReadSuccess(false); //this tells GUI not to populate JList components
            break; //stop processing 
            // list of Connectors
        }
        
      } // connectors for() loop

   } // resolveConnectors()

   protected void makeArrays() { //convert ArrayList objects into arrays of the appropriate Class type
      if (alTables != null) {
         tables = (EdgeTable[])alTables.toArray(new EdgeTable[alTables.size()]);
      }
      if (alFields != null) {
         fields = (EdgeField[])alFields.toArray(new EdgeField[alFields.size()]);
      }
      if (alConnectors != null) {
         connectors = (EdgeConnector[])alConnectors.toArray(new EdgeConnector[alConnectors.size()]);
      }
   }
   
   protected boolean isTableDup(String testTableName) {
      for (int i = 0; i < alTables.size(); i++) {
         EdgeTable tempTable = (EdgeTable)alTables.get(i);
         if (tempTable.getName().equals(testTableName)) {
            logger.info(testTableName + " is a duplicate.");
            return true;
         }
      }
      return false;
   }
   
   public EdgeTable[] getEdgeTables() {
      return tables;
   }
   
   public EdgeField[] getEdgeFields() {
      return fields;
   }
   
   public void openFile(File inputFile) {
      try {
         logger.info("Opening file: " + inputFile);
         fr = new FileReader(inputFile);
         br = new BufferedReader(fr);
         //test for what kind of file we have
         currentLine = br.readLine().trim();
         numLine++;
         if (currentLine.startsWith(EDGE_ID)) { //the file chosen is an Edge Diagrammer file
            this.parseEdgeFile(); //parse the file
            br.close();
            this.makeArrays(); //convert ArrayList objects into arrays of the appropriate Class type
            this.resolveConnectors(); //Identify nature of Connector endpoints
         } else {
            if (currentLine.startsWith(SAVE_ID)) { //the file chosen is a Save file created by this application
               this.parseSaveFile(); //parse the file
               br.close();
               this.makeArrays(); //convert ArrayList objects into arrays of the appropriate Class type
            } else { //the file chosen is something else
               JOptionPane.showMessageDialog(null, "Unrecognized file format");
            }
         }
      } // try
      catch (FileNotFoundException fnfe) {
         logger.log(Level.FINE, "Debug message:  " + fnfe.getMessage()); // Debug log

         logger.log(Level.WARNING, "Cannot find file: " + inputFile.getName(), fnfe);//error logs
         System.out.println("Cannot find \"" + inputFile.getName() + "\".");
         System.exit(0);
      } // catch FileNotFoundException
      catch (IOException ioe) {
         logger.log(Level.FINE, "Debug message:  " + ioe.getMessage()); // Debug log

         logger.log(Level.WARNING, "IO Exception occurred: " + ioe.getMessage(), ioe);//error logs
         System.out.println(ioe);
         System.exit(0);
      } // catch IOException
   } // openFile()
} // EdgeConvertFileHandler
