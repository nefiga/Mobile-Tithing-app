package personal.com.tithingapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import personal.com.tithingapp.ListAdapter.OnListItemClickListener;
import personal.com.tithingapp.database.IncomeTable;
import personal.com.tithingapp.database.TableBuilder;
import personal.com.tithingapp.utilities.Utils;
import personal.com.tithingapp.utilities.Utils.SimpleDate;

public abstract class CursorRecyclerViewAdapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> implements RecyclerView.OnItemTouchListener {
    private final int FOOTER_COUNT = 1;
    private final int NO_ITEMS = 0;

    public static final int HEADER_VIEW_TYPE = 0;
    public static final int NORMAL_VIEW_TYPE = 1;
    public static final int FOOTER_VIEW_TYPE = 2;
    public static final int SECTION_VIEW_TYPE = 3;

    private int[] mViewTypes;

    private Cursor mCursor;

    private boolean mDataValid;

    private int mRowIdColumn;

    private DataSetObserver mDataSetObserver;

    private OnListItemClickListener mClickListener;
    private GestureDetector mGestureDetector;

    private FooterAdapter<VH> mFooterAdapter;
    private SectionAdapter<VH> mSectionAdapter;

    public CursorRecyclerViewAdapter(Context context, Cursor cursor, OnListItemClickListener clickListener) {
        mCursor = cursor;
        mDataValid = cursor != null;
        mRowIdColumn = mDataValid ? mCursor.getColumnIndex(TableBuilder.ID) : -1;
        mDataSetObserver = new NotifyingDataSetObserver();
        mGestureDetector = new GestureDetector(context, new SimpleTouchListener());
        mClickListener = clickListener;
        if (mCursor != null)
            mCursor.registerDataSetObserver(mDataSetObserver);
    }

    @Override
    public int getItemCount() {
        if (mViewTypes != null)
            return mViewTypes.length;

        return NO_ITEMS;
    }

    @Override
    public long getItemId(int position) {
        if (mDataValid && mCursor != null && mCursor.moveToPosition(position))
            return mCursor.getLong(mRowIdColumn);

        return RecyclerView.NO_ID;
    }

    @Override
    public int getItemViewType(int position) {
        return mViewTypes[position];
    }

    public boolean isHeaderOrFooter(int position) {
        int viewType = getItemViewType(position);

        return viewType == FOOTER_VIEW_TYPE || viewType == HEADER_VIEW_TYPE;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_VIEW_TYPE)
            return mFooterAdapter.onCreateFooter(parent);
        else if (viewType == SECTION_VIEW_TYPE)
            return mSectionAdapter.onCreateSectionViewHolder(parent);

        return onCreateViewHolder(parent);
    }

    public abstract VH onCreateViewHolder(ViewGroup parent);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (!mDataValid)
            throw new IllegalStateException("This should only be called when the cursor is valid");

        if (position < 0 || position >= mViewTypes.length)
            throw new IllegalStateException("No view type found for position " + position);
        if (mViewTypes[position] == NORMAL_VIEW_TYPE) {
            mCursor.moveToPosition(position - sectionOffsetForPosition(position));
            onBindViewHolder(holder, mCursor);
        }
        else if (mViewTypes[position] == FOOTER_VIEW_TYPE) {
            mFooterAdapter.onBindFooter(holder);
        }
        else if (mViewTypes[position] == SECTION_VIEW_TYPE) {
            mCursor.moveToPosition(position - sectionOffsetForPosition(position));
            mSectionAdapter.onBindSectionViewHolder(holder, mCursor);
        }
        else {
            throw new IllegalStateException("No view type found for position " + position);
        }
    }

    public void enableFooter(FooterAdapter<VH> footerAdapter) {
        mFooterAdapter = footerAdapter;
    }

    public void enableSections(SectionAdapter<VH> sectionAdapter) {
        mSectionAdapter = sectionAdapter;
    }

    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null)
            old.close();
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor)
            return null;

        final Cursor oldCursor = mCursor;
        if (oldCursor != null && mDataSetObserver != null)
            oldCursor.unregisterDataSetObserver(mDataSetObserver);

        mCursor = newCursor;
        if (mCursor != null) {
            if (mSectionAdapter != null)
                setUpSections();

            mCursor.registerDataSetObserver(mDataSetObserver);

            mRowIdColumn = newCursor.getColumnIndex(TableBuilder.ID);
            mDataValid = true;
            notifyDataSetChanged();
        } else {
            mRowIdColumn = -1;
            mDataValid = false;
            notifyDataSetChanged();
        }

        return oldCursor;
    }

    private void setUpSections() {
        List<SimpleDate> dates = new ArrayList<>();
        List<Integer> section = new ArrayList<>();

        if (mCursor.moveToFirst()) {
            dates.add(Utils.getSimpleDateFromPersistableDate(mCursor.getString(mCursor.getColumnIndex(IncomeTable.DATE))));

            while (mCursor.moveToNext()) {
                dates.add(Utils.getSimpleDateFromPersistableDate(mCursor.getString(mCursor.getColumnIndex(IncomeTable.DATE))));
            }
        }

        int sections = 0;
        for (int i = 0; i < dates.size(); i++) {
            if (i == 0) {
                section.add(i + sections);
                sections++;
            } else if (dates.get(i).month > dates.get(i -1).month) {
                section.add(i + sections);
                sections++;
            }
        }

        mViewTypes = new int[dates.size() + section.size() + (mFooterAdapter != null ? FOOTER_COUNT : 0)];

        for (int i = 0; i < mViewTypes.length; i++) {
            if (i == mViewTypes.length -1 && mFooterAdapter != null)
                mViewTypes[i] = FOOTER_VIEW_TYPE;
            else if (section.contains(i))
                mViewTypes[i] = SECTION_VIEW_TYPE;
            else
                mViewTypes[i] = NORMAL_VIEW_TYPE;

        }
    }

    private int sectionOffsetForPosition(int position) {
        int sections = 0;

        for (int i = 0; i < position; i++) {
            if (mViewTypes[i] == SECTION_VIEW_TYPE)
                sections++;
        }

        return sections;
    }

    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);

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

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

        if (mClickListener != null && childView != null &&  mGestureDetector.onTouchEvent(motionEvent)) {
            int position = recyclerView.getChildPosition(childView);

            if (!isHeaderOrFooter(position))
                mClickListener.onItemClick(childView, getItemId(position));

            return true;
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

    }

    private class SimpleTouchListener extends SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }
    }
}
