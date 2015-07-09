package personal.com.tithingapp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import personal.com.tithingapp.views.SlidingTabLayout;
import personal.com.tithingapp.views.ViewPagerAdapter;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p/>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    ViewPager mPager;
    ViewPagerAdapter mAdapter;
    SlidingTabLayout mTabs;
    CharSequence mTitles[]={"TITHING","INCOME"};
    int mNumboftabs =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolBar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        mAdapter =  new ViewPagerAdapter(getSupportFragmentManager(),mTitles,mNumboftabs);

        // Assigning ViewPager View and setting the adapter
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        // Assiging the Sliding Tab Layout View
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        mTabs.setViewPager(mPager);
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