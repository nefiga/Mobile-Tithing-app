package personal.com.tithingapp.parcel_translators;

import android.content.Context;
import android.database.Cursor;

import personal.com.tithingapp.database.IncomeTable;
import personal.com.tithingapp.database.Provider;
import personal.com.tithingapp.parcels.IncomeParcel;

public class IncomeParcelTranslator {

    private Context mContext;

    public IncomeParcelTranslator(Context context) {
        mContext = context;
    }

    public IncomeParcel getParcelForID(long id) {
        IncomeParcel incomeParcel = new IncomeParcel();

        Cursor cursor = mContext.getContentResolver().query(Provider.INCOME_CONTENT_URI, IncomeTable.ALL_COLUMNS, IncomeTable.WHERE_ID_EQUALS, new String[] {Long.toString(id)}, null);
        if (cursor.moveToFirst()) {
            incomeParcel.setTitle(cursor.getString(cursor.getColumnIndex(IncomeTable.TITLE)));
            incomeParcel.setAmount(cursor.getInt(cursor.getColumnIndex(IncomeTable.AMOUNT)));
            incomeParcel.setDate(cursor.getString(cursor.getColumnIndex(IncomeTable.DATE)));
        }
        cursor.close();

        return incomeParcel;
    }
}
