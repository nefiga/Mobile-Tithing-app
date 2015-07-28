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

public class EditIncomeTab extends DataFragment implements OnDateSetListener{

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

        mDateDialog = new DatePickerDialog(getActivity(), this, 2015, 4, 15);

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

        return containerView;
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
    public void setParcelData(Bundle data) {

    }
}
