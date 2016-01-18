package ua.madless.lingowl.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import ua.madless.lingowl.R;
import ua.madless.lingowl.adapter.DictionariesListAdapter;
import ua.madless.lingowl.model.Dictionary;

public class TestActivity extends AppCompatActivity {
    RecyclerView recyclerViewDictionariesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_dict_list);

        recyclerViewDictionariesList = (RecyclerView) findViewById(R.id.recyclerViewDictionariesList);
        DictionariesListAdapter dictionariesListAdapter = new DictionariesListAdapter(this, getDefaultDictionaries());
        recyclerViewDictionariesList.setAdapter(dictionariesListAdapter);
        Log.d("mylog", "DICTIONARIES: " + getDefaultDictionaries());
    }

    private ArrayList<Dictionary> getDefaultDictionaries() {
        ArrayList<Dictionary> dictionaries = new ArrayList<>();
        dictionaries.add(new Dictionary(0, "Английский", "en", "ru", 0, 34, 1));
        dictionaries.add(new Dictionary(1, "Немецкий", "de", "ru", 1, 32, 1));
        dictionaries.add(new Dictionary(2, "Французский", "fr", "ru", 2, 12, 1));
        dictionaries.add(new Dictionary(3, "Испанский", "es", "ru", 4, 0, 1));
        return dictionaries;
    }
}
