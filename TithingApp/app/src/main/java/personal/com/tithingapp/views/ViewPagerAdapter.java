package personal.com.tithingapp.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import personal.com.tithingapp.TabFragment;
import personal.com.tithingapp.TabFragment.ChangeTabListener;

public class ViewPagerAdapter extends FragmentStatePagerAdapter implements ChangeTabListener{
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
        if (mChangedFragments.contains(object)) {
            mChangedFragments.remove(object);
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
        mChangedFragments.add(currentFragment);
        mFragments.set(mFragments.indexOf(currentFragment), newFragment);
        mFragmentManager.beginTransaction().remove(currentFragment).commit();
        notifyDataSetChanged();
    }
}
