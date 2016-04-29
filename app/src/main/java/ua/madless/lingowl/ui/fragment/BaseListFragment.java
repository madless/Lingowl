package ua.madless.lingowl.ui.fragment;

import android.os.Bundle;
import android.view.View;

import ua.madless.lingowl.core.listener.OnRecyclerViewItemClickListener;
import ua.madless.lingowl.db.DbApi;

/**
 * Created by madless on 07.01.2016.
 */
public abstract class BaseListFragment extends BaseFragment implements OnRecyclerViewItemClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbApi = DbApi.getInstance();
    }

    @Override
    public void onRecyclerViewItemLongClick(View view, int position) {
        // CONTEXT MENU
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {

    }
}
