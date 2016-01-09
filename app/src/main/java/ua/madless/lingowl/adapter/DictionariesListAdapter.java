package ua.madless.lingowl.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.madless.lingowl.R;
import ua.madless.lingowl.manager.IconManager;
import ua.madless.lingowl.model.Dictionary;

public class DictionariesListAdapter extends RecyclerView.Adapter<DictionariesListAdapter.ViewHolder> {
    ArrayList<Dictionary> dictionaries;
    Context context;
    IconManager iconManager;

    public DictionariesListAdapter(Context context, ArrayList<Dictionary> dictionaries) {
        this.dictionaries = dictionaries;
        this.context = context;
        this.iconManager = new IconManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dictionaries_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Dictionary dictionary = dictionaries.get(position);
        Drawable icon = iconManager.getDictionaryIconById(dictionary.getIconId());
        holder.getImageViewDictionaryItemIcon().setImageDrawable(icon);
        holder.getTextViewDictionaryItemTitle().setText(dictionary.getName());
        holder.getTextViewDictionaryItemCounter().setText(String.valueOf(dictionary.getWordCounter()));
    }

    @Override
    public int getItemCount() {
        return dictionaries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewDictionaryItemIcon;
        public TextView textViewDictionaryItemTitle;
        public TextView textViewDictionaryItemCounter;
        public ViewHolder(View itemView) {
            super(itemView);
            imageViewDictionaryItemIcon = (ImageView) itemView.findViewById(R.id.imageViewDictionaryItemIcon);
            textViewDictionaryItemTitle = (TextView) itemView.findViewById(R.id.textViewDictionaryItemTitle);
            textViewDictionaryItemCounter = (TextView) itemView.findViewById(R.id.textViewDictionaryItemCounter);
        }
        public ImageView getImageViewDictionaryItemIcon() {
            return imageViewDictionaryItemIcon;
        }

        public TextView getTextViewDictionaryItemTitle() {
            return textViewDictionaryItemTitle;
        }

        public TextView getTextViewDictionaryItemCounter() {
            return textViewDictionaryItemCounter;
        }
    }
}
