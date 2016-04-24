package ua.madless.lingowl.core.model.server_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 20.01.2016.
 */
public class Synonym {
    @SerializedName("text")
    private String text;
    @SerializedName("pos")
    private String partOfSpeech;
    @SerializedName("gen")
    private String gender;

    public Synonym(String text, String partOfSpeech, String gender) {
        this.text = text;
        this.partOfSpeech = partOfSpeech;
        this.gender = gender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
