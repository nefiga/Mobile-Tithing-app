package personal.com.tithingapp.utilities;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import personal.com.tithingapp.R;
import personal.com.tithingapp.database.IncomeTable;

public class IncomeListAdapter extends CursorRecyclerViewAdapter<IncomeListAdapter.ViewHolder> {

    public IncomeListAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        viewHolder.mTitle.setText(cursor.getString(cursor.getColumnIndex(IncomeTable.TITLE)));
        viewHolder.mDate.setText(cursor.getString(cursor.getColumnIndex(IncomeTable.DATE)));
        viewHolder.mAmount.setText(cursor.getString(cursor.getColumnIndex(IncomeTable.AMOUNT)));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_list_view, parent, false);
        TextView title = (TextView) itemView.findViewById(R.id.title);
        TextView date = (TextView) itemView.findViewById(R.id.date);
        TextView amount = (TextView) itemView.findViewById(R.id.amount);
        return new ViewHolder(itemView, title, date, amount);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mDate;
        TextView mAmount;

        public ViewHolder(View itemView, TextView title, TextView date, TextView amount) {
            super(itemView);
            mTitle = title;
            mDate = date;
            mAmount = amount;
        }
    }
}
