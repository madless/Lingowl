package ua.madless.lingowl.ui.fragment;

import android.app.Fragment;
import android.view.View;

import ua.madless.lingowl.listener.OnRecyclerViewItemClickListener;

/**
 * Created by madless on 07.01.2016.
 */
public abstract class BaseListFragment extends Fragment implements OnRecyclerViewItemClickListener {
    @Override
    public void onRecyclerViewItemLongClick(View view, int position) {
        // CONTEXT MENU
    }
}
