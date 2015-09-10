package personal.com.tithingapp.database;

import com.verisage.quickprovider.Table;
import com.verisage.quickprovider.TableBuilder;

public class TithingTable implements Table {
    private static TableBuilder mTableBuider = TableBuilder.getInstance();

    public static final String TABLE_NAME = mTableBuider.open("tithing");

    public static final String TITLE = mTableBuider.appendText("title");
    public static final String DATE = mTableBuider.appendText("date");
    public static final String NOTES = mTableBuider.appendText("notes");
    public static final String AMOUNT = mTableBuider.appendInt("amount");

    public static final String CREATE = mTableBuider.retrieveCreateString();

    public static final String[] ALL_COLUMNS = mTableBuider.retrieveAllColumnsArray();
}
