package com.test.recylerviewsections;

import java.util.List;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface SectionHandler<VH extends RecyclerView.ViewHolder> {

    VH onCreateSectionViewHolder(ViewGroup parent);

    void onBindSectionViewHolder(VH viewHolder, Cursor cursor);

    List<Integer> getSectionPositions(Cursor cursor);
}
