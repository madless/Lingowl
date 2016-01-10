package ua.madless.lingowl.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ua.madless.lingowl.R;
import ua.madless.lingowl.adapter.CategoriesListAdapter;
import ua.madless.lingowl.adapter.WordsListAdapter;
import ua.madless.lingowl.model.Category;
import ua.madless.lingowl.model.Word;

public class WordsListFragment extends BaseListFragment {
    RecyclerView recyclerViewWordsList;
    ArrayList<Word> words;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_words_list, null);
        recyclerViewWordsList = (RecyclerView) root.findViewById(R.id.recyclerViewWordsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerViewWordsList.setLayoutManager(layoutManager);
        recyclerViewWordsList.setItemAnimator(itemAnimator);
        words = getWords();
        WordsListAdapter wordsListAdapter = new WordsListAdapter(getActivity(), words);
        recyclerViewWordsList.setAdapter(wordsListAdapter);
        setHasOptionsMenu(true);
        return root;
    }

    ArrayList<Word> getWords() {
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word(0, "Hello", "привет", true));
        words.add(new Word(1, "World", "мир", false));
        words.add(new Word(2, "Test", "тест", false));
        words.add(new Word(0, "Hello", "привет", true));
        words.add(new Word(1, "World", "мир", false));
        words.add(new Word(2, "Test", "тест", false));
        words.add(new Word(0, "Hello", "привет", true));
        words.add(new Word(1, "World", "мир", false));
        words.add(new Word(2, "Test", "тест", false));
        words.add(new Word(0, "Hello", "привет", true));
        words.add(new Word(1, "World", "мир", false));
        words.add(new Word(2, "Test", "тест", false));
        words.add(new Word(0, "Hello", "привет", true));
        words.add(new Word(1, "World", "мир", false));
        words.add(new Word(2, "Test", "тест", false));
        words.add(new Word(0, "Hello", "привет", true));
        words.add(new Word(1, "World", "мир", false));
        words.add(new Word(2, "Test", "тест", false));
        words.add(new Word(0, "Hello", "привет", true));
        words.add(new Word(1, "World", "мир", false));
        words.add(new Word(2, "Test", "тест", false));
        words.add(new Word(0, "Hello", "привет", true));
        words.add(new Word(1, "World", "мир", false));
        words.add(new Word(2, "Test", "тест", false));
        words.add(new Word(0, "Hello", "привет", true));
        words.add(new Word(1, "World", "мир", false));
        words.add(new Word(2, "Test", "тест", false));
        words.add(new Word(0, "Hello", "привет", true));
        words.add(new Word(1, "World", "мир", false));
        words.add(new Word(2, "Test", "тест", false));
        return words;
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {

    }
}
