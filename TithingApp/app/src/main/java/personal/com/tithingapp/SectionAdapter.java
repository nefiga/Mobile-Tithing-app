package personal.com.tithingapp;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface SectionAdapter<VH extends RecyclerView.ViewHolder> {

    VH onCreateSectionViewHolder(ViewGroup parent);

    void onBindSectionViewHolder(VH viewHolder, Cursor cursor);
}
