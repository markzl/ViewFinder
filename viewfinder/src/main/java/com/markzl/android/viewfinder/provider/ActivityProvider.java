package com.markzl.android.viewfinder.provider;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * <pre>
 * @autor markzl
 * @date 2018/6/7
 * @desc
 * </pre>
 */

public class ActivityProvider implements Provider {

    @Override
    public Context getContext(Object source) {
        return ((Activity)source);
    }

    @Override
    public View findView(Object source, int id) {
        return ((Activity)source).findViewById(id);
    }
}
