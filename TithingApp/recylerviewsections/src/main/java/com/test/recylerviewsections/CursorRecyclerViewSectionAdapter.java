package com.test.recylerviewsections;

import java.util.Arrays;
import java.util.List;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;

public abstract class CursorRecyclerViewSectionAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerViewSectionAdapter<VH> implements SectionAdapter<VH> {

    private Cursor mCursor;

    private boolean mDataValid;

    private int mRowIdColumn;

    private DataSetObserver mDataSetObserver;

    public CursorRecyclerViewSectionAdapter(Cursor cursor) {
        mCursor = cursor;
        mDataValid = cursor != null;
        mRowIdColumn = mDataValid ? mCursor.getColumnIndex("_id") : -1;
        mDataSetObserver = new NotifyingDataSetObserver();
        if (mCursor != null)
            mCursor.registerDataSetObserver(mDataSetObserver);
    }

    @Override
    public long getItemId(int position) {
        if (mDataValid && mCursor != null && mCursor.moveToPosition(position))
            return mCursor.getLong(mRowIdColumn);

        return RecyclerView.NO_ID;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (!mDataValid)
            throw new IllegalStateException("This should only be called when the cursor is valid");

        if (position < 0 || position > mViewTypes.length)
            throw new IllegalStateException("No view type found for position " + position);

        if (isFooter(position)) {
            mFooterHandler.onBindFooter(holder);
        } else if (mViewTypes[position] == DEFAULT_VIEW_TYPE) {
            mCursor.moveToPosition(position - getSectionOffsetForPosition(position));
            onBindViewHolder(holder, mCursor);
        } else if (mViewTypes[position] == SECTION_VIEW_TYPE) {
            mCursor.moveToPosition(position - getSectionOffsetForPosition(position));
            mSectionHandler.onBindSectionViewHolder(holder, mCursor);
        } else {
            throw new IllegalStateException("No view type found for position " + position);
        }
    }

    @Override
    public void setViewTypes() {
        if (mSectionHandler != null) {
            List<Integer> sectionPositions = mSectionHandler.getSectionPositions(mCursor);

            mViewTypes = new int[mCursor.getCount() + sectionPositions.size()];
            Arrays.fill(mViewTypes, DEFAULT_VIEW_TYPE);

            int sectionOffset = 0;
            for (int i = 0; i < sectionPositions.size(); i++) {
                mViewTypes[sectionPositions.get(i) + sectionOffset] = SECTION_VIEW_TYPE;
                sectionOffset++;
            }
        } else {
            mViewTypes = new int[mCursor.getCount()];
            Arrays.fill(mViewTypes, DEFAULT_VIEW_TYPE);
        }
    }

    private class NotifyingDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            mDataValid = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            mDataValid = false;
            notifyDataSetChanged();
        }
    }

    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);
}
