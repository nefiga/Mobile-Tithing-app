package personal.com.tithingapp.parcel_translators;

import android.content.Context;
import android.database.Cursor;

import personal.com.tithingapp.database.IncomeTable;
import personal.com.tithingapp.database.Provider;
import personal.com.tithingapp.parcels.IncomeParcel;

public class IncomeParcelTranslator {

    public static IncomeParcel getParcelForID(Context context, long id) {
        IncomeParcel incomeParcel = new IncomeParcel();

        Cursor cursor = context.getContentResolver().query(Provider.INCOME_CONTENT_URI, IncomeTable.ALL_COLUMNS, IncomeTable.WHERE_ID_EQUALS, new String[] {Long.toString(id)}, null);
        if (cursor.moveToFirst()) {
            incomeParcel.setID(cursor.getLong(cursor.getColumnIndex(IncomeTable.ID)));
            incomeParcel.setTitle(cursor.getString(cursor.getColumnIndex(IncomeTable.TITLE)));
            incomeParcel.setAmount(cursor.getFloat(cursor.getColumnIndex(IncomeTable.AMOUNT)));
            incomeParcel.setDate(cursor.getString(cursor.getColumnIndex(IncomeTable.DATE)));
        }
        cursor.close();

        return incomeParcel;
    }
}
