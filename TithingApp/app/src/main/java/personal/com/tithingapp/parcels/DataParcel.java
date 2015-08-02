package personal.com.tithingapp.parcels;

import android.os.Parcel;
import personal.com.tithingapp.database.Persistable;
import personal.com.tithingapp.utilities.Utils;

public abstract class DataParcel implements Persistable {
    public static final String NAME = DataParcel.class.getName();

    protected Long mID = Utils.EMPTY_LONG;
    protected String mTitle;
    protected float mAmount;
    protected String mDate;

    public DataParcel() {
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

    protected DataParcel(Parcel in) {
        readFromParcel(in);
    }

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
}
