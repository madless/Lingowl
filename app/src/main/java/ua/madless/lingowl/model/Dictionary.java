package ua.madless.lingowl.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by madless on 02.01.2016.
 */
public class Dictionary implements Parcelable {
    int id;
    String name;
    String codeTargetLanguage;
    String codeNativeLanguage;
    int iconId;
    int wordCounter;

    public Dictionary(int id, String name, String codeTargetLanguage, String codeNativeLanguage, int iconId, int wordCounter) {
        this.id = id;
        this.name = name;
        this.codeTargetLanguage = codeTargetLanguage;
        this.codeNativeLanguage = codeNativeLanguage;
        this.iconId = iconId;
        this.wordCounter = wordCounter;
    }

    public int getWordCounter() {
        return wordCounter;
    }

    public int getIconId() {
        return iconId;
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

    public String getCodeTargetLanguage() {
        return codeTargetLanguage;
    }

    public String getCodeNativeLanguage() {
        return codeNativeLanguage;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", codeTargetLanguage='" + codeTargetLanguage + '\'' +
                ", codeNativeLanguage='" + codeNativeLanguage + '\'' +
                ", iconId=" + iconId +
                "}\n";
    }

    public Dictionary(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        codeTargetLanguage = parcel.readString();
        codeNativeLanguage = parcel.readString();
        iconId = parcel.readInt();
        wordCounter = parcel.readInt();
    }

    public static final Creator<Dictionary> CREATOR = new Creator<Dictionary>() {
        @Override
        public Dictionary createFromParcel(Parcel source) {
            return new Dictionary(source);
        }

        @Override
        public Dictionary[] newArray(int size) {
            return new Dictionary[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getName());
        dest.writeString(getCodeTargetLanguage());
        dest.writeString(getCodeNativeLanguage());
        dest.writeInt(getIconId());
        dest.writeInt(getWordCounter());
    }
}
