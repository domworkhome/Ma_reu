package com.lamzone.mareu.di;

import android.support.annotation.VisibleForTesting;
import com.lamzone.mareu.services.DummyApiService;
import com.lamzone.mareu.services.ApiService;

/**
 * // Created by Stéphane TAILLET on 21/10/2019
 */

public abstract class Di {

    // FIELDS

    private static ApiService mService = new DummyApiService();

    // METHODS

    /**
     *
     * @return the only instance of {@link ApiService}
     */
    public static ApiService getApiService() {
        return mService;
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
