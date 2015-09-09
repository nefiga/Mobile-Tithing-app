package personal.com.tithingapp;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface FooterAdapter<VH extends RecyclerView.ViewHolder> {

    VH onCreateFooter(ViewGroup parent);

    /**
     * Override this method if you want to put something in the footer
     */
    void onBindFooter(VH viewHolder);
}
