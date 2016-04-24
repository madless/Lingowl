package ua.madless.lingowl.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.madless.lingowl.R;
import ua.madless.lingowl.core.constants.Transfer;
import ua.madless.lingowl.core.model.db_model.Category;
import ua.madless.lingowl.ui.adapter.WordsListAdapter;
import ua.madless.lingowl.core.constants.FragmentRequest;
import ua.madless.lingowl.core.manager.EventBusManager;
import ua.madless.lingowl.core.model.db_model.Word;

public class WordsListFragment extends BaseListFragment implements View.OnClickListener {
    @Bind(R.id.recyclerViewWordsList) RecyclerView recyclerViewWordsList;
    @Bind(R.id.buttonAddWord) FloatingActionButton buttonAddCategory;
    ArrayList<Word> words;
    Bus eventBus;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_words_list, null);
        ButterKnife.bind(this, root);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        Category category = getArguments().getParcelable(Transfer.SELECTED_CATEGORY.toString());
        recyclerViewWordsList.setLayoutManager(layoutManager);
        recyclerViewWordsList.setItemAnimator(itemAnimator);
        words = dbApi.getWordsInCategory(category);
        WordsListAdapter wordsListAdapter = new WordsListAdapter(getActivity(), words);

        eventBus = EventBusManager.getBus();

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

    @OnClick(R.id.buttonAddWord)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddWord: {
                eventBus.post(FragmentRequest.ADD_WORD);
                break;
            }
        }
    }

}
