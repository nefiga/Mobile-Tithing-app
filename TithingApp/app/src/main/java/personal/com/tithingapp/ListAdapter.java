package personal.com.tithingapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import personal.com.tithingapp.database.IncomeTable;
import personal.com.tithingapp.utilities.Utils;
import personal.com.tithingapp.utilities.Utils.SimpleDate;

public class ListAdapter extends CursorRecyclerViewAdapter<ViewHolder> implements FooterAdapter<ViewHolder>, SectionAdapter<ViewHolder> {

    public ListAdapter(Context context, Cursor cursor, OnListItemClickListener clickListener) {
        super(context, cursor, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        if (viewHolder instanceof IncomeViewHolder) {
            IncomeViewHolder incomeViewHolder = (IncomeViewHolder) viewHolder;

            incomeViewHolder.mTitle.setText(cursor.getString(cursor.getColumnIndex(IncomeTable.TITLE)));
            incomeViewHolder.mDate.setText(Utils.getDisplayDate(cursor.getString(cursor.getColumnIndex(IncomeTable.DATE))));
            incomeViewHolder.mAmount.setText(cursor.getString(cursor.getColumnIndex(IncomeTable.AMOUNT)));

            String notes = cursor.getString(cursor.getColumnIndex(IncomeTable.NOTES));
            if (notes != null)
                incomeViewHolder.mNotes.setText(notes);
        } else {
            throw new IllegalArgumentException("viewHolder must be a instance of IncomeViewHolder");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_view, parent, false);

        TextView title = (TextView) itemView.findViewById(R.id.title);
        TextView date = (TextView) itemView.findViewById(R.id.date);
        TextView amount = (TextView) itemView.findViewById(R.id.amount);
        EditText notes = (EditText) itemView.findViewById(R.id.notes);

        return new IncomeViewHolder(itemView, title, date, amount, notes);
    }

    @Override
    public ViewHolder onCreateFooter(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
        return new IncomeViewHolder(itemView, null, null, null, null);
    }

    @Override
    public void onBindFooter(ViewHolder viewHolder) {
        // Overriding FooterAdapter interface method. Nothing to do here since I want the footer to be invisible
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public ViewHolder onCreateSectionViewHolder(ViewGroup parent) {
        View sectionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_header, parent, false);
        TextView title = (TextView) sectionView.findViewById(R.id.title);

        return new SectionViewHolder(sectionView, title);
    }

    @Override
    public void onBindSectionViewHolder(ViewHolder viewHolder, Cursor cursor) {
        if (viewHolder instanceof SectionViewHolder) {
            SectionViewHolder sectionViewHolder = (SectionViewHolder) viewHolder;

            String date = cursor.getString(cursor.getColumnIndex(IncomeTable.DATE));
            SimpleDate simpleDate = Utils.getSimpleDateFromPersistableDate(date);

            sectionViewHolder.mTitle.setText(readableMonth(simpleDate.month) + "  " + simpleDate.year);
        }
    }

    private String readableMonth(int month) {
        switch (month) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
        }

        return "Not a month";
    }

    public class IncomeViewHolder extends ViewHolder {
        TextView mTitle;
        TextView mDate;
        TextView mAmount;
        EditText mNotes;

        public IncomeViewHolder(View itemView, TextView title, TextView date, TextView amount, EditText notes) {
            super(itemView);
            mTitle = title;
            mDate = date;
            mAmount = amount;
            mNotes = notes;
        }
    }

    public class SectionViewHolder extends ViewHolder {
        TextView mTitle;

        public SectionViewHolder(View itemView, TextView title) {
            super(itemView);
            mTitle = title;
        }
    }


    public interface OnListItemClickListener {
        void onItemClick(View view, long id);
    }
}
