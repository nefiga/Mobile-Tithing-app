package personal.com.tithingapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class Provider extends ContentProvider{


    public static final String TAG = "TithingAppProvider";

    public static final String AUTHORITY = "personal.com.tithingapp.database.Provider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static final Uri INCOME_CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, IncomeTable.CONTENT_PATH);

    public static final int INCOME_DIR = 1;
    public static final int INCOME_ID = 2;

    private static final UriMatcher URI_MATCHER;

    private Database mDatabase;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        addToUriMatcher(IncomeTable.CONTENT_PATH, INCOME_DIR, INCOME_ID);
    }

    @Override
    public boolean onCreate() {
        mDatabase = new Database(getContext());
        return true;
    }

    private static void addToUriMatcher(String contentPath, int dir, int id) {
        URI_MATCHER.addURI(AUTHORITY, contentPath, dir);
        URI_MATCHER.addURI(AUTHORITY, contentPath + "/#", id);
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case INCOME_DIR:
                return IncomeTable.CONTENT_TYPE;
            case INCOME_ID:
                return IncomeTable.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        final SQLiteDatabase databaseConnection = mDatabase.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {
            case INCOME_ID:
                queryBuilder.appendWhere(IncomeTable.ID + "=" + uri.getPathSegments().get(1));
                break;
            case INCOME_DIR:
                queryBuilder.setTables(IncomeTable.TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        Cursor cursor = queryBuilder.query(databaseConnection, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase databaseConnection  = mDatabase.getWritableDatabase();

        try {
            databaseConnection.beginTransaction();

            switch (URI_MATCHER.match(uri)) {
                case INCOME_DIR:
                case INCOME_ID:
                    return insert(databaseConnection, IncomeTable.TABLE_NAME, INCOME_CONTENT_URI, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseConnection.endTransaction();
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return null;
    }

    private Uri insert(SQLiteDatabase dataBaseConnection, String tableName, Uri contentUri, ContentValues values)
            throws SQLException {
        final long id = dataBaseConnection.insertOrThrow(tableName, null, values);
        final Uri uri = ContentUris.withAppendedId(contentUri, id);
        getContext().getContentResolver().notifyChange(uri, null);
        dataBaseConnection.setTransactionSuccessful();
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase databaseConnection = mDatabase.getWritableDatabase();
        int deleteCount = 0;

        try {
            databaseConnection.beginTransaction();

            switch (URI_MATCHER.match(uri)) {
                case INCOME_DIR:
                    deleteCount = deleteSelection(databaseConnection, IncomeTable.TABLE_NAME, selection, selectionArgs);
                    break;
                case INCOME_ID:
                    deleteCount = deleteId(databaseConnection, IncomeTable.TABLE_NAME, uri);
                    break;
            }
        } finally {
            databaseConnection.endTransaction();
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleteCount;
    }

    private int deleteSelection(SQLiteDatabase databaseConnection, String tableName, String selection, String[] selectionArgs) {
        int deleteCount = databaseConnection.delete(tableName, selection, selectionArgs);
        databaseConnection.setTransactionSuccessful();
        return deleteCount;
    }

    private int deleteId(SQLiteDatabase databaseConnection, String tableName, Uri uri) {
        int deleteCount = databaseConnection.delete(tableName, TableBuilder.WHERE_ID_EQUALS, new String[] {uri.getPathSegments().get(1)});
        databaseConnection.setTransactionSuccessful();
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase databaseConnection = mDatabase.getWritableDatabase();
        int updateCount = 0;

        try {
            databaseConnection.beginTransaction();;

            switch (URI_MATCHER.match(uri)) {
                case INCOME_DIR:
                    updateCount = updateSelection(databaseConnection, IncomeTable.TABLE_NAME, values, selection, selectionArgs);
                    break;
                case INCOME_ID:
                    updateCount = updateId(databaseConnection, IncomeTable.TABLE_NAME, values, uri, selection, selectionArgs);
                    break;
            }
        } finally {
            databaseConnection.endTransaction();
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updateCount;
    }

    private int updateSelection(SQLiteDatabase databaseConnection, String tableName, ContentValues values, String selection, String[] selectionArgs) {
        int updateCount = databaseConnection.update(tableName, values, selection, selectionArgs);
        databaseConnection.setTransactionSuccessful();
        return updateCount;
    }

    private int updateId(SQLiteDatabase databaseConnection, String tableName, ContentValues values, Uri uri, String selection, String[] selectionArgs) {
        final Long id = ContentUris.parseId(uri);
        int updateCount = databaseConnection.update(tableName, values, TableBuilder.ID + "=" + id + (TextUtils.isEmpty(selection) ? "" : " AND (" + selection + ")"), selectionArgs);
        databaseConnection.setTransactionSuccessful();
        return updateCount;
    }

    //---------- INNER CLASSES -----------

    public static String appendContentTypeToPath(String type) {
        return "vnd.android.cursor.dir/vnd.appme." + type;
    }

    public static String appendContentItemTypeToPath(String type) {
        return "vnd.android.cursor.item/vnd.appme." + type;
    }
}
