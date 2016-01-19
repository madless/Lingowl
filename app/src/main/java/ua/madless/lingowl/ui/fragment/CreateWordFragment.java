package ua.madless.lingowl.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.madless.lingowl.R;
import ua.madless.lingowl.constants.State;
import ua.madless.lingowl.db.DbApi;

public class CreateWordFragment extends Fragment implements View.OnClickListener {
    DbApi dbApi;

    @Bind(R.id.editTextCreateWordInput) EditText editTextCreateWordInput;
    @Bind(R.id.buttonCreateWordTranslate) Button buttonCreateWordTranslate;
    @Bind(R.id.imageViewCreateWordModeSwitch) ImageView imageViewCreateWordModeSwitch;
    @Bind(R.id.spinnerCreateWordCategories) Spinner spinnerCreateWordCategories;
    @Bind(R.id.editTextCreateWordComment) EditText editTextCreateWordComment;
    @Bind(R.id.viewCreateWordMutableContainer) View viewCreateWordMutableContainer;

    Spinner spinnerCreateWordStandardTranslation;
    TextView textViewCreateWordStandardPart;
    TextView textViewCreateWordStandardGender;

    EditText editTextCreateWordCustomTranslation;
    Spinner spinnerCreateWordCustomPart;
    Spinner spinnerCreateWordCustomGender;

    State currentState = State.STANDARD;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_word, null);
        ButterKnife.bind(this, root);
        switch (currentState) {
            case STANDARD: {
                spinnerCreateWordStandardTranslation = ButterKnife.findById(viewCreateWordMutableContainer, R.id.spinnerCreateWordStandardTranslation);
                textViewCreateWordStandardPart = ButterKnife.findById(viewCreateWordMutableContainer, R.id.textViewCreateWordStandardPart);
                textViewCreateWordStandardGender = ButterKnife.findById(viewCreateWordMutableContainer, R.id.textViewCreateWordStandardGender);
            }
            case CUSTOM: {
            }
        }

        return root;
    }

    @OnClick({R.id.buttonCreateWordTranslate, R.id.imageViewCreateWordModeSwitch})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCreateWordTranslate: {
                String word = editTextCreateWordInput.getText().toString();
                break;
            }
            case R.id.imageViewCreateWordModeSwitch: {
                if(currentState == State.STANDARD) {
                    currentState = State.CUSTOM;
                } else {
                    currentState = State.STANDARD;
                }
                break;
            }
        }
    }
}
