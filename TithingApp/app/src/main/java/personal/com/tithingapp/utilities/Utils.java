package personal.com.tithingapp.utilities;

public class Utils {
    public static final String EMPTY_STRING = "";
    public static final Long EMPTY_LONG = 0L;

    private static final String DATE_SEPARATOR = "-";

    public static final int TITHING_INCOME_LOADER = 1;

    public static String getPersistableDate(int month, int day, int year) {
        String date = "";

        date += month + DATE_SEPARATOR + day + DATE_SEPARATOR + year;

        return date;
    }

    public static SimpleDate getSimpleDateFromPersistableDate(String persistableDate) {
        String[] dates = persistableDate.split(DATE_SEPARATOR);

        int month = Integer.parseInt(dates[0]);
        int day = Integer.parseInt(dates[1]);
        int year  = Integer.parseInt(dates[2]);

        return new SimpleDate(month, day, year);
    }

    public static class SimpleDate {
        public final int month;
        public final int day;
        public final int year;

        public SimpleDate(int month, int day, int year) {
            this.month = month;
            this.day = day;
            this.year = year;
        }
    }
}
