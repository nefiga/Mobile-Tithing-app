package com.test.recylerviewsections;

import android.support.v7.widget.RecyclerView;

public interface SectionAdapter<VH extends RecyclerView.ViewHolder> {
    boolean isNormalView(int position);
    boolean isFooter(int position);

    void setViewTypes();

    int getSectionOffsetForPosition(int position);

    void enableFooter(FooterHandler<VH> footerHandler);
    void enableSections(SectionHandler<VH> sectionHandler);
}
