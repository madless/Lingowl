package ua.madless.lingowl.manager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import ua.madless.lingowl.R;

/**
 * Created by madless on 06.01.2016.
 */
public class IconManager {
    Context context;

    public IconManager(Context context) {
        this.context = context;
    }

    public Drawable getDictionaryIconById(int iconId) {
        switch (iconId) {
            case 0: {
                return ContextCompat.getDrawable(context, R.drawable.ic_flag_en);
            }
            case 1: {
                return ContextCompat.getDrawable(context, R.drawable.ic_flag_de);
            }
            case 2: {
                return ContextCompat.getDrawable(context, R.drawable.ic_flag_fr);
            }
            case 3: {
                return ContextCompat.getDrawable(context, R.drawable.ic_flag_ru);
            }
            case 4: {
                return ContextCompat.getDrawable(context, R.drawable.ic_flag_es);
            } default: {
                return ContextCompat.getDrawable(context, R.mipmap.ic_unknown);
            }
        }
    }

    public Drawable getCategoryIconById(int iconId) {
        switch (iconId) {
            case 0: {
                return ContextCompat.getDrawable(context, R.mipmap.ic_category_favorite);
            }
            case 1: {
                return ContextCompat.getDrawable(context, R.mipmap.ic_category_books);
            }
            case 2: {
                return ContextCompat.getDrawable(context, R.mipmap.ic_category_family);
            }
            case 3: {
                return ContextCompat.getDrawable(context, R.mipmap.ic_category_movie);
            }
            case 4: {
                return ContextCompat.getDrawable(context, R.mipmap.ic_category_technologies);
            }
            default: {
                return ContextCompat.getDrawable(context, R.mipmap.ic_category_default);
            }
        }
    }
}
