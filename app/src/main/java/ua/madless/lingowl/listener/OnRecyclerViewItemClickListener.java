package ua.madless.lingowl.listener;

import android.view.View;

public interface OnRecyclerViewItemClickListener {
    void onRecyclerViewItemClick(View view, int position);
    void onRecyclerViewItemLongClick(View view, int position);
}

