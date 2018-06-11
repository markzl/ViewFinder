package com.markzl.android.viewfinder.provider;

import android.content.Context;
import android.view.View;

/**
 * <pre>
 * @autor markzl
 * @date 2018/6/7
 * @desc
 * </pre>
 */

public class ViewProvider implements Provider {
    @Override
    public Context getContext(Object source) {
        return ((View)source).getContext();
    }

    @Override
    public View findView(Object source, int id) {
        return ((View)source).findViewById(id);
    }
}
