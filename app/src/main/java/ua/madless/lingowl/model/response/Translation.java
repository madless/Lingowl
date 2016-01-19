package ua.madless.lingowl.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by User on 20.01.2016.
 */
public class Translation {
    String text;
    @SerializedName("pos")
    String partOfSpeech;
    @SerializedName("gen")
    String gender;
    @SerializedName("syn")
    ArrayList<Synonym> synonyms;
    @SerializedName("ex")
    ArrayList<Example> examples;
}
