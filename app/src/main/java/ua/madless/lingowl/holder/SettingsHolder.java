package ua.madless.lingowl.holder;

/**
 * Created by User on 14.02.2016.
 */
public class SettingsHolder {
    private static SettingsHolder instance;

    public static SettingsHolder getInstance() {
        return instance;
    }

    private String targetLanguage;
    private String nativeLanguage;

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }
}
