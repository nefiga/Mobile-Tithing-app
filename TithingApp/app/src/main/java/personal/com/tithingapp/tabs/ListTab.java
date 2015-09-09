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
import personal.com.tithingapp.ListAdapter;
import personal.com.tithingapp.ListAdapter.OnListItemClickListener;
import personal.com.tithingapp.R;
import personal.com.tithingapp.utilities.Utils;

public abstract class ListTab extends TabFragment implements LoaderManager.LoaderCallbacks<Cursor>, OnListItemClickListener{

    protected RecyclerView mRecyclerView;
    protected ListAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected FloatingActionButton mFab;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.income_list, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new  ListAdapter(getActivity(), null, this);
        mAdapter.enableFooter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(mAdapter);

        mFab = (FloatingActionButton) v.findViewById(R.id.fab);

        setListeners();

        getLoaderManager().initLoader(Utils.TITHING_INCOME_LOADER, null, this);

        return v;
    }

    private void setListeners() {
        mFab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNewEditTab();
            }
        });
    }

    protected abstract void loadNewEditTab();

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        //TODO Need to grab all the dates and figure out how many section headers I need and what their position and title will be
        /*List<String> dates = new ArrayList<>();
        while (data.moveToNext()) {
            dates.add(data.getString(data.getColumnIndex(TithingTable.DATE)));
        }*/
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }
}
