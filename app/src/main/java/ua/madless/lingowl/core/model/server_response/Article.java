package ua.madless.lingowl.core.model.server_response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by User on 20.01.2016.
 */
public class Article {
    @SerializedName("text")
    private String text;
    @SerializedName("pos")
    private String position;
    @SerializedName("gen")
    private String gender;
    @SerializedName("num")
    private String number;
    @SerializedName("tr")
    private ArrayList<Translation> translations;

    public Article(String text, String position, String gender, String number, ArrayList<Translation> translations) {
        this.text = text;
        this.position = position;
        this.gender = gender;
        this.number = number;
        this.translations = translations;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ArrayList<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(ArrayList<Translation> translations) {
        this.translations = translations;
    }

    @Override
    public String toString() {
        return "Article{" +
                "text='" + text + '\'' +
                ", position='" + position + '\'' +
                ", gender='" + gender + '\'' +
                ", number='" + number + '\'' +
                ", translations=" + translations +
                '}';
    }
}
