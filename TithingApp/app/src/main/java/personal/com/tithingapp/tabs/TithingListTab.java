package personal.com.tithingapp.tabs;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import personal.com.tithingapp.database.Provider;
import personal.com.tithingapp.parcel_translators.TithingParcelTranslator;
import personal.com.tithingapp.parcels.DataParcel;
import personal.com.tithingapp.utilities.Utils;

public class TithingListTab extends ListTab {

    protected void loadNewEditTab() {
        mChangeTabListener.replaceCurrentFragment(this, new EditTithingTab());
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == Utils.TITHING_INCOME_LOADER) {
            return new android.support.v4.content.CursorLoader(getActivity(), Provider.TITHING_CONTENT_URI, null, null, null, null);
        }

        return null;
    }

    @Override
    public void onItemClick(View view, long id) {
        Bundle data = new Bundle();
        data.putParcelable(DataParcel.NAME, TithingParcelTranslator.getParcelForID(getActivity(), id));

        EditTab editTab = new EditTithingTab();
        editTab.setData(data);

        mChangeTabListener.replaceCurrentFragment(this, editTab);
    }
}
