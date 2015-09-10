package personal.com.tithingapp.parcel_translators;

import android.content.Context;
import android.database.Cursor;
import personal.com.tithingapp.database.Provider;
import personal.com.tithingapp.database.TithingTable;
import personal.com.tithingapp.parcels.TithingParcel;

public class TithingParcelTranslator {

    public static TithingParcel getParcelForID(Context context, long id) {
        TithingParcel tithingParcel = new TithingParcel();

        Cursor cursor = context.getContentResolver().query(Provider.TITHING_CONTENT_URI, TithingTable.ALL_COLUMNS, TithingTable.WHERE_ID_EQUALS, new String[] {Long.toString(id)}, null);
        if (cursor.moveToFirst()) {
            tithingParcel.setID(cursor.getLong(cursor.getColumnIndex(TithingTable.ID)));
            tithingParcel.setTitle(cursor.getString(cursor.getColumnIndex(TithingTable.TITLE)));
            tithingParcel.setAmount(cursor.getInt(cursor.getColumnIndex(TithingTable.AMOUNT)));
            tithingParcel.setDate(cursor.getString(cursor.getColumnIndex(TithingTable.DATE)));
        }
        cursor.close();

        return tithingParcel;
    }
}
