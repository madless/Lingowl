package ua.madless.lingowl.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by User on 20.01.2016.
 */
public class ServerResponse {
    @SerializedName("def")
    ArrayList<Article> articles;
}
