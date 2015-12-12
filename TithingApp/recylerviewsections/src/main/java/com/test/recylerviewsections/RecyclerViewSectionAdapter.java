package com.test.recylerviewsections;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class RecyclerViewSectionAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements SectionAdapter<VH> {

    protected int FOOTER_COUNT = 1;
    protected int NO_ITEMS = 0;

    protected int DEFAULT_VIEW_TYPE = 0;
    protected int FOOTER_VIEW_TYPE = 2;
    protected int SECTION_VIEW_TYPE = 3;

    protected int[] mViewTypes;

    protected FooterHandler<VH> mFooterHandler;
    protected SectionHandler<VH> mSectionHandler;

    @Override
    public int getItemCount() {
        if (mViewTypes != null) {
            if (mFooterHandler != null)
                return mViewTypes.length + FOOTER_COUNT;

            return mViewTypes.length;
        }

        return NO_ITEMS;
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooter(position))
            return FOOTER_VIEW_TYPE;

        return mViewTypes[position];
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_VIEW_TYPE)
            return mFooterHandler.onCreateFooter(parent);
        else if (viewType == SECTION_VIEW_TYPE)
            return mSectionHandler.onCreateSectionViewHolder(parent);

        return onCreateViewHolder(parent);
    }

    public abstract VH onCreateViewHolder(ViewGroup parent);

    public boolean isNormalView(int position) {
        return getItemViewType(position) == DEFAULT_VIEW_TYPE;
    }

    public boolean isFooter(int position) {
        return position == mViewTypes.length && mFooterHandler!= null;
    }

    public int getSectionOffsetForPosition(int position) {
        int sections = 0;

        for (int i = 0; i < position; i++) {
            if (mViewTypes[i] == SECTION_VIEW_TYPE)
                sections++;
        }

        return sections;
    }

    public void enableFooter(FooterHandler<VH> footerAdapter) {
        mFooterHandler = footerAdapter;
    }

    public void enableSections(SectionHandler<VH> sectionAdapter) {
        mSectionHandler = sectionAdapter;
    }
}
