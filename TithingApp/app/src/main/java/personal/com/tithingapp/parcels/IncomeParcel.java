package personal.com.tithingapp.parcels;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import personal.com.tithingapp.database.IncomeTable;
import personal.com.tithingapp.database.Provider;

/**
 * Created by ryant on 8/2/15.
 */
public class IncomeParcel extends DataParcel {

    public IncomeParcel() {
        // Default constructor
    }

    public IncomeParcel(Parcel in) {
        super(in);
    }

    public static final Creator<IncomeParcel> CREATOR = new Creator<IncomeParcel>() {
        @Override
        public IncomeParcel createFromParcel(Parcel in) {
            return new IncomeParcel(in);
        }

        @Override
        public IncomeParcel[] newArray(int size) {
            return new IncomeParcel[size];
        }
    };

    @Override
    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(IncomeTable.TITLE, mTitle);
        contentValues.put(IncomeTable.AMOUNT, mAmount);
        contentValues.put(IncomeTable.DATE, mDate);

        return contentValues;
    }

    @Override
    public void save(Context context) {
        context.getContentResolver().insert(Provider.INCOME_CONTENT_URI, getContentValues());
    }

    @Override
    public void update(Context context) {
        context.getContentResolver().update(Provider.INCOME_CONTENT_URI, getContentValues(), IncomeTable.WHERE_ID_EQUALS, new String[]{mID.toString()});
    }

    @Override
    public void delete(Context context) {
        context.getContentResolver().delete(Provider.INCOME_CONTENT_URI, IncomeTable.WHERE_ID_EQUALS, new String[]{mID.toString()});
    }
}
