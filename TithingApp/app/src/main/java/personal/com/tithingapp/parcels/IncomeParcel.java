package personal.com.tithingapp.parcels;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcel;

import personal.com.tithingapp.database.IncomeTable;
import personal.com.tithingapp.database.Persistable;
import personal.com.tithingapp.database.Provider;
import personal.com.tithingapp.utilities.Utils;

public class IncomeParcel implements Persistable {
    public static final String NAME = IncomeParcel.class.getName();

    private Long mID = Utils.EMPTY_LONG;
    private String mTitle;
    private float mAmount;
    private String mDate;

    public IncomeParcel() {
        // Default constructor
    }

    public void setID(long id) {
        mID = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setAmount(float amount) {
        mAmount = amount;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public long getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public float getAmount() {
        return mAmount;
    }

    public String getDate() {
        return mDate;
    }

    public boolean hasID() {
        return !mID.equals(Utils.EMPTY_LONG);
    }

    protected IncomeParcel(Parcel in) {
        readFromParcel(in);
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mID);
        dest.writeString(mTitle);
        dest.writeFloat(mAmount);
        dest.writeString(mDate);
    }

    private void readFromParcel(Parcel parcel) {
        mID = parcel.readLong();
        mTitle = parcel.readString();
        mAmount = parcel.readFloat();
        mDate = parcel.readString();
    }

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
