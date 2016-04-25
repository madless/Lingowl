package ua.madless.lingowl.ui.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.squareup.otto.Bus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.madless.lingowl.R;
import ua.madless.lingowl.bus.LingllamaBus;
import ua.madless.lingowl.core.constants.FragmentRequest;
import ua.madless.lingowl.core.manager.Container;
import ua.madless.lingowl.core.manager.IconManager;
import ua.madless.lingowl.core.model.db_model.Category;

/**
 * Created by dmikhov on 25.02.2016.
 */
public class PickCategoryDialogFragment extends DialogFragment implements View.OnClickListener {
    LinearLayout layoutContent;
    Container appContainer;
    TableLayout table;
    ImageView imageViewShowIcons;
    EditText editTextAddCategoryName;
    Context context = getActivity();
    Bus eventBus;
    int iconIndex = -1;
    View selectedCategoryIcon;
    @Bind(R.id.buttonAddCategoryDialogCancel) Button buttonAddCategoryDialogCancel;
    @Bind(R.id.buttonAddCategoryDialogAdd) Button buttonAddCategoryDialogAdd;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        appContainer = Container.getInstance();
        eventBus = LingllamaBus.getBus();
        View root = inflater.inflate(R.layout.dialog_add_category, container);
        ButterKnife.bind(this, root);
        table = (TableLayout) root.findViewById(R.id.tableContent);
        imageViewShowIcons = (ImageView) root.findViewById(R.id.imageViewShowIcons);
        layoutContent = (LinearLayout) root.findViewById(R.id.layoutContent);
        editTextAddCategoryName = (EditText) root.findViewById(R.id.editTextAddCategoryName);
        layoutContent.setAlpha(0f);
        initTable();
        imageViewShowIcons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Show icons", Toast.LENGTH_SHORT).show();
                layoutContent.setVisibility(View.VISIBLE);
                Resources r = getResources();
                final float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
                layoutContent.setTranslationX(-px);
                layoutContent.animate().translationX(0).withLayer().setInterpolator(new AccelerateDecelerateInterpolator());
                layoutContent.animate().alpha(1).withLayer();
            }
        });

        return root;
    }


    @OnClick({R.id.buttonAddCategoryDialogCancel, R.id.buttonAddCategoryDialogAdd})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddCategoryDialogAdd: {
                String title = editTextAddCategoryName.getText().toString();
                if(!title.isEmpty()) {
                    Category category = new Category(title, iconIndex);
                    appContainer.getDbApi(context).addCategory(appContainer.getSettings().getSelectedDictionary(), category);
                    eventBus.post(FragmentRequest.CATEGORY_ADDED);
                    Toast.makeText(getActivity(), "Категория добавлена", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Введите название категории!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.buttonAddCategoryDialogCancel: {
                dismiss();
                break;
            }
            default: {
                iconIndex = (Integer) v.getTag();
                v.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
                if(selectedCategoryIcon != null) {
                    selectedCategoryIcon.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.color_transparent));
                }
                selectedCategoryIcon = v;
            }
        }
    }

    private void initTable() {
        ArrayList<Bitmap> bitmaps = getBitmapList(getActivity());
        int columnNum = 5;
        int lastRowColumnNum = bitmaps.size() % columnNum;
        int rowNum = (bitmaps.size() / columnNum) + (lastRowColumnNum != 0 ? 1 : 0);
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.width = 0;
        params.weight = 1;
        params.height = TableRow.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER_VERTICAL;

        for (int i = 0; i < rowNum; i++) {
            TableRow row = new TableRow(getActivity());
            for (int j = 0; j < columnNum; j++) {
                int iconIndex = i * columnNum + j;
                ImageView imageView = new ImageView(getActivity());
                imageView.setLayoutParams(params);
                if(bitmaps.size() > iconIndex) {
                    imageView.setImageBitmap(bitmaps.get(iconIndex));
                    imageView.setTag(iconIndex);
                    imageView.setOnClickListener(this);
                    row.addView(imageView);
                } else {
                    row.addView(imageView);
                }
            }
            table.addView(row, i);
        }
    }

    public ArrayList<Bitmap> getBitmapList(Context context) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        IconManager iconManager = new IconManager(getActivity());
        for(int i = 0; true; i++) {
            //InputStream inputStream = context.getAssets().open("ic_category_" + i + ".png");
            Bitmap bitmap = iconManager.getCategoryIconBitmapById(i);
            if(bitmap != null) {
                bitmaps.add(bitmap);
            } else {
                break;
            }
        }
        Log.d("mylog1", "bitmaps: " + bitmaps);
        return bitmaps;
    }
}
