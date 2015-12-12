package com.test.recylerviewsections;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface FooterHandler<VH extends RecyclerView.ViewHolder> {

    VH onCreateFooter(ViewGroup parent);

    /**
     * Use this method if you want to put something in the footer
     */
    void onBindFooter(VH viewHolder);
}