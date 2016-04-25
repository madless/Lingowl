package ua.madless.lingowl.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;

import java.util.ArrayList;

import ua.madless.lingowl.R;
import ua.madless.lingowl.bus.LingllamaBus;
import ua.madless.lingowl.ui.adapter.CategoriesListAdapter;
import ua.madless.lingowl.core.constants.FragmentRequest;
import ua.madless.lingowl.core.constants.Transfer;
import ua.madless.lingowl.core.listener.RecyclerItemClickListener;
import ua.madless.lingowl.core.model.db_model.Category;
import ua.madless.lingowl.core.model.db_model.Dictionary;

public class CategoriesListFragment extends BaseListFragment {
    private RecyclerView recyclerViewCategoriesList;
    private FloatingActionButton buttonAddCategory;
    private ArrayList<Category> categories;
    private Dictionary selectedDictionary;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories_list, null);
        buttonAddCategory = (FloatingActionButton) root.findViewById(R.id.buttonAddCategory);
        recyclerViewCategoriesList = (RecyclerView) root.findViewById(R.id.recyclerViewCategoriesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerViewCategoriesList.setLayoutManager(layoutManager);
        recyclerViewCategoriesList.setItemAnimator(itemAnimator);

        selectedDictionary = getArguments().getParcelable(Transfer.SELECTED_DICTIONARY.toString());

        categories = dbApi.getCategoriesInDictionary(selectedDictionary);

        CategoriesListAdapter categoriesListAdapter = new CategoriesListAdapter(getActivity(), categories);

        bus = LingllamaBus.getBus();
        recyclerViewCategoriesList.setAdapter(categoriesListAdapter);
        recyclerViewCategoriesList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewCategoriesList, this));
        buttonAddCategory.setOnClickListener(new FabOnClickListener());
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
        Bus bus = LingllamaBus.getBus();
        bus.post(categories.get(position));
    }

    class FabOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonAddCategory: {
                    bus.post(FragmentRequest.ADD_CATEGORY);
                    break;
                }
            }
        }
    }
}
