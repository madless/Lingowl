package ua.madless.lingowl.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;

import ua.madless.lingowl.R;
import ua.madless.lingowl.bus.events.ToolbarTitleChangedEvent;
import ua.madless.lingowl.bus.events.activities.CreateNewWordEvent;
import ua.madless.lingowl.bus.events.fragments.DictionarySelectedEvent;
import ua.madless.lingowl.bus.events.fragments.UpdateWordsListEvent;
import ua.madless.lingowl.core.constants.Constants;
import ua.madless.lingowl.core.constants.FragmentRequest;
import ua.madless.lingowl.core.constants.Transfer;
import ua.madless.lingowl.core.manager.IntentHelper;
import ua.madless.lingowl.core.model.db_model.Category;
import ua.madless.lingowl.core.model.db_model.Dictionary;
import ua.madless.lingowl.ui.dialog.PickCategoryDialogFragment;
import ua.madless.lingowl.ui.fragment.CategoriesListFragment;
import ua.madless.lingowl.ui.fragment.DictionariesListFragment;
import ua.madless.lingowl.ui.fragment.WordsListFragment;

//import com.mikepenz.materialdrawer.Drawer;
//import com.mikepenz.materialdrawer.DrawerBuilder;
//import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
//import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
//import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    //private Drawer drawerResult = null;
    //Dictionary selectedDictionary;

    FragmentManager fragmentManager;


    private NavigationView drawer;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int selectedNavItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("mylog", "bus in activity: " + bus.hashCode());

        fragmentManager = getFragmentManager();
        container.getSettings().setNativeLanguage(Constants.LANG_RUSSIAN); // TODO: 14.02.2016 User must choose native language by himself

        setToolbar();
        initView();
        initDrawer();
        initDrawerLaunchSelection();
    }

    private void initDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void initView() {
        drawer = (NavigationView) findViewById(R.id.main_drawer);
        drawer.setNavigationItemSelectedListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void initDrawerLaunchSelection() {
        int dictionaryId = container.getPreferencesManager().loadDictionaryId();
        if(dictionaryId >= 0) { // -1 if dictionary wasn't save
            selectedNavItem = R.id.navigation_item_categories;
            Dictionary dictionary = container.getDbApi().getDictionaryById(dictionaryId);
            settings.setSelectedDictionary(dictionary);
            settings.setTargetLanguage(dictionary.getCodeTargetLanguage());
        } else {
            selectedNavItem = R.id.navigation_item_dictionaries;
        }
        itemSelection(selectedNavItem);
        drawer.setCheckedItem(selectedNavItem);
    }

    private void itemSelection(int selectedId) {
        switch(selectedId){
            case R.id.navigation_item_dictionaries: {
                openDictionaries();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.navigation_item_categories: {
                openDictionaryCategories(container.getSettings().getSelectedDictionary());
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.navigation_item_exit: {
                finish();
                break;
            }
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        selectedNavItem = menuItem.getItemId();
        itemSelection(selectedNavItem);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //save selected item so it will remains same even after orientation change
       // outState.putInt("SELECTED_ID", selectedNavItem); // TODO: 29.04.2016 Impl state saving
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(drawer)) {
            drawerLayout.closeDrawer(drawer);
        } else {
            if(fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    // Заглушка, работа с меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Заглушка, работа с меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("mylog", "on activity Destroy()");
    }

    private void openDictionaryCategories(Dictionary dictionary) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment currentFragment = new CategoriesListFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Transfer.SELECTED_DICTIONARY.toString(), dictionary);
        currentFragment.setArguments(arguments);
        fragmentTransaction.replace(R.id.content, currentFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    private void openDictionaries() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment currentFragment = new DictionariesListFragment();
        fragmentTransaction.replace(R.id.content, currentFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    @Subscribe
    public void processDictionarySelectedEvent(DictionarySelectedEvent event) {
        Log.d("mylog", "onNewDictionarySelected");
        Dictionary dictionary = event.getDictionary();
        settings.setSelectedDictionary(dictionary);
        settings.setTargetLanguage(dictionary.getCodeTargetLanguage());
        preferencesManager.saveDictionaryId(dictionary);
        openDictionaryCategories(dictionary);
    }

    // FIXME: 29.04.2016 impl event to fix this
    @Subscribe
    public void onCategorySelected(Category category) {
        Log.d("mylog_ui", "category selected");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment currentFragment = new WordsListFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Transfer.SELECTED_CATEGORY.toString(), category);
        currentFragment.setArguments(arguments);
        fragmentTransaction.replace(R.id.content, currentFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    // FIXME: 29.04.2016 impl some events to fix this
    @Subscribe
    public void onFragmentRequest(FragmentRequest request) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment currentFragment = null;
        Bundle arguments = new Bundle();
        switch (request) {
            case ADD_DICTIONARY: {
                break;
            }
            case ADD_CATEGORY: {
                PickCategoryDialogFragment pickCategoryDialogFragment = new PickCategoryDialogFragment();
                pickCategoryDialogFragment.show(getFragmentManager(), "pick_category");
                return;
            }
            case CATEGORY_ADDED: {
                Dictionary dictionary = container.getSettings().getSelectedDictionary();
                currentFragment = new CategoriesListFragment();
                arguments.putParcelable(Transfer.SELECTED_DICTIONARY.toString(), dictionary);
                currentFragment.setArguments(arguments);
                break;
            }
        }
        currentFragment.setArguments(arguments);
        fragmentTransaction.replace(R.id.content, currentFragment);
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    @Subscribe
    public void processCreateNewWordEvent(CreateNewWordEvent event) {
        IntentHelper.startCreateWordActivity(this, null);
    }

    @Subscribe
    public void processUpdateWordsListEvent(UpdateWordsListEvent event) {
        Log.d("mylog", "processUpdateWordsListEvent MAIN activity");
    }

    @Subscribe
    public void processToolbarTitleChangedEvent(ToolbarTitleChangedEvent event) {
        String title = event.getToolbarTitle();
        toolbar.setTitle(title);
    }
}
