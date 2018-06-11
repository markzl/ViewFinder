package com.markzl.android.viewfinder;

import android.app.Activity;
import android.view.View;


import com.markzl.android.viewfinder.provider.ActivityProvider;
import com.markzl.android.viewfinder.provider.Provider;
import com.markzl.android.viewfinder.provider.ViewProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * @autor markzl
 * @date 2018/6/7
 * @desc
 * </pre>
 */

public class ViewFinder {

    private static final ActivityProvider PROVIDER_ACTIVITY = new ActivityProvider();

    private static final ViewProvider PROVIDER_VIEW = new ViewProvider();

    private static final Map<String,Finder> FINDER_MAP = new HashMap<>();

    public static void inject(View view){
        inject(view,view);
    }

    public static void inject(Activity activity){
        inject(activity,activity,PROVIDER_ACTIVITY);
    }
    public static void inject(Object host,View view){
        inject(host,view,PROVIDER_VIEW);
    }


    public static void inject(Object host, Object source,Provider provider){
        String className = host.getClass().getName();
        Finder finder = FINDER_MAP.get(className);
        try {
            if(finder == null){
                Class<?> finderClass = Class.forName(className +"$$Finder");
                finder = (Finder) finderClass.newInstance();
                FINDER_MAP.put(className,finder);
            }
            finder.inject(host,source,provider);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
