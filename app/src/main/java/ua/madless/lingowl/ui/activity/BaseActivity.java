package ua.madless.lingowl.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.squareup.otto.Bus;

import ua.madless.lingowl.R;
import ua.madless.lingowl.bus.LingllamaBus;
import ua.madless.lingowl.core.manager.Container;
import ua.madless.lingowl.db.DbApi;

/**
 * Created by User on 25.04.2016.
 */
public class BaseActivity extends AppCompatActivity {
    protected Bus bus;
    protected Container appContainer;
    protected DbApi dbApi;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus = LingllamaBus.getBus();
        appContainer = Container.getInstance();
        dbApi = appContainer.getDbApi(this);
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
}
