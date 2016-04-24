package ua.madless.lingowl.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.madless.lingowl.R;
import ua.madless.lingowl.core.manager.IconManager;
import ua.madless.lingowl.core.model.model_for_ui.CheckableCategory;

public class CategoriesListPickerAdapter extends RecyclerView.Adapter<CategoriesListPickerAdapter.ViewHolder> {
    ArrayList<CheckableCategory> checkableCategories;
    Context context;
    IconManager iconManager;

    public CategoriesListPickerAdapter(Context context, ArrayList<CheckableCategory> checkableCategories) {
        this.checkableCategories = checkableCategories;
        this.context = context;
        this.iconManager = new IconManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories_pick_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CheckableCategory category = checkableCategories.get(position);
        Drawable icon = iconManager.getCategoryIconById(category.getIconId());
        holder.getImageViewCategoryItemIcon().setImageDrawable(icon);
        holder.getTextViewCategoryItemTitle().setText(category.getName());
        holder.getCheckBoxCategoryItemPicker().setChecked(category.isChecked());
    }

    @Override
    public int getItemCount() {
        return checkableCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewCategoryItemIcon;
        private TextView textViewCategoryItemTitle;
        private CheckBox checkBoxCategoryItemPicker;
        public ViewHolder(View itemView) {
            super(itemView);
            imageViewCategoryItemIcon = (ImageView) itemView.findViewById(R.id.imageViewCategoryItemIcon);
            textViewCategoryItemTitle = (TextView) itemView.findViewById(R.id.textViewCategoryItemTitle);
            checkBoxCategoryItemPicker = (CheckBox) itemView.findViewById(R.id.checkBoxCategoryItemPicker);
        }

        public ImageView getImageViewCategoryItemIcon() {
            return imageViewCategoryItemIcon;
        }

        public TextView getTextViewCategoryItemTitle() {
            return textViewCategoryItemTitle;
        }

        public CheckBox getCheckBoxCategoryItemPicker() {
            return checkBoxCategoryItemPicker;
        }

    }
}
