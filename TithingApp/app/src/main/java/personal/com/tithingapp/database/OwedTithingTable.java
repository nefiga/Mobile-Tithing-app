package personal.com.tithingapp.database;

import com.verisage.quickprovider.TableBuilder;

public class OwedTithingTable {
    private static TableBuilder mTableBuilder = TableBuilder.getInstance();

    public static final String TABLE_NAME = mTableBuilder.open("OwedTithingTable");

    public static final String TITHING_PAID = mTableBuilder.appendText("TithingPaid");
    public static final String INCOME_MADE = mTableBuilder.appendText("IncomeMade");

    public static final String CREATE = mTableBuilder.retrieveCreateString();

    public static final String[] ALL_COLUMNS = mTableBuilder.retrieveAllColumnsArray();
}
