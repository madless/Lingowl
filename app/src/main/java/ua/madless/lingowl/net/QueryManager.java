package ua.madless.lingowl.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import ua.madless.lingowl.constants.HandlerResponseType;
import ua.madless.lingowl.constants.YandexDictionary;
import ua.madless.lingowl.model.server_response.ServerResponse;

/**
 * Created by madless on 03.01.2016.
 */

public class QueryManager {
    private static QueryManager instance;
    private Retrofit retrofit;
    private DictionaryServerApi serverApi;

    public static QueryManager getInstance() {
        if(instance == null) {
            instance = new QueryManager();
        }
        return instance;
    }

    private QueryManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(YandexDictionary.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverApi = retrofit.create(DictionaryServerApi.class);
    }

    public void executeGetWordsLookup(Handler handler, String word, String language) {
        Call<ServerResponse> serverResponseCall = serverApi.getWordLookup(YandexDictionary.KEY, language, word);
        serverResponseCall.enqueue(getCallback(handler, HandlerResponseType.GET_WORD_LOOKUP));
    }

    private Callback getCallback (final Handler handler, final HandlerResponseType handlerResponseType){
        Callback<ServerResponse> callback = new Callback<ServerResponse>() {
            @Override
            public void onResponse(Response<ServerResponse> response, Retrofit retrofit) {
                ServerResponse serverResponse = response.body();
                Message message = handler.obtainMessage(handlerResponseType.ordinal(), serverResponse);
                handler.sendMessage(message);
                Log.d("mylog_net", "Message sent! Is response != null - " + (serverResponse != null));
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        };
        return callback;
    }

}
