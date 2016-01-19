package ua.madless.lingowl.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Example {
    String text;
    @SerializedName("tr")
    ArrayList<SimpleTranslation> translations;
}
