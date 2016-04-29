package ua.madless.lingowl.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import ua.madless.lingowl.R;
import ua.madless.lingowl.bus.events.ToolbarTitleChangedEvent;
import ua.madless.lingowl.core.constants.Constants;
import ua.madless.lingowl.core.constants.FragmentRequest;
import ua.madless.lingowl.core.constants.Transfer;
import ua.madless.lingowl.core.listener.RecyclerItemClickListener;
import ua.madless.lingowl.core.model.db_model.Category;
import ua.madless.lingowl.core.model.db_model.Dictionary;
import ua.madless.lingowl.ui.adapter.CategoriesListAdapter;

public class CategoriesListFragment extends BaseListFragment {
    private RecyclerView recyclerViewCategoriesList;
    private FloatingActionButton buttonAddCategory;
    private ArrayList<Category> categories;
    private Dictionary selectedDictionary;
    private CategoriesListAdapter categoriesListAdapter;
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
        Category all = new Category(Constants.CATEGORY_MAIN_ID, getActivity().getString(R.string.category_all_words), 0, selectedDictionary.getWordCounter());
        categories = dbApi.getCategoriesInDictionary(selectedDictionary);
        categories.add(0, all);

        categoriesListAdapter = new CategoriesListAdapter(getActivity(), categories);

        recyclerViewCategoriesList.setAdapter(categoriesListAdapter);
        recyclerViewCategoriesList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewCategoriesList, this));
        recyclerViewCategoriesList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        buttonAddCategory.setOnClickListener(new FabOnClickListener());
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.post(new ToolbarTitleChangedEvent(String.format(getString(R.string.toolbar_title_categories), selectedDictionary.getCodeTargetLanguage())));
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
        bus.post(categories.get(position)); // FIXME: 29.04.2016
    }

    @Override
    public void onRecyclerViewItemLongClick(View view, int position) {
        showPopup(view, position);
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

    public void showPopup(View item, final int position) {
        final Category category = categories.get(position);
        PopupMenu categoryPopup = new PopupMenu(getActivity(), item.findViewById(R.id.textViewCategoryItemTitle));
        categoryPopup.inflate(R.menu.popup_category_item_menu);
        categoryPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_action_edit_category: {
                        Toast.makeText(getActivity(), "edit cat", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.popup_action_delete_category: {
                        categories.remove(position);
                        categoriesListAdapter.notifyItemRemoved(position);
                        dbApi.removeCategoryWithoutAllWords(selectedDictionary, category);
                        break;
                    }
                }
                return true;
            }
        });
        categoryPopup.show();
    }
}
