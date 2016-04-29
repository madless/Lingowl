package ua.madless.lingowl.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ua.madless.lingowl.R;
import ua.madless.lingowl.bus.events.ToolbarTitleChangedEvent;
import ua.madless.lingowl.bus.events.fragments.DictionarySelectedEvent;
import ua.madless.lingowl.core.listener.RecyclerItemClickListener;
import ua.madless.lingowl.core.model.db_model.Dictionary;
import ua.madless.lingowl.ui.adapter.DictionariesListAdapter;

public class DictionariesListFragment extends BaseListFragment {
    RecyclerView recyclerViewDictionariesList;
    ArrayList<Dictionary> dictionaries;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dictionaries_list, null);
        recyclerViewDictionariesList = (RecyclerView) root.findViewById(R.id.recyclerViewDictionariesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerViewDictionariesList.setLayoutManager(layoutManager);
        recyclerViewDictionariesList.setItemAnimator(itemAnimator);
        dictionaries = dbApi.getAllDictionaries();
        DictionariesListAdapter dictionariesListAdapter = new DictionariesListAdapter(getActivity(), dictionaries);
        recyclerViewDictionariesList.setAdapter(dictionariesListAdapter);
        recyclerViewDictionariesList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewDictionariesList, this));
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.post(new ToolbarTitleChangedEvent(getString(R.string.toolbar_title_dictionaries)));
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
        Log.d("mylog", "dict: " + dictionaries.get(position));
        bus.post(new DictionarySelectedEvent(dictionaries.get(position)));
    }
}
