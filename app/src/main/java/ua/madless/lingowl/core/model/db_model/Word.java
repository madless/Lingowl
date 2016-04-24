package ua.madless.lingowl.core.model.db_model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by madless on 02.01.2016.
 */
public class Word implements Parcelable {
    int id;
    String text;
    String translation;
    String partOfSpeech;
    String gender;
    String number;
    boolean isFavorite;

    public Word(int id, String text, String translation, String partOfSpeech, String gender, String number, boolean isFavorite) {
        this.id = id;
        this.text = text;
        this.translation = translation;
        this.partOfSpeech = partOfSpeech;
        this.gender = gender;
        this.number = number;
        this.isFavorite = isFavorite;
    }

    public Word(String text, String translation, String gender, String partOfSpeech, String number, boolean isFavorite) {
        this.text = text;
        this.translation = translation;
        this.gender = gender;
        this.partOfSpeech = partOfSpeech;
        this.number = number;
        this.isFavorite = isFavorite;
        this.id = -1;
    }

    public Word(int id, String text, String translation, boolean isFavorite) {
        this.id = id;
        this.text = text;
        this.translation = translation;
        this.isFavorite = isFavorite;
        partOfSpeech = new String();
        gender = new String();
        number = new String();
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
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

    Word (Parcel source) {
        id = source.readInt();
        text = source.readString();
        translation = source.readString();
        partOfSpeech = source.readString();
        gender = source.readString();
        number = source.readString();
        isFavorite = source.readByte() > 0;
    }

    final static Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(text);
        dest.writeString(translation);
        dest.writeString(partOfSpeech);
        dest.writeString(gender);
        dest.writeString(number);
        dest.writeByte((byte)(isFavorite? 1 : 0));

    }
}
