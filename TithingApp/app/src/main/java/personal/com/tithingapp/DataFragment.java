package personal.com.tithingapp;

import android.os.Bundle;
import personal.com.tithingapp.tabs.TabFragment;

public abstract class DataFragment extends TabFragment {

    public abstract void setData(Bundle data);
}
