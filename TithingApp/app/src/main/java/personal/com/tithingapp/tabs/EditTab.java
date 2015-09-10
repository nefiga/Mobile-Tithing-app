package personal.com.tithingapp.tabs;

import java.util.Calendar;

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
import personal.com.tithingapp.DataFragment;
import personal.com.tithingapp.R;
import personal.com.tithingapp.models.EditModel;
import personal.com.tithingapp.parcels.DataParcel;
import personal.com.tithingapp.services.ServiceHelper;
import personal.com.tithingapp.utilities.Utils;
import personal.com.tithingapp.utilities.SimpleDate;

public abstract class EditTab extends DataFragment implements OnDateSetListener {

    protected DataParcel mDataParcel;
    protected EditModel mModel;

    protected EditText mTitle;
    protected EditText mAmount;
    protected Button mSave;
    protected Button mCancel;
    protected DatePickerDialog mDateDialog;
    protected TextView mDateView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View containerView = inflater.inflate(R.layout.edit_income, container, false);

        mModel = new EditModel();

        mTitle = (EditText) containerView.findViewById(R.id.title);
        mAmount = (EditText) containerView.findViewById(R.id.amount);
        mSave = (Button) containerView.findViewById(R.id.save);
        mCancel = (Button) containerView.findViewById(R.id.cancel);
        mDateView = (TextView) containerView.findViewById(R.id.date_view);

        if (mDataParcel == null) {
            mDataParcel = getNewDataParcel();
            instantiateDateDialogWithCurrentDate();
        }
        else {
            populateGUIFromParcel();
        }

        setListeners();

        return containerView;
    }

    private void populateGUIFromParcel() {
        mTitle.setText(mDataParcel.getTitle());
        mAmount.setText(Utils.getDisplayableAmount(mDataParcel.getAmount()));
        populateDateDialogWithDateFromParcel();
    }

    private void populateParcel() {
        mDataParcel.setTitle(mTitle.getText().toString());
        mDataParcel.setAmount(Utils.getPersistableAmount(mAmount.getText().toString()));

        DatePicker datePicker = mDateDialog.getDatePicker();
        mDataParcel.setDate(Utils.getPersistableDate(datePicker.getMonth(), datePicker.getDayOfMonth(), datePicker.getYear()));
    }

    protected void save() {
        if (validate()) {
            populateParcel();

            if (mDataParcel.hasID())
                ServiceHelper.updatePersistable(getActivity(), mDataParcel);
            else
                ServiceHelper.savePersistable(getActivity(), mDataParcel);

            returnToListView();
        }
    }

    private void cancel() {
        returnToListView();
    }

    protected abstract void returnToListView();

    protected abstract DataParcel getNewDataParcel();

    private boolean validate() {
        boolean valid = true;

        if (!mModel.validateTitle(mTitle.getText().toString())) {
            valid = false;
            mTitle.setError("Title must not be blank");
        }

        if (!mModel.validateAmount(mAmount.getText().toString())) {
            valid = false;
            mAmount.setError("Invalid amount");
        }

        return valid;
    }

    private void showDatePickerDialog() {
        SimpleDate simpleDate = Utils.getSimpleDateFromPersistableDate(mDataParcel.getDate());

        mDateDialog = new DatePickerDialog(getActivity(), this, simpleDate.year, simpleDate.month, simpleDate.day);
        mDateDialog.show();
    }

    private void instantiateDateDialogWithCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        mDataParcel.setDate(Utils.getPersistableDate(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR)));
        mDateDialog = new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mDateView.setText(Utils.getDisplayDateFromPersistableDate(mDataParcel.getDate()));
    }

    private void populateDateDialogWithDateFromParcel() {
        SimpleDate simpleDate = Utils.getSimpleDateFromPersistableDate(mDataParcel.getDate());
        mDateDialog = new DatePickerDialog(getActivity(), this, simpleDate.year, simpleDate.month, simpleDate.day);
        mDateView.setText(Utils.getDisplayDateFromPersistableDate(mDataParcel.getDate()));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mDataParcel.setDate(Utils.getPersistableDate(monthOfYear, dayOfMonth, year));
        mDateView.setText(Utils.getDisplayDateFromPersistableDate(mDataParcel.getDate()));
    }

    @Override
    public void setData(Bundle data) {
        if (data.containsKey(DataParcel.NAME))
            mDataParcel = data.getParcelable(DataParcel.NAME);
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
