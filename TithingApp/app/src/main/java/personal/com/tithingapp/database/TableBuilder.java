package personal.com.tithingapp.database;

import java.util.ArrayList;

public class TableBuilder {
    public static final String DB_FLOAT = "REAL";
    public static final String DB_INTEGER = "INTEGER";
    public static final String DB_TEXT = "TEXT";
    public static final String DB_BOOLEAN = "INTEGER";

    public static final String ID = "_id";
    public static final String UUID = "uuid";
    public static final String APP_UUID = "app_uuid";

    public static final String WHERE_ID_EQUALS = ID + "=?";

    private static final String SPACE = " ";
    private static final String PERIOD = ".";
    private static final String COMMA = ",";

    private static StringBuilder createString;

    private static String currentTable;

    private static ArrayList<String> columns;

    protected static String begin(String tableName) {
        createString = new StringBuilder();
        columns = new ArrayList<>();
        currentTable = tableName;

        createString.append("CREATE TABLE ");
        createString.append(currentTable);
        createString.append(" ( ");
        createString.append(ID);
        createString.append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        createString.append(UUID);
        createString.append(SPACE);
        createString.append(DB_TEXT);
        createString.append(COMMA);

        columns.add(currentTable + PERIOD + ID);
        columns.add(currentTable + PERIOD + UUID);

        return tableName;
    }

    protected static String appendText(String columnName) {
        return append(columnName, DB_TEXT);
    }

    protected static String appendInt(String columnName) {
        return append(columnName, DB_INTEGER);
    }

    protected static String appendBoolean(String columnName) {
        return append(columnName, DB_BOOLEAN);
    }

    private static String append(String columnName, String type) {
        createString.append(columnName);
        createString.append(SPACE);
        createString.append(type);
        createString.append(COMMA);

        columns.add(currentTable + PERIOD + columnName);

        return columnName;
    }

    protected static String appendTextWithConstraint(String columnName, String constraint) {
        return appendWithConstraint(columnName, DB_TEXT, constraint);
    }

    protected static String appendIntWithConstraint(String columnName, String constraint) {
        return appendWithConstraint(columnName, DB_INTEGER, constraint);
    }

    protected static String appendBooleanWithConstraint(String columnName, String constraint) {
        return appendWithConstraint(columnName, DB_BOOLEAN, constraint);
    }

    private static String appendWithConstraint(String columnName, String type, String constraint) {
        createString.append(columnName);
        createString.append(SPACE);
        createString.append(type);
        createString.append(SPACE);
        createString.append(constraint);
        createString.append(COMMA);

        columns.add(currentTable + PERIOD + columnName);

        return columnName;
    }

    protected static String end(String columnName, String type) {
        createString.append(columnName);
        createString.append(SPACE);
        createString.append(type);
        createString.append(")");

        columns.add(currentTable + PERIOD + columnName);

        return columnName;
    }

    protected static String endWithConstraint(String columnName, String type, String constraint) {
        createString.append(columnName);
        createString.append(SPACE);
        createString.append(type);
        createString.append(SPACE);
        createString.append(constraint);
        createString.append(")");

        columns.add(currentTable + PERIOD + columnName);

        return columnName;
    }

    protected static String retrieveDropString() {
        return currentTable + " DROP TABLE IF EXISTS ";
    }

    protected static String retrieveCreateString() {
        return createString.toString();
    }

    protected static String[] retrieveAllColumnsArray() {
        String[] allColumns = new String[1];
        return columns.toArray(allColumns);
    }
}
