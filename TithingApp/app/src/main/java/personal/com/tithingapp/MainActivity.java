package personal.com.tithingapp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import personal.com.tithingapp.tabs.IncomeListTab;
import personal.com.tithingapp.tabs.TithingTab;
import personal.com.tithingapp.views.SlidingTabLayout;
import personal.com.tithingapp.views.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    ViewPager mPager;
    ViewPagerAdapter mViewPagerAdapter;
    SlidingTabLayout mSlidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.tool_bar));

        mViewPagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new IncomeListTab().setTitle(getResources().getString(R.string.income_tab_title)));
        mViewPagerAdapter.addFragment(new TithingTab().setTitle(getResources().getString(R.string.tithing_tab_title)));

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mViewPagerAdapter);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        mSlidingTabLayout.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accent);
            }
        });

        mSlidingTabLayout.setViewPager(mPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }
}