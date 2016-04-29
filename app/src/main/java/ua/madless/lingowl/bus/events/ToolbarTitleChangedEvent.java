package ua.madless.lingowl.bus.events;

/**
 * Created by User on 29.04.2016.
 */
public class ToolbarTitleChangedEvent {
    private String toolbarTitle;
    public ToolbarTitleChangedEvent(String toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
    }

    public String getToolbarTitle() {
        return toolbarTitle;
    }
}
