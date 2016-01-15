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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import ua.madless.lingowl.R;
import ua.madless.lingowl.constants.Transfer;
import ua.madless.lingowl.manager.EventBusManager;
import ua.madless.lingowl.model.Category;
import ua.madless.lingowl.model.Dictionary;
import ua.madless.lingowl.ui.fragment.CategoriesListFragment;
import ua.madless.lingowl.ui.fragment.DictionariesListFragment;
import ua.madless.lingowl.ui.fragment.WordsListFragment;

public class MainActivity extends AppCompatActivity {

    private Drawer.Result drawerResult = null;
    Dictionary selectedDictionary;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bus = EventBusManager.getBus();
        Log.d("mylog", "bus in activity: " + bus.hashCode());

        fragmentManager = getFragmentManager();
        // Инициализируем Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Инициализируем Navigation Drawer
        prepareDrawer();
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

    public void prepareDrawer() {
        drawerResult = new Drawer()
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
                })
                .withOnDrawerItemClickListener(new OnLingowlDrawerItemClickListener())
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    // Обработка длинного клика, например, только для SecondaryDrawerItem
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof SecondaryDrawerItem) {
                            Toast.makeText(MainActivity.this, MainActivity.this.getString(((SecondaryDrawerItem) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .build();
    }

    class OnLingowlDrawerItemClickListener implements Drawer.OnDrawerItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
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
}
