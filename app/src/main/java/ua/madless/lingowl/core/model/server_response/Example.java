package ua.madless.lingowl.core.model.server_response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Example {
    @SerializedName("text")
    private String text;
    @SerializedName("tr")
    private ArrayList<SimpleTranslation> translations;

    public Example(String text, ArrayList<SimpleTranslation> translations) {
        this.text = text;
        this.translations = translations;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<SimpleTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(ArrayList<SimpleTranslation> translations) {
        this.translations = translations;
    }
}
