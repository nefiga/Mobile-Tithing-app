package personal.com.tithingapp.utilities;

public class Utils {
    public static final int MONTH_OFFSET = 1;
    public static final String EMPTY_STRING = "";
    public static final Long EMPTY_LONG = 0L;
    public static final int HUNDREDTH = 100;
    public static final int TENTH = 10;

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

    public static String getDisplayableAmount(int amount) {
        int dollars = amount / HUNDREDTH;
        amount = amount % HUNDREDTH;
        int firstDecimal = amount / TENTH;
        amount = amount % TENTH;
        int secondDecimal = amount;

        return String.format("%d.%d%d", dollars, firstDecimal, secondDecimal);
    }

    public static int getPersistableAmount(String amount) {
        String[] currency = amount.split("\\.");

        int persistableAmount = Integer.valueOf(currency[0]) * HUNDREDTH;

        if (currency.length  == 2) {
            persistableAmount += Integer.valueOf(currency[1].substring(0, 1)) * TENTH;
            persistableAmount += Integer.valueOf(currency[1].substring(1));
        }

        return persistableAmount;
    }
}
