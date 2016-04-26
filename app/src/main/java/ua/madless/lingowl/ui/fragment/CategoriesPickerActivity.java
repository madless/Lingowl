package ua.madless.lingowl.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ua.madless.lingowl.R;
import ua.madless.lingowl.bus.LingllamaBus;
import ua.madless.lingowl.core.constants.Constants;
import ua.madless.lingowl.core.manager.IntentHelper;
import ua.madless.lingowl.core.model.db_model.Dictionary;
import ua.madless.lingowl.core.model.model_for_ui.CheckableCategory;
import ua.madless.lingowl.core.util.CategoryUtil;
import ua.madless.lingowl.ui.activity.BaseActivity;
import ua.madless.lingowl.ui.adapter.CategoriesListPickerAdapter;

/**
 * Created by User on 20.03.2016.
 */
public class CategoriesPickerActivity extends BaseActivity {

    private RecyclerView recyclerViewCategoriesList;
    private FloatingActionButton buttonAddCategory;
    private ArrayList<CheckableCategory> categories;
    private Dictionary selectedDictionary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_category_picker);
        buttonAddCategory = (FloatingActionButton) findViewById(R.id.buttonAddCategoryInPicker);
        recyclerViewCategoriesList = (RecyclerView) findViewById(R.id.recyclerViewCategoriesPickerList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerViewCategoriesList.setLayoutManager(layoutManager);
        recyclerViewCategoriesList.setItemAnimator(itemAnimator);

        selectedDictionary = container.getSettings().getSelectedDictionary();

        categories = CategoryUtil.convertToCheckable(dbApi.getCategoriesInDictionary(selectedDictionary));

        CategoriesListPickerAdapter categoriesListPickerAdapter = new CategoriesListPickerAdapter(this, categories);

        bus = LingllamaBus.getBus();
        recyclerViewCategoriesList.setAdapter(categoriesListPickerAdapter);
        buttonAddCategory.setOnClickListener(new FabOnClickListener());
        setToolbar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ok_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("dmikhov", "onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.menu_action_item_ok: {
                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                setResult(IntentHelper.RESULT_CODE_OK, getSelectedCategoriesData());
                this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public Intent getSelectedCategoriesData() {
        Intent result = new Intent();
        ArrayList<CheckableCategory> selectedCategories = new ArrayList<>();
        for (CheckableCategory category: categories) {
            if(category.isChecked()) {
                selectedCategories.add(category);
            }
        }
        result.putParcelableArrayListExtra(Constants.EXTRA_SELECTED_CATEGORIES, selectedCategories);
        return result;
    }

    class FabOnClickListener implements View.OnClickListener { 
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonAddCategoryInPicker: {
                    //bus.post(FragmentRequest.ADD_CATEGORY); // TODO: 20.03.2016 CHANGE CONST!
                    break;
                }
            }
        }
    }
}
