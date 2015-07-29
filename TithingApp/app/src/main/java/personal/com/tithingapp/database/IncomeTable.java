package personal.com.tithingapp.database;

public class IncomeTable extends TableBuilder {

    public static final String TABLE_NAME = begin("income");
    public static final String TITLE = appendText("title");
    public static final String DATE = appendText("date");
    public static final String AMOUNT = end("amount", DB_FLOAT);

    public static final String CREATE = retrieveCreateString();

    public static final String[] ALL_COLUMNS = retrieveAllColumnsArray();

    public static final String CONTENT_PATH = "income";
    public static final String CONTENT_TYPE = Provider.appendContentTypeToPath(CONTENT_PATH);
    public static final String CONTENT_ITEM_TYPE = Provider.appendContentItemTypeToPath(CONTENT_PATH);
}
