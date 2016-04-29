package ua.madless.lingowl.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.madless.lingowl.R;
import ua.madless.lingowl.bus.events.activities.CreateNewWordEvent;
import ua.madless.lingowl.bus.events.fragments.UpdateWordsListEvent;
import ua.madless.lingowl.core.constants.Constants;
import ua.madless.lingowl.core.constants.Transfer;
import ua.madless.lingowl.core.listener.RecyclerItemClickListener;
import ua.madless.lingowl.core.model.db_model.Category;
import ua.madless.lingowl.core.model.db_model.Dictionary;
import ua.madless.lingowl.core.model.db_model.Word;
import ua.madless.lingowl.ui.adapter.WordsListAdapter;

public class WordsListFragment extends BaseListFragment implements View.OnClickListener {
    @Bind(R.id.recyclerViewWordsList) RecyclerView recyclerViewWordsList;
    @Bind(R.id.buttonAddWord) FloatingActionButton buttonAddCategory;
    ArrayList<Word> words;
    WordsListAdapter wordsListAdapter;
    Category category;
    Dictionary dictionary;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_words_list, null);
        ButterKnife.bind(this, root);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        dictionary = this.container.getSettings().getSelectedDictionary();
        category = getArguments().getParcelable(Transfer.SELECTED_CATEGORY.toString());
        recyclerViewWordsList.setLayoutManager(layoutManager);
        recyclerViewWordsList.setItemAnimator(itemAnimator);
        recyclerViewWordsList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewWordsList, this));
        recyclerViewWordsList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        updateWords();
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void updateWords() {
        if(category.getId() == Constants.CATEGORY_MAIN_ID) {
            words = dbApi.getWordsInDictionary(dictionary);
        } else {
            words = dbApi.getWordsInCategory(category);
        }
        wordsListAdapter = new WordsListAdapter(getActivity(), words);
        recyclerViewWordsList.setAdapter(wordsListAdapter);
    }

    @Subscribe
    public void processUpdateWordsListEvent(UpdateWordsListEvent event) {
        updateWords();
    }

    @Override
    public void onRecyclerViewItemClick(View view, int position) {
        Toast.makeText(getActivity(), words.get(position).getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecyclerViewItemLongClick(View view, int position) {
        showPopup(view, position);
    }

    public void showPopup(View item, final int position) {
        final Word word = words.get(position);
        PopupMenu wordPopup = new PopupMenu(getActivity(), item.findViewById(R.id.textViewWordsListItemWord));
        wordPopup.inflate(R.menu.popup_word_item_menu);
        wordPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_action_open_word: {
                        Toast.makeText(getActivity(), "open " + word.getText(), Toast.LENGTH_SHORT);
                        break;
                    }
                    case R.id.popup_action_edit_word: {
                        Toast.makeText(getActivity(), "edt " + word.getText(), Toast.LENGTH_SHORT);
                        break;
                    }
                    case R.id.popup_action_delete_word: {
                        Toast.makeText(getActivity(), "del " + word.getText(), Toast.LENGTH_SHORT);
                        words.remove(position);
                        wordsListAdapter.notifyItemRemoved(position);
                        dbApi.removeWordFromAllCategories(dictionary, word);
                        break;
                    }
                    default: {
                        Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT);
                        break;
                    }
                }
                return true;
            }
        });
        wordPopup.show();
    }

    @OnClick(R.id.buttonAddWord)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddWord: {
                bus.post(new CreateNewWordEvent());
                break;
            }
        }
    }
}
