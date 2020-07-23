package com.example.bakingappproject;

import androidx.test.espresso.IdlingResource;

import com.example.bakingappproject.testing.SimpleIdlingResource;

public class IdlingRes {
    private static SimpleIdlingResource sIdlingResource;
    public static IdlingResource getIdlingResource() {
        if (sIdlingResource == null) {
            sIdlingResource = new SimpleIdlingResource();
        }
        return sIdlingResource;
    }

    public static void setIdleResourceTo(boolean isIdleNow){
        if (sIdlingResource == null) {
            sIdlingResource = new SimpleIdlingResource();
        }
        sIdlingResource.setIdleState(isIdleNow);
    }
}
