package ua.madless.lingowl.core.model.model_for_ui;

import android.os.Parcel;
import android.os.Parcelable;

import ua.madless.lingowl.core.model.db_model.Category;

/**
 * Created by User on 03.03.2016.
 */
public class CheckableCategory extends Category {

    private boolean isChecked;

    public CheckableCategory(Category category) {
        super(category.getId(), category.getName(), category.getIconId(), category.getWordCounter());
    }

    public CheckableCategory(int id, String name, int iconId, int wordCounter, boolean isChecked) {
        super(id, name, iconId, wordCounter);
        this.isChecked = isChecked;
    }

    public CheckableCategory(String name, int iconId, boolean isChecked) {
        super(name, iconId);
        this.isChecked = isChecked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    CheckableCategory(Parcel source) {
        super(source);
        setIsChecked(source.readByte() > 0);
    }

    public final static Parcelable.Creator<CheckableCategory> CREATOR = new Parcelable.Creator<CheckableCategory>() {
        @Override
        public CheckableCategory createFromParcel(Parcel source) {
            return new CheckableCategory(source);
        }

        @Override
        public CheckableCategory[] newArray(int size) {
            return new CheckableCategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (isChecked() ? 1 : 0));
    }
}
