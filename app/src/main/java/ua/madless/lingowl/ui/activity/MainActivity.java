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
import ua.madless.lingowl.bus.events.activities.CreateNewWordEvent;
import ua.madless.lingowl.bus.events.fragments.UpdateWordsListEvent;
import ua.madless.lingowl.core.constants.FragmentRequest;
import ua.madless.lingowl.core.constants.Transfer;
import ua.madless.lingowl.core.manager.IntentHelper;
import ua.madless.lingowl.core.model.db_model.Category;
import ua.madless.lingowl.core.model.db_model.Dictionary;
import ua.madless.lingowl.ui.FragmentStub;
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
    Dictionary selectedDictionary;

    FragmentManager fragmentManager;


    private NavigationView mDrawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int mSelectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("mylog", "bus in activity: " + bus.hashCode());

        fragmentManager = getFragmentManager();
        // Инициализируем Toolbar
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.color_white));
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Инициализируем Navigation Drawer
        //prepareDrawer();
        container.getSettings().setNativeLanguage("ru"); // TODO: 14.02.2016 User must choose native language by himself

        setToolbar();
        initView();

        drawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar, 0, 0);
        mDrawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        //default it set first item as selected
        mSelectedId = savedInstanceState == null ? R.id.navigation_item_1: savedInstanceState.getInt("SELECTED_ID");
        itemSelection(mSelectedId);
        mDrawer.setCheckedItem(mSelectedId);
    }

    private void initView() {
        mDrawer= (NavigationView) findViewById(R.id.main_drawer);
        mDrawer.setNavigationItemSelectedListener(this);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    private void itemSelection(int mSelectedId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment currentFragment = new FragmentStub();
        switch(mSelectedId){
            case R.id.navigation_item_1:
                currentFragment = new DictionariesListFragment();
                toolbar.setTitle(R.string.nav_menu_item_dictionaries);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.navigation_item_2:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        fragmentTransaction.replace(R.id.content, currentFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mSelectedId=menuItem.getItemId();
        itemSelection(mSelectedId);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //save selected item so it will remains same even after orientation change
        outState.putInt("SELECTED_ID", mSelectedId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //bus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //bus.unregister(this);
    }

    @Override
    public void onBackPressed() {
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
//        if (drawerResult.isDrawerOpen()) {
//            drawerResult.closeDrawer();
//        } else {
//            if(fragmentManager.getBackStackEntryCount() > 0) {
//                fragmentManager.popBackStack();
//            } else {
//                super.onBackPressed();
//            }
//        }
    }

    // Заглушка, работа с меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Заглушка, работа с меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("mylog", "ID: " + id);
        return super.onOptionsItemSelected(item);
    }


//    class OnLingowlDrawerItemClickListener implements Drawer.OnDrawerItemClickListener {
//        @Override
//        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//            Log.d("mylog", "Pos: " + position);
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            Fragment currentFragment = new FragmentStub();
//            switch (position) {
//                case 1: {
//                    currentFragment = new DictionariesListFragment();
//                    toolbar.setTitle("Словари");
//                    break;
//                }
//                case 2: {
//                    currentFragment = new CategoriesListFragment();
//                    Bundle arguments = new Bundle();
//                    arguments.putParcelable(Transfer.SELECTED_DICTIONARY.toString(), selectedDictionary);
//                    currentFragment.setArguments(arguments);
//                    toolbar.setTitle("Категории (" + selectedDictionary.getCodeTargetLanguage() + ")");
//                    break;
//                }
//                case 3: {
//                    toolbar.setTitle("Тест");
//                    break;
//                }
//                case 5: {
//                    toolbar.setTitle("Статистика");
//                    break;
//                }
//                case 6: {
//                    toolbar.setTitle("Помощь");
//                    break;
//                }
//                case 7: {
//                    toolbar.setTitle("Настройки");
//                    break;
//                }
//                case 8: {
//                    toolbar.setTitle("Выход");
//                    finish();
//                    break;
//                }
//            }
//            fragmentTransaction.replace(R.id.content, currentFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
//            return false;
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("mylog", "on activity Destroy()");
    }

    @Subscribe
    public void onDictionarySelected(Dictionary dictionary) {
        Log.d("mylog", "onNewDictionarySelected");
        this.selectedDictionary = dictionary;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment currentFragment = new CategoriesListFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Transfer.SELECTED_DICTIONARY.toString(), selectedDictionary);
        currentFragment.setArguments(arguments);
        toolbar.setTitle("Категории (" + selectedDictionary.getCodeTargetLanguage() + ")");
        container.getSettings().setTargetLanguage(selectedDictionary.getCodeTargetLanguage());
        container.getSettings().setSelectedDictionary(selectedDictionary);
        fragmentTransaction.replace(R.id.content, currentFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Subscribe
    public void onCategorySelected(Category category) {
        Log.d("mylog_ui", "category selected");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment currentFragment = new WordsListFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(Transfer.SELECTED_CATEGORY.toString(), category);
        currentFragment.setArguments(arguments);
        toolbar.setTitle(category.getName());
        fragmentTransaction.replace(R.id.content, currentFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

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
                //currentFragment = new CreateCategoryFragment();
                //arguments.putParcelable(Transfer.SELECTED_DICTIONARY.toString(), selectedDictionary);
                //toolbar.setTitle("Создание категории");
                PickCategoryDialogFragment pickCategoryDialogFragment = new PickCategoryDialogFragment();
                pickCategoryDialogFragment.show(getFragmentManager(), "pick_category");
                return;
            }
            case CATEGORY_ADDED: {
                currentFragment = new CategoriesListFragment();
                arguments.putParcelable(Transfer.SELECTED_DICTIONARY.toString(), selectedDictionary);
                currentFragment.setArguments(arguments);
                toolbar.setTitle("Категории (" + selectedDictionary.getCodeTargetLanguage() + ")");
                break;
            }
        }
        currentFragment.setArguments(arguments);
        fragmentTransaction.replace(R.id.content, currentFragment);
        fragmentTransaction.addToBackStack(null);
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
}
