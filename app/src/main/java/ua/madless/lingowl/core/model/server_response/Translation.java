package ua.madless.lingowl.core.model.server_response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by User on 20.01.2016.
 */
public class Translation {
    @SerializedName("text")
    String text;
    @SerializedName("pos")
    String partOfSpeech;
    @SerializedName("gen")
    String gender;
    @SerializedName("syn")
    ArrayList<Synonym> synonyms;
    @SerializedName("ex")
    ArrayList<Example> examples;

    public Translation(String text, String partOfSpeech, String gender, ArrayList<Synonym> synonyms, ArrayList<Example> examples) {
        this.text = text;
        this.partOfSpeech = partOfSpeech;
        this.gender = gender;
        this.synonyms = synonyms;
        this.examples = examples;
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

    public ArrayList<Synonym> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(ArrayList<Synonym> synonyms) {
        this.synonyms = synonyms;
    }

    public ArrayList<Example> getExamples() {
        return examples;
    }

    public void setExamples(ArrayList<Example> examples) {
        this.examples = examples;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "text='" + text + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", gender='" + gender + '\'' +
                ", synonyms=" + synonyms +
                ", examples=" + examples +
                '}';
    }
}
