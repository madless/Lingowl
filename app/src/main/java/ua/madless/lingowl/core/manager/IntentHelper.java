package ua.madless.lingowl.core.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import ua.madless.lingowl.ui.activity.CreateNewWordActivity;
import ua.madless.lingowl.ui.activity.CategoriesPickerActivity;

/**
 * Created by User on 25.04.2016.
 */
public class IntentHelper {
    public static int RESULT_CODE_OK = 1;
    public static int RESULT_CODE_ERROR = 0;

    public static int SELECT_CATEGORIES_REQUEST_CODE = 1;

    public static void startCreateWordActivity(Activity activity, Bundle bundle) {
        Intent intent = new Intent(activity, CreateNewWordActivity.class);
        activity.startActivity(intent);
    }
    public static void startForResultSelectCategoriesActivity(Activity activity, Bundle bundle) {
        Intent intent = new Intent(activity, CategoriesPickerActivity.class);
        activity.startActivityForResult(intent, SELECT_CATEGORIES_REQUEST_CODE);
    }
}
