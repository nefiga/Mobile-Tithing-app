package personal.com.tithingapp.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import personal.com.tithingapp.R;
import personal.com.tithingapp.tabs.TabFragment;

public class TithingTab extends TabFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tithing_list, container, false);
        //initialize here
        return v;
    }
}
