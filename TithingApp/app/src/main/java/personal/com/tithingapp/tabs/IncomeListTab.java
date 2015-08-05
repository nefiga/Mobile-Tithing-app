package personal.com.tithingapp.tabs;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.view.View;
import personal.com.tithingapp.database.IncomeTable;
import personal.com.tithingapp.database.Provider;
import personal.com.tithingapp.parcel_translators.IncomeParcelTranslator;
import personal.com.tithingapp.parcels.DataParcel;
import personal.com.tithingapp.utilities.Utils;

public class IncomeListTab extends ListTab {

    protected void loadNewEditTab() {
        mChangeTabListener.replaceCurrentFragment(this, new EditIncomeTab());
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == Utils.TITHING_INCOME_LOADER) {
            return  new CursorLoader(getActivity(), Provider.INCOME_CONTENT_URI, null, null, null, IncomeTable.DATE + " ASC");
        }

        return null;
    }

    @Override
    public void onItemClick(View view, long id) {
        Bundle data = new Bundle();
        data.putParcelable(DataParcel.NAME, IncomeParcelTranslator.getParcelForID(getActivity(), id));

        EditTab editTab = new EditIncomeTab();
        editTab.setData(data);

        mChangeTabListener.replaceCurrentFragment(this, editTab);
    }
}
