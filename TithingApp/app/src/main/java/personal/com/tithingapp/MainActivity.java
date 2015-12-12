package personal.com.tithingapp;

import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import personal.com.tithingapp.database.IncomeTable;
import personal.com.tithingapp.database.Provider;
import personal.com.tithingapp.database.TithingTable;
import personal.com.tithingapp.tabs.IncomeListTab;
import personal.com.tithingapp.tabs.TithingListTab;
import personal.com.tithingapp.utilities.Utils;
import personal.com.tithingapp.views.SlidingTabLayout;
import personal.com.tithingapp.views.ViewPagerAdapter;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ViewPager mPager;
    ViewPagerAdapter mViewPagerAdapter;
    SlidingTabLayout mSlidingTabLayout;
    private TextView mOwedTithing;

    private Subscription mSubscription;

    private TithingDueContentObserver incomeObserver;
    private TithingDueContentObserver tithingObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.tool_bar));

        mOwedTithing = (TextView) findViewById(R.id.owed_tithing);

        mViewPagerAdapter =  new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new IncomeListTab().setTitle(getResources().getString(R.string.income_tab_title)));
        mViewPagerAdapter.addFragment(new TithingListTab().setTitle(getResources().getString(R.string.tithing_tab_title)));

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

        displayOwedTithing();

        tithingObserver = new TithingDueContentObserver(new Handler(getMainLooper()));
        incomeObserver = new TithingDueContentObserver(new Handler(getMainLooper()));

        getContentResolver().registerContentObserver(Provider.TITHING_CONTENT_URI, true, tithingObserver);
        getContentResolver().registerContentObserver(Provider.INCOME_CONTENT_URI, true, incomeObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }

        getContentResolver().unregisterContentObserver(tithingObserver);
        getContentResolver().unregisterContentObserver(incomeObserver);
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

    private void displayOwedTithing() {
        mSubscription = Observable.create(new OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int totalIncome = 0;
                int totalTithing = 0;

                Cursor cursor = getContentResolver().query(Provider.INCOME_CONTENT_URI, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        totalIncome += cursor.getInt(cursor.getColumnIndex(IncomeTable.AMOUNT));
                    }
                    cursor.close();
                }

                cursor = getContentResolver().query(Provider.TITHING_CONTENT_URI, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        totalTithing += cursor.getInt(cursor.getColumnIndex(TithingTable.AMOUNT));
                    }
                    cursor.close();
                }

                subscriber.onNext((int) (totalIncome * .10) - totalTithing);

                subscriber.onCompleted();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer amount) {
                        mOwedTithing.setText(Utils.getDisplayableAmount(amount));
                    }
                });
    }

    public class TithingDueContentObserver extends ContentObserver {

        public TithingDueContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            displayOwedTithing();
        }
    }
}