package ua.madless.lingowl.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ua.madless.lingowl.R;
import ua.madless.lingowl.core.constants.Transfer;
import ua.madless.lingowl.db.DbApi;
import ua.madless.lingowl.core.model.db_model.Category;
import ua.madless.lingowl.core.model.db_model.Dictionary;

/**
 * Created by User on 18.01.2016.
 */
@Deprecated
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
        Log.d("mylog", "on create view");
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        Log.d("mylog", "on create");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Toast.makeText(getActivity(), "onCreateOptionsMenu", Toast.LENGTH_SHORT).show();
        Log.d("mylog", "on create option menu");
        inflater.inflate(R.menu.ok_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_item_ok:
                Toast.makeText(getActivity(), "Added!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setHasOptionsMenu(true);
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
