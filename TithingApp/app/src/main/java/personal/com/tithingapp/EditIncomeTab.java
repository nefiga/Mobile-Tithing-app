package personal.com.tithingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EditIncomeTab extends TabFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_income, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("Resuming", "Resuming");
    }
}
