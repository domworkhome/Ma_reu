package com.lamzone.mareu.di;

import android.support.annotation.VisibleForTesting;
import com.lamzone.mareu.services.DummyApiService;
import com.lamzone.mareu.services.ApiService;


public abstract class Di {

    private static ApiService mService = null;

    /**
     *
     * @return the only instance of {@link ApiService}
     */
    public static ApiService getApiService() {
        if (mService == null){
            mService = new DummyApiService();
            return mService;
        }else return mService;
    }

    /**
     *
     *
     * @return a {@link ApiService}
     */
    @VisibleForTesting
    public static ApiService getNewInstanceApiService() {
        return new DummyApiService();
    }
}
