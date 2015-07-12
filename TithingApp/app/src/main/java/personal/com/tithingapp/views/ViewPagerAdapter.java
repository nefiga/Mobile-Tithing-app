package personal.com.tithingapp.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import personal.com.tithingapp.utilities.TabFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
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
}
