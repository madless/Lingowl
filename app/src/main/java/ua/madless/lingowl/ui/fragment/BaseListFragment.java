package ua.madless.lingowl.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

import ua.madless.lingowl.db.DbApi;
import ua.madless.lingowl.core.listener.OnRecyclerViewItemClickListener;

/**
 * Created by madless on 07.01.2016.
 */
public abstract class BaseListFragment extends Fragment implements OnRecyclerViewItemClickListener {
    DbApi dbApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbApi = DbApi.getInstance(getActivity());
    }

    @Override
    public void onRecyclerViewItemLongClick(View view, int position) {
        // CONTEXT MENU
    }
}
