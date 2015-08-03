package personal.com.tithingapp.parcels;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;
import personal.com.tithingapp.database.Provider;
import personal.com.tithingapp.database.TithingTable;

public class TithingParcel extends DataParcel {

    public TithingParcel() {
        // Default constructor
    }

    public TithingParcel(Parcel in) {
        super(in);
    }

    public static final Creator<TithingParcel> CREATOR = new Creator<TithingParcel>() {
        @Override
        public TithingParcel createFromParcel(Parcel in) {
            return new TithingParcel(in);
        }

        @Override
        public TithingParcel[] newArray(int size) {
            return new TithingParcel[size];
        }
    };

    @Override
    public ContentValues getContentValues() {
        //TODO need to make tithing table
        ContentValues contentValues = new ContentValues();

        contentValues.put(TithingTable.TITLE, mTitle);
        contentValues.put(TithingTable.AMOUNT, mAmount);
        contentValues.put(TithingTable.DATE, mDate);

        return contentValues;
    }

    @Override
    public void save(Context context) {
        context.getContentResolver().insert(Provider.TITHING_CONTENT_URI, getContentValues());
    }

    @Override
    public void update(Context context) {
        context.getContentResolver().update(Provider.TITHING_CONTENT_URI, getContentValues(), TithingTable.WHERE_ID_EQUALS, new String[]{mID.toString()});
    }

    @Override
    public void delete(Context context) {
        context.getContentResolver().delete(Provider.TITHING_CONTENT_URI, TithingTable.WHERE_ID_EQUALS, new String[]{mID.toString()});
    }
}
