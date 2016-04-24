package ua.madless.lingowl.core.model.converted_server_response;

/**
 * Created by User on 21.02.2016.
 */
public class ConvertedResponseAdapterItem extends ConvertedResponseItem {
    private boolean isSelected;

    public ConvertedResponseAdapterItem(ConvertedResponseItem convertedResponseItem) {
        super(convertedResponseItem.getTranslation(), convertedResponseItem.getPartOfSpeech(), convertedResponseItem.getGender());
    }

    @Override
    public String getTranslation() {
        return super.getTranslation();
    }

    @Override
    public String getPartOfSpeech() {
        return super.getPartOfSpeech();
    }

    @Override
    public String getGender() {
        return super.getGender();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
