package personal.com.tithingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class EditIncomeTab extends TabFragment {

    private RelativeLayout mMainView;
    private EditText mTitle;
    private EditText mAmount;
    private Button mSave;
    private Button mCancel;
    private DatePicker mDatePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View containerView = inflater.inflate(R.layout.edit_income, container, false);

        mMainView = (RelativeLayout) containerView.findViewById(R.id.main_view);
        mTitle = (EditText) containerView.findViewById(R.id.title);
        mAmount = (EditText) containerView.findViewById(R.id.amount);
        mSave = (Button) containerView.findViewById(R.id.save);
        mCancel = (Button) containerView.findViewById(R.id.cancel);
        mDatePicker = (DatePicker) containerView.findViewById(R.id.date_picker);

        mSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        mCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDatePicker();
            }
        });

        return containerView;
    }

    private void save() {
        mChangeTabListener.replaceCurrentFragment(this, new IncomeListTab());
    }

    private void toggleDatePicker() {
        if (mDatePicker.getVisibility() == View.VISIBLE) {
            mDatePicker.setVisibility(View.GONE);
            mMainView.setVisibility(View.VISIBLE);
        } else {
            mDatePicker.setVisibility(View.VISIBLE);
            mMainView.setVisibility(View.GONE);
        }
    }
}
