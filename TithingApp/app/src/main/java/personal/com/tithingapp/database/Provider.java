package personal.com.tithingapp.database;

import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import com.verisage.quickprovider.QuickProvider;

public class Provider extends QuickProvider {
    private final String MIME_TYPE = "appme";

    public static final String AUTHORITY = "personal.com.tithingapp.database.Provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static final Uri INCOME_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, IncomeTable.TABLE_NAME);
    public static final Uri TITHING_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, TithingTable.TABLE_NAME);
    public static final Uri OWED_TITHING_URI = Uri.withAppendedPath(AUTHORITY_URI, OwedTithingTable.TABLE_NAME);

    @Override
    public boolean onCreate() {
        super.onCreate();

        addToUriMatcher(AUTHORITY, IncomeTable.TABLE_NAME);
        addToUriMatcher(AUTHORITY, TithingTable.TABLE_NAME);
        addToUriMatcher(AUTHORITY, OwedTithingTable.TABLE_NAME);

        return true;
    }

    @Override
    protected SQLiteOpenHelper getSQLiteOpenHelper() {
        return new Database(getContext());
    }

    @Override
    protected String getMimeTypeName() {
        return MIME_TYPE;
    }
}
