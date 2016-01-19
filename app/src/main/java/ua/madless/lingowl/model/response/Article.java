package ua.madless.lingowl.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by User on 20.01.2016.
 */
public class Article {
    String text;
    @SerializedName("pos")
    String position;
    @SerializedName("gen")
    String gender;
    @SerializedName("num")
    String number;
    @SerializedName("tr")
    ArrayList<Translation> translations;
}
