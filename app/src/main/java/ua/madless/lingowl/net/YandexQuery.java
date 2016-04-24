package ua.madless.lingowl.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import ua.madless.lingowl.core.model.db_model.Dictionary;
import ua.madless.lingowl.core.model.db_model.Word;
import ua.madless.lingowl.core.model.YandexDictionaryResponse;

@Deprecated
public class YandexQuery implements Callable<YandexDictionaryResponse> {
    String key;
    Dictionary dictionary;
    Word word;
    URL url;

    public YandexQuery(String key, Dictionary dictionary, Word word) {
        this.key = key;
        this.dictionary = dictionary;
        this.word = word;
    }

    @Override
    public YandexDictionaryResponse call() throws Exception {
        HttpURLConnection connection = (HttpURLConnection) getQueryUrl().openConnection();
        StringBuilder response = new StringBuilder();
        BufferedReader out = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String row;
        while ((row = out.readLine()) != null) {
            response.append(row);
        }
        return new YandexDictionaryResponse(new String(response));
    }

    private URL getQueryUrl() {
        String queryStr = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=" + key + "&lang=" + dictionary.getCodeTargetLanguage() + "-" + dictionary.getCodeNativeLanguage() + "&text=" + word.getText();
        try {
            url = new URL(queryStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
