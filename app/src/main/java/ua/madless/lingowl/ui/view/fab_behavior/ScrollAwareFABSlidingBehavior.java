package ua.madless.lingowl.ui.view.fab_behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by User on 29.04.2016.
 */
public class ScrollAwareFABSlidingBehavior extends FloatingActionButton.Behavior {
    public ScrollAwareFABSlidingBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
                        nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);

        if (dyConsumed > 0 && child.getTranslationY() < 1) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            child.animate().translationY(300);
        } else if (dyConsumed < 0 && child.getTranslationY() > 1) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            child.animate().translationY(0);
        }
    }
}
