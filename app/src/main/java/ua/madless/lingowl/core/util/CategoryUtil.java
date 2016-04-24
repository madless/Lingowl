package ua.madless.lingowl.core.util;

import java.util.ArrayList;

import ua.madless.lingowl.core.model.db_model.Category;
import ua.madless.lingowl.core.model.model_for_ui.CheckableCategory;

/**
 * Created by User on 20.03.2016.
 */
public class CategoryUtil {
    public static ArrayList<CheckableCategory> convertToCheckable(ArrayList<Category> categories) {
        ArrayList<CheckableCategory> checkableCategories = new ArrayList<>(categories.size());
        for (Category category : categories) {
            checkableCategories.add(new CheckableCategory(category));
        }
        return checkableCategories;
    }
}
