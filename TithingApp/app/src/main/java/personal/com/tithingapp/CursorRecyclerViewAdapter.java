package personal.com.tithingapp;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import personal.com.tithingapp.IncomeListAdapter.OnListItemClickListener;

public abstract class CursorRecyclerViewAdapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH> implements RecyclerView.OnItemTouchListener {
    private final int FOOTER_COUNT = 1;
    private final int NO_ITEMS = 0;

    public static final int HEADER_VIEW_TYPE = 0;
    public static final int NORMAL_VIEW_TYPE = 1;
    public static final int FOOTER_VIEW_TYPE = 2;

    private Context mContext;

    private Cursor mCursor;

    private boolean mDataValid;

    private int mRowIdColumn;

    private DataSetObserver mDataSetObserver;

    private OnListItemClickListener mClickListener;
    private GestureDetector mGestureDetector;

    private boolean mDisplayFooter;

    public CursorRecyclerViewAdapter(Context context, Cursor cursor, OnListItemClickListener clickListener) {
        mContext = context;
        mCursor = cursor;
        mDataValid = cursor != null;
        mRowIdColumn = mDataValid ? mCursor.getColumnIndex("_id") : -1;
        mDataSetObserver = new NotifyingDataSetObserver();
        mGestureDetector = new GestureDetector(context, new TabListGestureListener());
        mClickListener = clickListener;
        if (mCursor != null)
            mCursor.registerDataSetObserver(mDataSetObserver);
    }

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public int getItemCount() {
        if (mDataValid && mCursor != null) {
            if (mDisplayFooter)
                return mCursor.getCount() + FOOTER_COUNT;

            return mCursor.getCount();
        }

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
        if (mCursor != null && position >= mCursor.getCount())
            return FOOTER_VIEW_TYPE;

        return NORMAL_VIEW_TYPE;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_VIEW_TYPE)
            return onCreateFooter(parent);

        return onCreateNormalViewHolder(parent);
    }

    public abstract VH onCreateNormalViewHolder(ViewGroup parent);

    public abstract VH onCreateFooter(ViewGroup parent);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (!mDataValid)
            throw new IllegalStateException("This should only be called when the cursor is valid");

        if (!mCursor.moveToPosition(position) && !mDisplayFooter && position != mCursor.getCount())
            throw new IllegalStateException("Couldn't move cursor to position " + position);

        if (position < mCursor.getCount())
            onBindViewHolder(holder, mCursor);
        else
            onBindFooter(holder);
    }

    public void enableFooter() {
        mDisplayFooter = true;
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
            mCursor.registerDataSetObserver(mDataSetObserver);

            mRowIdColumn = newCursor.getColumnIndex("_id");
            mDataValid = true;
            notifyDataSetChanged();
        } else {
            mRowIdColumn = -1;
            mDataValid = false;
            notifyDataSetChanged();
        }

        return oldCursor;
    }

    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);

    /**
     * Override this method if you want to put something in the footer
     */
    public void onBindFooter(VH viewHolder) {}

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
            mClickListener.onClick(childView, recyclerView.getChildPosition(childView));

            return true;
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

    }
}
