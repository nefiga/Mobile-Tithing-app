package personal.com.tithingapp.database;

import com.verisage.quickprovider.Table;
import com.verisage.quickprovider.TableBuilder;

public class IncomeTable implements Table{
    private static TableBuilder mTableBuilder = TableBuilder.getInstance();

    public static final String TABLE_NAME = mTableBuilder.open("income");
    public static final String TITLE = mTableBuilder.appendText("title");
    public static final String DATE = mTableBuilder.appendText("date");
    public static final String NOTES = mTableBuilder.appendText("notes");
    public static final String AMOUNT = mTableBuilder.appendInt("amount");

    public static final String CREATE = mTableBuilder.retrieveCreateString();

    public static final String[] ALL_COLUMNS = mTableBuilder.retrieveAllColumnsArray();
}
