package personal.com.tithingapp.utilities;

public class Utils {
    public static final int MONTH_OFFSET = 1;
    public static final String EMPTY_STRING = "";
    public static final Long EMPTY_LONG = 0L;

    private static final String DATE_SEPARATOR = "/";

    public static final int TITHING_INCOME_LOADER = 1;

    public static String getPersistableDate(int month, int day, int year) {
        return String.format("%02d/%02d/%d", month, day, year);
    }

    public static String getDisplayDate(String date) {
        String[] dates = date.split(DATE_SEPARATOR);

        int month = Integer.parseInt(dates[0]) + MONTH_OFFSET;
        int day = Integer.parseInt(dates[1]);
        int year  = Integer.parseInt(dates[2]);

        return String.format("%02d/%02d/%d", month, day, year);
    }

    public static SimpleDate getSimpleDateFromPersistableDate(String persistableDate) {
        String[] dates = persistableDate.split(DATE_SEPARATOR);

        int month = Integer.parseInt(dates[0]);
        int day = Integer.parseInt(dates[1]);
        int year  = Integer.parseInt(dates[2]);

        return new SimpleDate(month, day, year);
    }
}
