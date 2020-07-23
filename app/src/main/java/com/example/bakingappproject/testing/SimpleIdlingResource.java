package com.example.bakingappproject.testing;


import androidx.annotation.Nullable;
import androidx.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleIdlingResource implements IdlingResource {
    @Nullable
    private volatile IdlingResource.ResourceCallback mCallback;

    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);
    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isIdleNow() {
        return false;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {

    }
    public void setIdleState(boolean isIdleNow) {
        mIsIdleNow.set(isIdleNow);
        if (isIdleNow && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}
