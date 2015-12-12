package personal.com.tithingapp.views;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import personal.com.tithingapp.tabs.TabFragment;
import personal.com.tithingapp.tabs.TabFragment.ChangeTabListener;

public class ViewPagerAdapter extends FragmentStatePagerAdapter implements ChangeTabListener {
    private FragmentManager mFragmentManager;

    private List<TabFragment> mFragments = new ArrayList<>();
    private List<TabFragment> mChangedFragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        mFragmentManager = fragmentManager;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }

    @Override
    public int getItemPosition(Object object) {
        TabFragment fragment = (TabFragment) object;
        if (mChangedFragments.contains(fragment)) {
            mChangedFragments.remove(fragment);
            return POSITION_NONE;
        }

        return POSITION_UNCHANGED;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(TabFragment fragment) {
        fragment.setTabChangeListener(this);
        mFragments.add(fragment);
    }

    public void replaceCurrentFragment(TabFragment currentFragment, TabFragment newFragment) {
        newFragment.setTabChangeListener(this);
        mChangedFragments.add(currentFragment);
        mFragments.set(mFragments.indexOf(currentFragment), newFragment);
        mFragmentManager.beginTransaction().remove(currentFragment).commit();
        notifyDataSetChanged();
    }
}
