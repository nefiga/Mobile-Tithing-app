package personal.com.tithingapp.tabs;

import personal.com.tithingapp.parcels.DataParcel;
import personal.com.tithingapp.parcels.IncomeParcel;

public class EditIncomeTab extends EditTab {

    @Override
    protected void returnToListView() {
        mChangeTabListener.replaceCurrentFragment(this, new IncomeListTab());
    }

    @Override
    protected DataParcel getNewDataParcel() {
        return new IncomeParcel();
    }
}
