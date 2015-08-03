package personal.com.tithingapp.tabs;

import personal.com.tithingapp.parcels.DataParcel;
import personal.com.tithingapp.parcels.TithingParcel;

public class EditTithingTab extends EditTab {

    @Override
    protected void returnToListView() {
        mChangeTabListener.replaceCurrentFragment(this, new TithingListTab());
    }

    @Override
    protected DataParcel getNewDataParcel() {
        return new TithingParcel();
    }
}
