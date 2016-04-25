package ua.madless.lingowl.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.madless.lingowl.R;

/**
 * Created by madless on 06.01.2016.
 */

public class FragmentStub extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category_picker, null, false);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
