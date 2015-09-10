package personal.com.tithingapp.tabs;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import personal.com.tithingapp.ListAdapter;
import personal.com.tithingapp.ListAdapter.OnListItemClickListener;
import personal.com.tithingapp.R;
import personal.com.tithingapp.database.IncomeTable;
import personal.com.tithingapp.database.Provider;
import personal.com.tithingapp.database.TithingTable;
import personal.com.tithingapp.utilities.Utils;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public abstract class ListTab extends TabFragment implements LoaderManager.LoaderCallbacks<Cursor>, OnListItemClickListener {

    protected RecyclerView mRecyclerView;
    protected ListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected FloatingActionButton mFab;
    private TextView mOwedTithing;

    private Subscription mSubscription;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.income_list, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListAdapter(getActivity(), null, this);
        mAdapter.enableFooter(mAdapter);
        mAdapter.enableSections(mAdapter);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(mAdapter);

        mFab = (FloatingActionButton) v.findViewById(R.id.fab);

        mOwedTithing = (TextView) v.findViewById(R.id.owed_tithing);

        setListeners();

        getLoaderManager().initLoader(Utils.TITHING_INCOME_LOADER, null, this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayOwedTithing();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if  (isVisibleToUser) {
            displayOwedTithing();
        }
    }

    private void setListeners() {
        mFab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNewEditTab();
            }
        });
    }

    private void displayOwedTithing() {
        mSubscription = Observable.create(new OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                int totalIncome = 0;
                int totalTithing = 0;

                Cursor cursor = getContext().getContentResolver().query(Provider.INCOME_CONTENT_URI, null, null, null, null);
                while (cursor.moveToNext()) {
                    totalIncome += cursor.getInt(cursor.getColumnIndex(IncomeTable.AMOUNT));
                }
                cursor.close();

                cursor = getContext().getContentResolver().query(Provider.TITHING_CONTENT_URI, null, null, null, null);
                while (cursor.moveToNext()) {
                    totalTithing += cursor.getInt(cursor.getColumnIndex(TithingTable.AMOUNT));
                }
                cursor.close();

                subscriber.onNext((int) (totalIncome * .10) - totalTithing);

                subscriber.onCompleted();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer amount) {
                mOwedTithing.setText(Utils.getDisplayableAmount(amount));
            }
        });
    }

    protected abstract void loadNewEditTab();

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }
}
