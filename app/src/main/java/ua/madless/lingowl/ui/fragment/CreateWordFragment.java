package ua.madless.lingowl.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import ua.madless.lingowl.R;
import ua.madless.lingowl.db.DbApi;

public class CreateWordFragment extends Fragment {
    DbApi dbApi;
    EditText editTextCreateWordInput;
    Button buttonCreateWordTranslate;
    Spinner spinnerCreateWordTranslations;
    TextView textViewCreateWordType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_word, null);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
