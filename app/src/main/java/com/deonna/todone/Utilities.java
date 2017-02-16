package com.deonna.todone;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

public class Utilities {

    public static void hideSoftKeyboard(View view, Activity activity) {

        final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void initializePriorityListeners(View convertView, int lowPriorityId, int mediumPriority, int highPriority) {

        final ImageView ivLowPriority = (ImageView) convertView.findViewById(lowPriorityId);
        final ImageView ivMediumPriority = (ImageView) convertView.findViewById(mediumPriority);
        final ImageView ivHighPriority = (ImageView) convertView.findViewById(highPriority);

        ivLowPriority.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changePriority(ivLowPriority, ivMediumPriority);
            }
        });

        ivMediumPriority.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changePriority(ivMediumPriority,ivHighPriority);
            }
        });

        ivHighPriority.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changePriority(ivHighPriority, ivLowPriority);
            }
        });
    }

    private static void changePriority(ImageView ivOldPriority, ImageView ivNewPriority) {

        ivOldPriority.setVisibility(View.GONE);
        ivNewPriority.setVisibility(View.VISIBLE);
    }


}
