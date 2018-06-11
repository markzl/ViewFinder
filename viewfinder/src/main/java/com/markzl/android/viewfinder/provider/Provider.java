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

public interface Provider {

    Context getContext(Object source);

    View findView(Object source, int id);
}
