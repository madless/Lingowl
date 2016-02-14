package ua.madless.lingowl.model.server_response;

import com.google.gson.annotations.SerializedName;

public class SimpleTranslation {
    @SerializedName("text")
    private String text;

    public SimpleTranslation(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
