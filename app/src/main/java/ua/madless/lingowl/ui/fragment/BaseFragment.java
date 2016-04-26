package ua.madless.lingowl.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;

import com.squareup.otto.Bus;

import ua.madless.lingowl.bus.LingllamaBus;

/**
 * Created by User on 24.04.2016.
 */
public abstract class BaseFragment extends Fragment {
    protected Bus bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus = LingllamaBus.getBus();
        bus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
