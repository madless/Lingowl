package ua.madless.lingowl.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.madless.lingowl.R;
import ua.madless.lingowl.core.constants.Constants;
import ua.madless.lingowl.core.manager.IconManager;
import ua.madless.lingowl.core.model.db_model.Category;

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.ViewHolder> {
    ArrayList<Category> categories;
    Context context;
    IconManager iconManager;

    public CategoriesListAdapter(Context context, ArrayList<Category> categories) {
        this.categories = categories;
        this.context = context;
        this.iconManager = new IconManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = categories.get(position);
        Drawable icon = iconManager.getCategoryIconById(category.getIconId());
        holder.getImageViewCategoryItemIcon().setImageDrawable(icon);
        holder.getTextViewCategoryItemTitle().setText(category.getName());
        holder.getTextViewCategoryItemCounter().setText(String.valueOf(category.getWordCounter()));
        if(category.getId() == Constants.CATEGORY_MAIN_ID) {
            holder.setAsMainCategory();
        } else {
            holder.setAsSimpleCategory();
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View root;
        public ImageView imageViewCategoryItemIcon;
        public TextView textViewCategoryItemTitle;
        public TextView textViewCategoryItemCounter;
        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView;

            imageViewCategoryItemIcon = (ImageView) itemView.findViewById(R.id.imageViewCategoryItemIcon);
            textViewCategoryItemTitle = (TextView) itemView.findViewById(R.id.textViewCategoryItemTitle);
            textViewCategoryItemCounter = (TextView) itemView.findViewById(R.id.textViewCategoryItemCounter);
        }

        public ImageView getImageViewCategoryItemIcon() {
            return imageViewCategoryItemIcon;
        }

        public TextView getTextViewCategoryItemTitle() {
            return textViewCategoryItemTitle;
        }

        public TextView getTextViewCategoryItemCounter() {
            return textViewCategoryItemCounter;
        }

        public void setAsMainCategory() { // with all words
            root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

        public void setAsSimpleCategory() { // with all words
            root.setBackgroundColor(ContextCompat.getColor(context, R.color.color_transparent));
        }
    }
}
