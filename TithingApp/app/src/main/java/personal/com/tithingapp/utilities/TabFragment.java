package personal.com.tithingapp.utilities;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

public class TabFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected IncomeListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    private CharSequence mTitle;
    private int mPosition;

    public CharSequence getTitle() {
        return mTitle;
    }

    public TabFragment setTitle(CharSequence title) {
        mTitle = title;

        return this;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }
}
