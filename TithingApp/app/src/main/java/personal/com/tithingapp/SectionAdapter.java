package personal.com.tithingapp;

import android.database.Cursor;
import android.view.ViewGroup;

public interface SectionAdapter<VH extends ViewHolder> {

    VH onCreateSectionViewHolder(ViewGroup parent);

    void onBindSectionViewHolder(VH viewHolder, Cursor cursor);
}
