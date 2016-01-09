package ua.madless.lingowl.model;

/**
 * Created by madless on 02.01.2016.
 */
public class Word {
    int id;
    String text;
    String translation;
    String partOfSpeech;
    String gender;
    String number;

    public Word(int id, String text, String translation, String partOfSpeech, String gender, String number) {
        this.id = id;
        this.text = text;
        this.translation = translation;
        this.partOfSpeech = partOfSpeech;
        this.gender = gender;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", translation='" + translation + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", gender='" + gender + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
