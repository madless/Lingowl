package ua.madless.lingowl.util;

import android.util.Log;

import java.util.ArrayList;

import ua.madless.lingowl.model.converted_server_response.ConvertedResponseAdapterItem;
import ua.madless.lingowl.model.converted_server_response.ConvertedResponseItem;
import ua.madless.lingowl.model.server_response.Article;
import ua.madless.lingowl.model.server_response.ServerResponse;
import ua.madless.lingowl.model.server_response.Synonym;
import ua.madless.lingowl.model.server_response.Translation;

/**
 * Created by User on 20.01.2016.
 */
public class ServerResponseConverter {
    private static ServerResponseConverter instance;

    public static ServerResponseConverter getInstance() {
        if(instance == null) {
            instance = new ServerResponseConverter();
        }
        return instance;
    }

    private ServerResponseConverter() {}

    public ArrayList<ConvertedResponseItem> convertServerResponse(ServerResponse serverResponse) {
        ArrayList<Article> articles = serverResponse.getArticles();
        ArrayList<Translation> translations = new ArrayList<>();
        ArrayList<ConvertedResponseItem> convertedResponseItems = new ArrayList<>();
        for (Article article: articles) {
            Log.d("mylog", "article: " + article);
            translations.addAll(article.getTranslations());
        }
        for(Translation translation: translations) {
            ConvertedResponseItem convertedResponseItem = new ConvertedResponseItem(translation.getText(), translation.getPartOfSpeech(), translation.getGender());
            convertedResponseItems.add(convertedResponseItem);
            if(translation.getSynonyms() != null) {
                for(Synonym synonym : translation.getSynonyms()) {
                    ConvertedResponseItem convertedResponseItemSynonym = new ConvertedResponseItem(synonym.getText(), synonym.getPartOfSpeech(), synonym.getGender());
                    convertedResponseItems.add(convertedResponseItemSynonym);
                }
            }
        }
        String text = null;
        if(!articles.isEmpty()) {
            text = articles.get(0).getText();
        }
        Log.d("mylog", "WORD: " + text);
        Log.d("mylog", "TRANSLATIONS:" + convertedResponseItems);
        return convertedResponseItems;
    }

    public ArrayList<ConvertedResponseAdapterItem> convertedResponseToAdapterItems(ServerResponse serverResponse){
        ArrayList<ConvertedResponseItem> convertedResponseItems = convertServerResponse(serverResponse);
        ArrayList<ConvertedResponseAdapterItem> convertedResponseAdapterItems = new ArrayList<>(convertedResponseItems.size());
        for(ConvertedResponseItem convertedResponseItem: convertedResponseItems) {
            convertedResponseAdapterItems.add(new ConvertedResponseAdapterItem(convertedResponseItem));
        }
        return convertedResponseAdapterItems;
    }

}
