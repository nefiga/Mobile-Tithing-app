package personal.com.tithingapp.parcels;

import android.os.Parcel;
import android.os.Parcelable;

public class IncomeParcel implements Parcelable{

    private String mTitle;
    private int mAmount;
    private String mDate;

    public IncomeParcel() {
        // Default constructor
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getAmount() {
        return mAmount;
    }

    public String getDate() {
        return mDate;
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
        dest.writeString(mTitle);
        dest.writeInt(mAmount);
        dest.writeString(mDate);
    }

    private void readFromParcel(Parcel parcel) {
        mTitle = parcel.readString();
        mAmount = parcel.readInt();
        mDate = parcel.readString();
    }
}
