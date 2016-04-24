package ua.madless.lingowl.core.listener;

import android.view.View;

public interface OnRecyclerViewItemClickListener {
    void onRecyclerViewItemClick(View view, int position);
    void onRecyclerViewItemLongClick(View view, int position);
}

