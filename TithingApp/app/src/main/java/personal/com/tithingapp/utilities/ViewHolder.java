package personal.com.tithingapp.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    private ViewHolderOnClickListener mListener;

    public ViewHolder(View itemView) {
        super(itemView);
    }

    public void setOnClickListener(ViewHolderOnClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null)
            mListener.onClick(view, getPosition());
    }

    public interface ViewHolderOnClickListener {
        void onClick(View view, int position);
    }
}
