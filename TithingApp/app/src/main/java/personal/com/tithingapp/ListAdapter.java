package personal.com.tithingapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import personal.com.tithingapp.ListAdapter.IncomeViewHolder;
import personal.com.tithingapp.database.IncomeTable;
import personal.com.tithingapp.utilities.Utils;

public class ListAdapter extends CursorRecyclerViewAdapter<IncomeViewHolder> {

    public ListAdapter(Context context, Cursor cursor, OnListItemClickListener clickListener) {
        super(context, cursor, clickListener);
    }

    @Override
    public void onBindViewHolder(IncomeViewHolder viewHolder, Cursor cursor) {
        viewHolder.mTitle.setText(cursor.getString(cursor.getColumnIndex(IncomeTable.TITLE)));
        viewHolder.mDate.setText(Utils.getDisplayDate(cursor.getString(cursor.getColumnIndex(IncomeTable.DATE))));
        viewHolder.mAmount.setText(cursor.getString(cursor.getColumnIndex(IncomeTable.AMOUNT)));

        String notes = cursor.getString(cursor.getColumnIndex(IncomeTable.NOTES));
        if (notes != null)
            viewHolder.mNotes.setText(notes);
    }

    @Override
    public IncomeViewHolder onCreateNormalViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_view, parent, false);

        TextView title = (TextView) itemView.findViewById(R.id.title);
        TextView date = (TextView) itemView.findViewById(R.id.date);
        TextView amount = (TextView) itemView.findViewById(R.id.amount);
        EditText notes = (EditText) itemView.findViewById(R.id.notes);

        return new IncomeViewHolder(itemView, title, date, amount, notes);
    }

    @Override
    public IncomeViewHolder onCreateFooter(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
        return new IncomeViewHolder(itemView, null, null, null, null);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

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

    public interface OnListItemClickListener {
        void onItemClick(View view, long id);
    }
}
