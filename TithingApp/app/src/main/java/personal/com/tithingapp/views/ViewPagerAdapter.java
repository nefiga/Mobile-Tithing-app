package personal.com.tithingapp.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import personal.com.tithingapp.TabFragment;
import personal.com.tithingapp.TabFragment.ChangeTabListener;

public class ViewPagerAdapter extends FragmentStatePagerAdapter implements ChangeTabListener{
    private List<TabFragment> mFragments = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
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
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(TabFragment fragment) {
        mFragments.add(fragment);
    }

    public void replaceCurrentFragment(TabFragment currentFragment, TabFragment newFragment) {
        mFragments.set(mFragments.indexOf(currentFragment), newFragment);
        notifyDataSetChanged();
    }
}
