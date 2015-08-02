package personal.com.tithingapp.models;

public class EditIncomeModel {

    public boolean validateTitle(String title) {
        String expression = "^(?!\\s*$).+";

        return title.matches(expression);
    }

    public boolean validateAmount(String amountString) {
        boolean valid = false;
        String expression = "^(\\d*(\\d\\.?|\\.\\d{1,2}))";

        if (amountString.matches(expression))
            valid = true;

        return valid;
    }
}
