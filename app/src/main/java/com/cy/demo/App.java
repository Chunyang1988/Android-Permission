package com.cy.demo;

import android.app.Application;

import com.xiu8.log.XLog;

/**
 * Created by chunyang on 2018/3/13.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XLog.init(null);

    }
}
