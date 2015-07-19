package personal.com.tithingapp;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

public class TabFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected IncomeListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ChangeTabListener mChangeTabListener;

    private CharSequence mTitle;

    public CharSequence getTitle() {
        return mTitle;
    }

    public TabFragment setTitle(CharSequence title) {
        mTitle = title;

        return this;
    }

    public void setTabChangeListener(ChangeTabListener tabChangeListener) {
        mChangeTabListener = tabChangeListener;
    }

    public interface ChangeTabListener {
        void replaceCurrentFragment(TabFragment currentFragment, TabFragment newFragment);
    }
}
