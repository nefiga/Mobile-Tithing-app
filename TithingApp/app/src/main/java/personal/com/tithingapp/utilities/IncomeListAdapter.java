package personal.com.tithingapp.utilities;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import personal.com.tithingapp.R;
import personal.com.tithingapp.database.IncomeTable;
import personal.com.tithingapp.utilities.IncomeListAdapter.IncomeViewHolder;
import personal.com.tithingapp.utilities.ViewHolder.ViewHolderOnClickListener;

public class IncomeListAdapter extends CursorRecyclerViewAdapter<IncomeViewHolder> {

    private ViewHolderOnClickListener mViewClickedListener;

    public IncomeListAdapter(Context context, Cursor cursor, ViewHolderOnClickListener clickedListener) {
        super(context, cursor);
        mViewClickedListener = clickedListener;
    }

    @Override
    public void onBindViewHolder(IncomeViewHolder viewHolder, Cursor cursor) {
        viewHolder.mTitle.setText(cursor.getString(cursor.getColumnIndex(IncomeTable.TITLE)));
        viewHolder.mDate.setText(cursor.getString(cursor.getColumnIndex(IncomeTable.DATE)));
        viewHolder.mAmount.setText(cursor.getString(cursor.getColumnIndex(IncomeTable.AMOUNT)));
    }

    @Override
    public IncomeViewHolder onCreateNormalViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_list_view, parent, false);

        TextView title = (TextView) itemView.findViewById(R.id.title);
        TextView date = (TextView) itemView.findViewById(R.id.date);
        TextView amount = (TextView) itemView.findViewById(R.id.amount);

        IncomeViewHolder viewHolder = new IncomeViewHolder(itemView, title, date, amount);
        viewHolder.setOnClickListener(mViewClickedListener);

        return viewHolder;
    }

    @Override
    public IncomeViewHolder onCreateFooter(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
        return new IncomeViewHolder(itemView, null, null, null);
    }

    public class IncomeViewHolder extends ViewHolder {
        TextView mTitle;
        TextView mDate;
        TextView mAmount;

        public IncomeViewHolder(View itemView, TextView title, TextView date, TextView amount) {
            super(itemView);
            mTitle = title;
            mDate = date;
            mAmount = amount;
        }
    }
}
