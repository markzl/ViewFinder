package com.markzl.android.viewfinder;


import com.markzl.android.viewfinder.provider.Provider;

/**
 * <pre>
 * @autor markzl
 * @date 2018/6/7
 * @desc
 * </pre>
 */

public interface Finder<T> {

    void inject(T host, Object source, Provider provider);
}
