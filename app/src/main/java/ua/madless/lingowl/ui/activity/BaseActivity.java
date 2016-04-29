package ua.madless.lingowl.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import ua.madless.lingowl.R;
import ua.madless.lingowl.bus.LingllamaBus;
import ua.madless.lingowl.bus.events.fragments.UpdateWordsListEvent;
import ua.madless.lingowl.core.holder.SettingsHolder;
import ua.madless.lingowl.core.manager.Container;
import ua.madless.lingowl.core.manager.PreferencesManager;
import ua.madless.lingowl.db.DbApi;

/**
 * Created by User on 25.04.2016.
 */
public class BaseActivity extends AppCompatActivity {
    protected Bus bus;
    protected Container container;
    protected DbApi dbApi;
    protected Toolbar toolbar;
    protected PreferencesManager preferencesManager;
    protected SettingsHolder settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus = LingllamaBus.getBus();
        bus.register(this);
        container = Container.getInstance();
        dbApi = container.getDbApi();
        preferencesManager = container.getPreferencesManager();
        settings = container.getSettings();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    protected void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(toolbar);
        }
    }

    @Subscribe
    public void processUpdateWordsListEvent(UpdateWordsListEvent event) {
        Log.d("mylog", "processUpdateWordsListEvent activity");
    }
}
