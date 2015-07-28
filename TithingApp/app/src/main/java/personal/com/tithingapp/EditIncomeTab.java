package personal.com.tithingapp;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
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

import personal.com.tithingapp.parcels.IncomeParcel;

public class EditIncomeTab extends DataFragment implements OnDateSetListener{

    private IncomeParcel mIncomeParcel;

    private RelativeLayout mMainView;
    private EditText mTitle;
    private EditText mAmount;
    private Button mSave;
    private Button mCancel;
    private DatePickerDialog mDateDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View containerView = inflater.inflate(R.layout.edit_income, container, false);

        mMainView = (RelativeLayout) containerView.findViewById(R.id.main_view);
        mTitle = (EditText) containerView.findViewById(R.id.title);
        mAmount = (EditText) containerView.findViewById(R.id.amount);
        mSave = (Button) containerView.findViewById(R.id.save);
        mCancel = (Button) containerView.findViewById(R.id.cancel);

        setListeners();

        populateGUI();

        return containerView;
    }

    private void populateGUI() {
        if (mIncomeParcel != null) {
            mTitle.setText(mIncomeParcel.getTitle());
            mAmount.setText(Integer.toString(mIncomeParcel.getAmount()));
        }
    }

    private void save() {
        mChangeTabListener.replaceCurrentFragment(this, new IncomeListTab());
    }

    private void showDatePickerDialog() {
        mDateDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        showDatePickerDialog();
    }

    @Override
    public void setData(Bundle data) {
        if (data.containsKey(IncomeParcel.NAME))
            mIncomeParcel = data.getParcelable(IncomeParcel.NAME);
    }

    private void setListeners() {
        mSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        mCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }
}
