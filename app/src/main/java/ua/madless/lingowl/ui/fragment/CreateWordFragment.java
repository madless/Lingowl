package ua.madless.lingowl.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Vocalizer;
import ru.yandex.speechkit.VocalizerListener;
import ua.madless.lingowl.R;
import ua.madless.lingowl.core.constants.HandlerResponseType;
import ua.madless.lingowl.core.constants.State;
import ua.madless.lingowl.core.constants.YandexApi;
import ua.madless.lingowl.core.manager.Container;
import ua.madless.lingowl.core.model.converted_server_response.ConvertedResponseAdapterItem;
import ua.madless.lingowl.core.model.converted_server_response.ConvertedTranslation;
import ua.madless.lingowl.core.model.server_response.ServerResponse;
import ua.madless.lingowl.ui.adapter.TranslationsListAdapter;
import ua.madless.lingowl.core.util.MeasureUtil;
import ua.madless.lingowl.core.util.ServerResponseConverter;

/**
 * Created by User on 21.02.2016.
 */
public class CreateWordFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, View.OnKeyListener {
    @Bind(R.id.editTextCreateWordWord) EditText editTextInputWord;
    @Bind(R.id.checkBoxAutoTranslate) CheckBox checkBoxAutoTranslate;
    @Bind(R.id.buttonTranslate) Button buttonTranslate;
    @Bind(R.id.editTextCreateWordPickWordCategory) EditText editTextPickWordCategory;
    @Bind(R.id.listViewCreateWordTranslations) ListView listViewTranslations;
    @Bind(R.id.editTextCreateWordInputNote) EditText editTextInputNote;
    @Bind(R.id.wordContent) View wordContent;
    @Bind(R.id.progressBarTranslating) ProgressBar progressBarTranslating;
    @Bind(R.id.inputLayoutCreateWord) TextInputLayout layoutInputWord;
    @Bind(R.id.imageViewCreateWordPlay) ImageView imageViewCreateWordPlay;
    @Bind(R.id.progressBarWordPlaying) ProgressBar progressBarWordPlaying;

    Container container;
    Handler handler;
    Vocalizer vocalizer;

    ConvertedTranslation convertedTranslation;
    ArrayList<ConvertedResponseAdapterItem> convertedResponses;
    TranslationsListAdapter translationsAdapter;

    State currentState = State.STANDARD;
    private volatile boolean isTranslated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechKit.getInstance().configure(getActivity(), YandexApi.SPEECH_KIT_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View root = inflater.inflate(R.layout.fragment_create_word_new, null);
        ButterKnife.bind(this, root);

        editTextInputWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(layoutInputWord.isErrorEnabled()) {
                    layoutInputWord.setErrorEnabled(false);
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });
        if(!isTranslated) {
            wordContent.setVisibility(View.GONE);
            wordContent.setAlpha(0);
        }
        this.container = Container.getInstance();
        handler = new ResponseHandler();
        switch (currentState) {
            case STANDARD: {
                break;
            }
            case CUSTOM: {
                break;
            }
        }

        return root;
    }

    @OnClick({R.id.buttonTranslate, R.id.editTextCreateWordPickWordCategory, R.id.imageViewCreateWordPlay})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonTranslate: {
                String word = editTextInputWord.getText().toString();
                String language = container.getSettings().getTargetLanguage() + "-" + container.getSettings().getNativeLanguage();
                progressBarTranslating.setVisibility(View.VISIBLE);
                container.getQueryManager().executeGetWordsLookup(handler, word, language);
                break;
            }
            case R.id.editTextCreateWordPickWordCategory: {
                break;
            }
            case R.id.imageViewCreateWordPlay: {
                progressBarWordPlaying.setVisibility(View.VISIBLE);
                v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_view_click));
                vocalizer = Vocalizer.createVocalizer(container.getSettings().getTargetLanguage(), editTextInputWord.getText().toString(), true, Vocalizer.Voice.JANE);
                vocalizer.setListener(new VocalizerResponsePlayer());
                vocalizer.start();
                break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.ok_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ok:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnItemClick({R.id.listViewCreateWordTranslations})
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for(ConvertedResponseAdapterItem item: convertedResponses) {
            item.setIsSelected(false);
        }
        convertedResponses.get(position).setIsSelected(true);
        translationsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(layoutInputWord.isErrorEnabled()) {
            layoutInputWord.setErrorEnabled(false);
            layoutInputWord.setError(null);
        }
        return true;
    }

    class VocalizerResponsePlayer implements VocalizerListener {

        @Override
        public void onSynthesisBegin(Vocalizer vocalizer) {
            Log.d(CreateWordFragment.class.getCanonicalName(), "Synthesis begin..");
        }

        @Override
        public void onSynthesisDone(Vocalizer vocalizer, Synthesis synthesis) {
            //vocalizer.play();
            progressBarWordPlaying.setVisibility(View.GONE);
            Log.d(CreateWordFragment.class.getCanonicalName(), "Synthesis done");
        }

        @Override
        public void onPlayingBegin(Vocalizer vocalizer) {
            Log.d(CreateWordFragment.class.getCanonicalName(), "Playing begin..");
        }

        @Override
        public void onPlayingDone(Vocalizer vocalizer) {
            Log.d(CreateWordFragment.class.getCanonicalName(), "Playing done");
        }

        @Override
        public void onVocalizerError(Vocalizer vocalizer, ru.yandex.speechkit.Error error) {
            Toast.makeText(getActivity(), "Vocalizer error!", Toast.LENGTH_SHORT).show();
            Log.d(CreateWordFragment.class.getCanonicalName(), error.getString());
        }
    }

    class ResponseHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerResponseType responseType = HandlerResponseType.values()[msg.what];
            switch (responseType) {
                case GET_WORD_LOOKUP: {
                    progressBarTranslating.setVisibility(View.GONE);
                    wordContent.setVisibility(View.INVISIBLE);
                    ServerResponse serverResponse = (ServerResponse) msg.obj;
                    ServerResponseConverter responseConverter = container.getResponseConverter();
                    convertedResponses = responseConverter.convertedResponseToAdapterItems(serverResponse);
                    if(convertedResponses != null && !convertedResponses.isEmpty()) {
                        convertedResponses.get(0).setIsSelected(true);
                        translationsAdapter = new TranslationsListAdapter(getContext(), convertedResponses);
                        listViewTranslations.setAdapter(translationsAdapter);
                        isTranslated = true;
                        wordContent.setVisibility(View.VISIBLE);
                        wordContent.setTranslationY(MeasureUtil.getDp(getActivity(), 100));
                        wordContent.animate().translationY(0).alpha(1);
                    } else {
                        Toast.makeText(getActivity(), "Ошибка, невозможно перевести введенное слово", Toast.LENGTH_SHORT).show();
                        layoutInputWord.setErrorEnabled(true);
                        layoutInputWord.setError("Проверьте введенное слово");
                    }
                    // TODO: 14.02.2016 HANDLE VIEW DATA
                    Log.d("mylog", "GOT WORD LOOKUP!");
                    break;
                }
                default: {
                    Log.d("mylog", "CREATE WORD HANDLER ERROR!");
                    throw new UnsupportedOperationException();
                }
            }
        }
    }
}
