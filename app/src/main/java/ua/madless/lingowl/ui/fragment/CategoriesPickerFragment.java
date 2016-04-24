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
import ua.madless.lingowl.core.constants.FragmentRequest;
import ua.madless.lingowl.core.constants.Transfer;
import ua.madless.lingowl.core.listener.RecyclerItemClickListener;
import ua.madless.lingowl.core.manager.EventBusManager;
import ua.madless.lingowl.core.model.db_model.Dictionary;
import ua.madless.lingowl.core.model.model_for_ui.CheckableCategory;
import ua.madless.lingowl.core.util.CategoryUtil;
import ua.madless.lingowl.ui.adapter.CategoriesListPickerAdapter;

/**
 * Created by User on 20.03.2016.
 */
public class CategoriesPickerFragment extends BaseListFragment {
    private Bus eventBus;
    private RecyclerView recyclerViewCategoriesList;
    private FloatingActionButton buttonAddCategory;
    private ArrayList<CheckableCategory> categories;
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

        categories = CategoryUtil.convertToCheckable(dbApi.getCategoriesInDictionary(selectedDictionary));

        CategoriesListPickerAdapter categoriesListPickerAdapter = new CategoriesListPickerAdapter(getActivity(), categories);

        eventBus = EventBusManager.getBus();
        recyclerViewCategoriesList.setAdapter(categoriesListPickerAdapter);
        recyclerViewCategoriesList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewCategoriesList, this));
        buttonAddCategory.setOnClickListener(new FabOnClickListener());
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
//        Bus bus = EventBusManager.getBus();
//        bus.post(categories.get(position));
    }

    class FabOnClickListener implements View.OnClickListener { 
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonAddCategory: {
                    eventBus.post(FragmentRequest.ADD_CATEGORY); // TODO: 20.03.2016 CHANGE CONST!
                    break;
                }
            }
        }
    }
}
