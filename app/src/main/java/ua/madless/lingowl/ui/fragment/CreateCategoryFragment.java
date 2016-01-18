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
import android.widget.Toast;

import ua.madless.lingowl.R;
import ua.madless.lingowl.constants.Transfer;
import ua.madless.lingowl.db.DbApi;
import ua.madless.lingowl.model.Category;
import ua.madless.lingowl.model.Dictionary;

/**
 * Created by User on 18.01.2016.
 */
public class CreateCategoryFragment extends Fragment implements View.OnClickListener {
    DbApi dbApi;
    Button buttonCreateCategoryCancel;
    Button buttonCreateCategorySave;
    ImageView imageViewCreateCategoryPickIcon;
    EditText editTextCreateCategoryTitle;
    Dictionary currentDictionary;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_category, null);
        dbApi = DbApi.getInstance(getActivity());
        currentDictionary = getArguments().getParcelable(Transfer.SELECTED_DICTIONARY.toString());
        editTextCreateCategoryTitle = (EditText) root.findViewById(R.id.editTextCreateCategoryTitle);
        imageViewCreateCategoryPickIcon = (ImageView) root.findViewById(R.id.imageViewCreateCategoryPickIcon);
        buttonCreateCategoryCancel = (Button) root.findViewById(R.id.buttonCreateCategoryCancel);
        buttonCreateCategorySave = (Button) root.findViewById(R.id.buttonCreateCategorySave);
        imageViewCreateCategoryPickIcon.setOnClickListener(this);
        buttonCreateCategoryCancel.setOnClickListener(this);
        buttonCreateCategorySave.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCreateCategoryCancel: {
                getActivity().getFragmentManager().popBackStack();
                break;
            }
            case R.id.buttonCreateCategorySave: {
                String title = editTextCreateCategoryTitle.getText().toString();
                int iconId = -1;
                if(!title.isEmpty()) {
                    Category category = new Category(title, iconId);
                    dbApi.addCategory(currentDictionary, category);
                    getActivity().getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getActivity(), "Введите название категории!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.imageViewCreateCategoryPickIcon: {
                break;
            }
        }
    }
}
