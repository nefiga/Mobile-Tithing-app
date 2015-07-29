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
import android.widget.TextView;

import java.util.Calendar;

import personal.com.tithingapp.parcels.IncomeParcel;
import personal.com.tithingapp.services.ServiceHelper;
import personal.com.tithingapp.utilities.Utils;
import personal.com.tithingapp.utilities.Utils.SimpleDate;

public class EditIncomeTab extends DataFragment implements OnDateSetListener {

    private IncomeParcel mIncomeParcel;

    private EditText mTitle;
    private EditText mAmount;
    private Button mSave;
    private Button mCancel;
    private DatePickerDialog mDateDialog;
    private TextView mDateView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View containerView = inflater.inflate(R.layout.edit_income, container, false);

        mTitle = (EditText) containerView.findViewById(R.id.title);
        mAmount = (EditText) containerView.findViewById(R.id.amount);
        mSave = (Button) containerView.findViewById(R.id.save);
        mCancel = (Button) containerView.findViewById(R.id.cancel);
        mDateView = (TextView) containerView.findViewById(R.id.date_view);

        if (mIncomeParcel == null) {
            mIncomeParcel = new IncomeParcel();
            instantiateDateDialogWithCurrentDate();
        }
        else {
            populateGUI();
        }

        setListeners();

        return containerView;
    }

    private void populateGUI() {
        mTitle.setText(mIncomeParcel.getTitle());
        mAmount.setText(Float.toString(mIncomeParcel.getAmount()));
        populateDateDialogWithDateFromParcel();
    }

    private void populateParcel() {
        mIncomeParcel.setTitle(mTitle.getText().toString());
        mIncomeParcel.setAmount(Float.parseFloat(mAmount.getText().toString()));

        DatePicker datePicker = mDateDialog.getDatePicker();
        mIncomeParcel.setDate(Utils.getPersitableDate(datePicker.getMonth(), datePicker.getDayOfMonth(), datePicker.getYear()));
    }

    private void save() {
        populateParcel();

        if (mIncomeParcel.hasID())
            ServiceHelper.updatePersistable(getActivity(), mIncomeParcel);
        else
            ServiceHelper.savePersistable(getActivity(), mIncomeParcel);

        mChangeTabListener.replaceCurrentFragment(this, new IncomeListTab());
    }

    private void cancel() {
        mChangeTabListener.replaceCurrentFragment(this, new IncomeListTab());
    }

    private void showDatePickerDialog() {
        SimpleDate simpleDate = Utils.getSimpleDateFromPersistableDate(mIncomeParcel.getDate());

        mDateDialog = new DatePickerDialog(getActivity(), this, simpleDate.year, simpleDate.month, simpleDate.day);
        mDateDialog.show();
    }

    private void instantiateDateDialogWithCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        mIncomeParcel.setDate(Utils.getPersitableDate(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR)));
        mDateDialog = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mDateView.setText(mIncomeParcel.getDate());
    }

    private void populateDateDialogWithDateFromParcel() {
        SimpleDate simpleDate = Utils.getSimpleDateFromPersistableDate(mIncomeParcel.getDate());
        mDateDialog = new DatePickerDialog(getActivity(), this, simpleDate.year, simpleDate.month, simpleDate.day);
        mDateView.setText(mIncomeParcel.getDate());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mIncomeParcel.setDate(Utils.getPersitableDate(monthOfYear, dayOfMonth, year));
        mDateView.setText(mIncomeParcel.getDate());
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
                cancel();
            }
        });

        mDateView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }
}
