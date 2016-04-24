package ua.madless.lingowl.core.model.converted_server_response;

/**
 * Created by User on 14.02.2016.
 */
public class ConvertedResponseItem {
    private String translation;
    private String partOfSpeech;
    private String gender;

    public ConvertedResponseItem(String translation, String partOfSpeech, String gender) {
        this.translation = translation;
        this.partOfSpeech = partOfSpeech;
        this.gender = gender;
    }

    public String getTranslation() {
        return translation;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "ConvertedResponseItem{" +
                "translation='" + translation + '\'' +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
