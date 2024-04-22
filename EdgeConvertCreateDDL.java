import static org.junit.Assert.*;
import org.junit.*;
// import org.mockito.Mockito;

import java.util.logging.Logger;
import java.util.logging.Level;

public abstract class EdgeConvertCreateDDL {
    protected Logger logger = Logger.getLogger(EdgeConvertCreateDDL.class.getName());
    static String[] products = {"MySQL"};
    protected EdgeTable[] tables; // master copy of EdgeTable objects
    protected EdgeField[] fields; // master copy of EdgeField objects
    protected int[] numBoundTables;
    protected int maxBound;
    protected StringBuffer sb;
    protected int selected;

    public EdgeConvertCreateDDL(EdgeTable[] tables, EdgeField[] fields) {
        this.tables = tables;
        this.fields = fields;
        initialize();
    } // EdgeConvertCreateDDL(EdgeTable[], EdgeField[])

    public EdgeConvertCreateDDL() { // default constructor with empty arg list for to allow output dir to be set before there are table and field objects

    } // EdgeConvertCreateDDL()

    public void initialize() {
        try {
            logger.info("Starting initialization.");
            numBoundTables = new int[tables.length];
            maxBound = 0;
            sb = new StringBuffer();

            for (int i = 0; i < tables.length; i++) { // step through list of tables
                int numBound = 0; // initialize counter for number of bound tables
                int[] relatedFields = tables[i].getRelatedFieldsArray();
                for (int j = 0; j < relatedFields.length; j++) { // step through related fields list
                    if (relatedFields[j] != 0) {
                        numBound++; // count the number of non-zero related fields
                    }
                }
                numBoundTables[i] = numBound;
                if (numBound > maxBound) {
                    maxBound = numBound;
                }
            }
        } catch (Exception e) {
            logger.log(Level.FINE, "Debug message: " + e.getMessage(), e);
            logger.log(Level.SEVERE, "Fatal error occurred: " + e.getMessage(), e);
            EdgeConvertGUI.setReadSuccess(false);
        }
    }

    protected EdgeTable getTable(int numFigure) {
        try {
            for (int tIndex = 0; tIndex < tables.length; tIndex++) {
                if (numFigure == tables[tIndex].getNumFigure()) {
                    logger.info("Table found at index" + tIndex);
                    return tables[tIndex];
                }
            }
        } catch (Exception e) {
            logger.log(Level.FINE, "Debug message: " + e.getMessage()); // Debug log
            // tracing using sysout
            logger.log(Level.WARNING, "Error occurred while retrieving table: " + e.getMessage(), e);// error logs
        }
        return null;
    }

    protected EdgeField getField(int numFigure) {
        try {
            for (int fIndex = 0; fIndex < fields.length; fIndex++) {
                if (numFigure == fields[fIndex].getNumFigure()) {
                    logger.info("Field found at index " + fIndex);
                    return fields[fIndex];
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error occurred while retrieving field: " + e.getMessage(), e);// error logs
        }
        return null;
    }

    public abstract String getDatabaseName();

    public abstract String getProductName();

    public abstract String getSQLString();

    public abstract void createDDL();

} // EdgeConvertCreateDDL
