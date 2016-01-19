package ua.madless.lingowl.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 20.01.2016.
 */
public class Synonym {
    String text;
    @SerializedName("pos")
    String partOfSpeech;
    @SerializedName("gen")
    String gender;
}
