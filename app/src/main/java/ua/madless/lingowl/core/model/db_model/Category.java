package ua.madless.lingowl.core.model.db_model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by madless on 02.01.2016.
 */
public class Category implements Parcelable {
    private int id;
    private String name;
    private int iconId;

    public void setWordCounter(int wordCounter) {
        this.wordCounter = wordCounter;
    }

    private int wordCounter;

    public Category(int id, String name, int iconId, int wordCounter) {
        this.id = id;
        this.name = name;
        this.iconId = iconId;
        this.wordCounter = wordCounter;
    }

    public Category(String name, int iconId) {
        this.name = name;
        this.iconId = iconId;
        this.wordCounter = 0;
        this.id = -1;
    }

    public int getWordCounter() {
        return wordCounter;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Category(Parcel source) {
        id = source.readInt();
        name = source.readString();
        iconId = source.readInt();
        wordCounter = source.readInt();
    }

    final static Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(iconId);
        dest.writeInt(wordCounter);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", iconId=" + iconId +
                ", wordCounter=" + wordCounter +
                '}';
    }
}
