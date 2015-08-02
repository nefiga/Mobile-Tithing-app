package personal.com.tithingapp.tabs;

import android.support.v4.app.Fragment;

public class TabFragment extends Fragment {

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
