package personal.com.tithingapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import personal.com.tithingapp.database.Provider;
import personal.com.tithingapp.utilities.IncomeListAdapter;
import personal.com.tithingapp.utilities.TabFragment;
import personal.com.tithingapp.utilities.Utils;

public class IncomeTab extends TabFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.income_list, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new IncomeListAdapter(getActivity(), null);
        mRecyclerView.setAdapter(mAdapter);

        getLoaderManager().initLoader(Utils.INCOME_LOADER, null, this);
        return v;
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == Utils.INCOME_LOADER) {
            return new android.support.v4.content.CursorLoader(getActivity(), Provider.INCOME_CONTENT_URI, null, null, null, null);
        }

        return null;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }
}
