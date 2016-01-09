package ua.madless.lingowl.model;

/**
 * Created by madless on 02.01.2016.
 */
public class Category {
    private int id;
    private String name;
    private int iconId;
    private int wordCounter;

    public Category(int id, String name, int iconId, int wordCounter) {
        this.id = id;
        this.name = name;
        this.iconId = iconId;
        this.wordCounter = wordCounter;
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

}
