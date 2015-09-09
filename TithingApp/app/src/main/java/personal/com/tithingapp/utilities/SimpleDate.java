package personal.com.tithingapp.utilities;

public class SimpleDate {
    public final int month;
    public final int day;
    public final int year;

    public SimpleDate(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    public String getReadableMonth() {
        switch (month) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
        }

        return "Not a month";
    }
}
