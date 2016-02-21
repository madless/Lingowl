package ua.madless.lingowl.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import ua.madless.lingowl.R;
import ua.madless.lingowl.constants.FragmentRequest;
import ua.madless.lingowl.constants.Transfer;
import ua.madless.lingowl.db.DbApi;
import ua.madless.lingowl.manager.Container;
import ua.madless.lingowl.manager.EventBusManager;
import ua.madless.lingowl.model.db_model.Category;
import ua.madless.lingowl.model.db_model.Dictionary;
import ua.madless.lingowl.ui.fragment.CategoriesListFragment;
import ua.madless.lingowl.ui.fragment.CreateCategoryFragment;
import ua.madless.lingowl.ui.fragment.CreateWordFragment;
import ua.madless.lingowl.ui.fragment.DictionariesListFragment;
import ua.madless.lingowl.ui.fragment.WordsListFragment;

public class MainActivity extends AppCompatActivity {

    private Drawer drawerResult = null;
    Dictionary selectedDictionary;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    Bus bus;
    DbApi dbApi;
    Container container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bus = EventBusManager.getBus();
        Log.d("mylog", "bus in activity: " + bus.hashCode());

        container = Container.getInstance();
        fragmentManager = getFragmentManager();
        // Инициализируем Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.color_white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Инициализируем Navigation Drawer
        prepareDrawer();
        container.getSettings().setNativeLanguage("ru"); // TODO: 14.02.2016 User must choose native language by himself 
        //dbApi = DbApi.getInstance(this);

//        ArrayList<Dictionary> dictionaries = getDefaultDictionaries();
//        for(int i = 0; i < dictionaries.size(); i++) {
//            dbApi.addDictionary(dictionaries.get(i));
//        }
//
//        ArrayList<Category> categories = getDefaultCategories();
//        for(int i = 0; i < categories.size(); i++) {
//            dbApi.addCategory(dictionaries.get(i % 4), categories.get(i));
//        }

    }

    private ArrayList<Dictionary> getDefaultDictionaries() {
        ArrayList<Dictionary> dictionaries = new ArrayList<>();
        dictionaries.add(new Dictionary("Английский", "en", "ru", 0, 1));
        dictionaries.add(new Dictionary("Немецкий", "de", "ru", 1, 1));
        dictionaries.add(new Dictionary("Французский", "fr", "ru", 2, 1));
        dictionaries.add(new Dictionary("Испанский", "es", "ru", 4, 0));
        return dictionaries;
    }

    private ArrayList<Category> getDefaultCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("Избранное", 0));
        categories.add(new Category("Книги", 1));
        categories.add(new Category("Семья", 2));
        categories.add(new Category("Кино", 3));
        categories.add(new Category("Технологии", 4));
        categories.add(new Category("Путешествия", -1));
        return categories;
    }

    @Override
    protected void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bus.unregister(this);
    }

    @Override
    public void onBackPressed() {
        // Закрываем Navigation Drawer по нажатию системной кнопки "Назад" если он открыт
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            if(fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

//    // Заглушка, работа с меню
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    // Заглушка, работа с меню
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        Log.d("mylog", "ID: " + id);
//        return super.onOptionsItemSelected(item);
//    }

    public void prepareDrawer() {
        drawerResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Словари").withIdentifier(1).withIcon(ContextCompat.getDrawable(this, R.drawable.ic_dictionary)).withBadge("3"),
                        new PrimaryDrawerItem().withName("Категории").withIcon(ContextCompat.getDrawable(this, R.mipmap.ic_folder)).withBadge("14"),
                        new PrimaryDrawerItem().withName("Тест").withIcon(ContextCompat.getDrawable(this, R.mipmap.ic_test)),
                        new PrimaryDrawerItem().withName("Статистика").withIcon(ContextCompat.getDrawable(this, R.drawable.ic_stats)),
                        new PrimaryDrawerItem().withName("Помощь").withIcon(ContextCompat.getDrawable(this, R.mipmap.ic_info)),
                        new PrimaryDrawerItem().withName("Настройки").withIcon(ContextCompat.getDrawable(this, R.drawable.ic_settings)),
                        new PrimaryDrawerItem().withName("Выход").withIcon(ContextCompat.getDrawable(this, R.drawable.ic_exit)))
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Скрываем клавиатуру при открытии Navigation Drawer
                        InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .withOnDrawerItemClickListener(new OnLingowlDrawerItemClickListener())
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof SecondaryDrawerItem) {
                            Toast.makeText(MainActivity.this, MainActivity.this.getString(((SecondaryDrawerItem) drawerItem).getName().getTextRes()), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .build();
    }

    class OnLingowlDrawerItemClickListener implements Drawer.OnDrawerItemClickListener {
        @Override
        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            Log.d("mylog", "Pos: " + position);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment currentFragment = new FragmentStub();
            switch (position) {
                case 1: {
                    currentFragment = new DictionariesListFragment();
                    toolbar.setTitle("Словари");
                    break;
                }
                case 2: {
                    currentFragment = new CategoriesListFragment();
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(Transfer.SELECTED_DICTIONARY.toString(), selectedDictionary);
                    currentFragment.setArguments(arguments);
                    toolbar.setTitle("Категории (" + selectedDictionary.getCodeTargetLanguage() + ")");
                    break;
                }
                case 3: {
                    toolbar.setTitle("Тест");
                    break;
                }
                case 5: {
                    toolbar.setTitle("Статистика");
                    break;
                }
                case 6: {
                    toolbar.setTitle("Помощь");
                    break;
                }
                case 7: {
                    toolbar.setTitle("Настройки");
                    break;
                }
                case 8: {
                    toolbar.setTitle("Выход");
                    finish();
                    break;
                }
            }
            fragmentTransaction.replace(R.id.content, currentFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return false;
        }
    }

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
                currentFragment = new CreateCategoryFragment();
                arguments.putParcelable(Transfer.SELECTED_DICTIONARY.toString(), selectedDictionary);
                toolbar.setTitle("Создание категории");
                break;
            }
            case ADD_WORD: {
                currentFragment = new CreateWordFragment();
                toolbar.setTitle("Добавить новое слово");
                break;
            }
        }
        currentFragment.setArguments(arguments);
        fragmentTransaction.replace(R.id.content, currentFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

//    @Subscribe
//    public void onWordAdd(FragmentEventType fragmentEventType) {
//        Log.d("mylog_ui", "onWordAdd");
//        switch (fragmentEventType) {
//            case ADD_WORD: {
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                Fragment currentFragment = new CreateWordFragmentOld();
//                break;
//            }
//        }
//    }
}
