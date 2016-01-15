package ua.madless.lingowl.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.madless.lingowl.R;
import ua.madless.lingowl.manager.IconManager;
import ua.madless.lingowl.model.Word;

public class WordsListAdapter extends RecyclerView.Adapter<WordsListAdapter.ViewHolder> {
    ArrayList<Word> words;
    Context context;
    IconManager iconManager;

    public WordsListAdapter(Context context, ArrayList<Word> words) {
        this.words = words;
        this.context = context;
        iconManager = new IconManager(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_words_list_with_translation, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Word word = words.get(position);
        holder.getTextViewWordsListItemWord().setText(word.getText());
        holder.getTextViewWordsListItemTranslation().setText(word.getTranslation());
        holder.getImageViewWordsListItemSetFavorite().setImageDrawable(iconManager.getFavoriteIcon(word.isFavorite()));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewWordsListItemWord;
        TextView textViewWordsListItemTranslation;
        ImageView imageViewWordsListItemSetFavorite;
        ImageView imageViewWordsListItemPlayWord;
        public ViewHolder(View root) {
            super(root);
            textViewWordsListItemWord = (TextView) root.findViewById(R.id.textViewWordsListItemWord);
            textViewWordsListItemTranslation = (TextView) root.findViewById(R.id.textViewWordsListItemTranslation);
            imageViewWordsListItemSetFavorite = (ImageView) root.findViewById(R.id.imageViewWordsListItemSetFavorite);
            imageViewWordsListItemPlayWord = (ImageView) root.findViewById(R.id.imageViewWordsListItemPlayWord);
            imageViewWordsListItemPlayWord.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_sound));
            Log.d("mylog_adapter", "view holder created");
        }

        public ImageView getImageViewWordsListItemPlayWord() {
            return imageViewWordsListItemPlayWord;
        }

        public ImageView getImageViewWordsListItemSetFavorite() {
            return imageViewWordsListItemSetFavorite;
        }

        public TextView getTextViewWordsListItemTranslation() {
            return textViewWordsListItemTranslation;
        }

        public TextView getTextViewWordsListItemWord() {
            return textViewWordsListItemWord;
        }
    }
}
