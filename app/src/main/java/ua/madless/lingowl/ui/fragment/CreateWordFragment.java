package ua.madless.lingowl.ui.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.madless.lingowl.R;
import ua.madless.lingowl.constants.HandlerResponseType;
import ua.madless.lingowl.constants.State;
import ua.madless.lingowl.manager.Container;
import ua.madless.lingowl.manager.ServerResponseConverter;
import ua.madless.lingowl.model.converted_server_response.ConvertedResponseItem;
import ua.madless.lingowl.model.converted_server_response.ConvertedTranslation;
import ua.madless.lingowl.model.server_response.ServerResponse;

public class CreateWordFragment extends Fragment implements View.OnClickListener {
    Container container;
    Handler handler;
    private ProgressDialog progressDialog;

    ConvertedTranslation convertedTranslation;
    ArrayList<ConvertedResponseItem> convertedResponses;

    @Bind(R.id.editTextCreateWordInput) EditText editTextCreateWordInput;
    @Bind(R.id.buttonCreateWordTranslate) Button buttonCreateWordTranslate;
    @Bind(R.id.imageViewCreateWordModeSwitch) ImageView imageViewCreateWordModeSwitch;
    @Bind(R.id.spinnerCreateWordCategories) Spinner spinnerCreateWordCategories;
    @Bind(R.id.editTextCreateWordComment) EditText editTextCreateWordComment;
    @Bind(R.id.viewCreateWordMutableContainer) View viewCreateWordMutableContainer;

    Spinner spinnerCreateWordStandardTranslation;
    TextView textViewCreateWordStandardPart;
    TextView textViewCreateWordStandardGender;

    EditText editTextCreateWordCustomTranslation;
    Spinner spinnerCreateWordCustomPart;
    Spinner spinnerCreateWordCustomGender;

    State currentState = State.STANDARD;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_word, null);
        ButterKnife.bind(this, root);
        this.container = Container.getInstance();
        handler = new ResponseHandler();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        switch (currentState) {
            case STANDARD: {
                spinnerCreateWordStandardTranslation = ButterKnife.findById(viewCreateWordMutableContainer, R.id.spinnerCreateWordStandardTranslation);
                textViewCreateWordStandardPart = ButterKnife.findById(viewCreateWordMutableContainer, R.id.textViewCreateWordStandardPart);
                textViewCreateWordStandardGender = ButterKnife.findById(viewCreateWordMutableContainer, R.id.textViewCreateWordStandardGender);
                break;
            }
            case CUSTOM: {
                break;
            }
        }

        return root;
    }

    @OnClick({R.id.buttonCreateWordTranslate, R.id.imageViewCreateWordModeSwitch})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCreateWordTranslate: {
                String word = editTextCreateWordInput.getText().toString();
                String language = container.getSettings().getTargetLanguage() + "-" + container.getSettings().getNativeLanguage();
                progressDialog.setTitle("Перевод...");
                progressDialog.show();
                container.getQueryManager().executeGetWordsLookup(handler, word, language);
                break;
            }
            case R.id.imageViewCreateWordModeSwitch: {
                if(currentState == State.STANDARD) {
                    currentState = State.CUSTOM;
                } else {
                    currentState = State.STANDARD;
                }
                break;
            }
        }
    }

    class ResponseHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            HandlerResponseType responseType = HandlerResponseType.values()[msg.what];
            switch (responseType) {
                case GET_WORD_LOOKUP: {
                    ServerResponse serverResponse = (ServerResponse) msg.obj;
                    ServerResponseConverter responseConverter = container.getResponseConverter();
                    convertedResponses = responseConverter.convertServerResponse(serverResponse);
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
