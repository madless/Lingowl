package ua.madless.lingowl.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ua.madless.lingowl.R;
import ua.madless.lingowl.adapter.CategoriesListAdapter;
import ua.madless.lingowl.model.Category;

public class WordsListFragment extends Fragment {
    RecyclerView recyclerViewWordsList;
    ArrayList<Category> categories;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_words_list, null);
        recyclerViewWordsList = (RecyclerView) root.findViewById(R.id.recyclerViewWordsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerViewCategoriesList.setLayoutManager(layoutManager);
        recyclerViewCategoriesList.setItemAnimator(itemAnimator);
        categories = getDefaultCategories();
        CategoriesListAdapter categoriesListAdapter = new CategoriesListAdapter(getActivity(), categories);
        recyclerViewCategoriesList.setAdapter(categoriesListAdapter);
        setHasOptionsMenu(true);
        return root;
    }

    private ArrayList<Category> getDefaultCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(0, "Избранное", 0, 5));
        categories.add(new Category(1, "Книги", 1, 15));
        categories.add(new Category(2, "Семья", 2, 54));
        categories.add(new Category(3, "Кино", 3, 12));
        categories.add(new Category(4, "Технологии", 4, 0));
        categories.add(new Category(4, "Путешествия", -1, 12));
        return categories;
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {

    }
}
