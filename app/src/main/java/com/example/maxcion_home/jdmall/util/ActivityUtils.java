package com.example.maxcion_home.jdmall.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by maxcion_home on 2017/9/9.
 */

public class ActivityUtils {
    public static void start2Activity(Context context , Class c, boolean isFinish){
        Intent i = new Intent(context,c);
        context.startActivity(i);
        if (isFinish){
            ((Activity)context).finish();
        }

    }
}
