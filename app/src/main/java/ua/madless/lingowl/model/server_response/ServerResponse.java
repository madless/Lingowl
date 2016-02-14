package ua.madless.lingowl.model.server_response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by User on 20.01.2016.
 */
public class ServerResponse {
    @SerializedName("def")
    private ArrayList<Article> articles;

    public ServerResponse(ArrayList<Article> articles) {
        this.articles = articles;
    }

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
}
