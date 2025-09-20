/*
 * Open Surge Engine
 * AdManager.java - AdMob Ads Manager (Stub Implementation)
 * Copyright (C) 2024 Abdul Mueed
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 */

package org.opensurge2d.surgeengine;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class AdManager {
    private static final String TAG = "AdManager";
    
    private static AdManager instance;
    private Activity activity;
    private Context context;
    
    // Timing
    private long lastInterstitialTime = 0;
    private static final long INTERSTITIAL_INTERVAL = 2 * 60 * 1000; // 2 minutes
    private boolean isAppOpenAdAvailable = false;
    private boolean isInterstitialAdAvailable = false;
    private boolean isRewardedAdAvailable = false;
    
    // Native callbacks
    private static native void onAppOpenAdLoaded();
    private static native void onAppOpenAdFailedToLoad();
    private static native void onAppOpenAdClosed();
    private static native void onInterstitialAdLoaded();
    private static native void onInterstitialAdFailedToLoad();
    private static native void onInterstitialAdClosed();
    private static native void onRewardedAdLoaded();
    private static native void onRewardedAdFailedToLoad();
    private static native void onRewardedAdClosed();
    private static native void onRewardedAdEarnedReward();
    
    private AdManager(Activity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        initializeAdMob();
    }
    
    public static synchronized AdManager getInstance(Activity activity) {
        if (instance == null) {
            instance = new AdManager(activity);
        }
        return instance;
    }
    
    private void initializeAdMob() {
        Log.d(TAG, "AdMob initialization skipped - using stub implementation");
        // Stub implementation - AdMob not available
        isAppOpenAdAvailable = false;
        isInterstitialAdAvailable = false;
        isRewardedAdAvailable = false;
    }
    
    // App Open Ad Methods - Stub implementation
    private void loadAppOpenAd() {
        Log.d(TAG, "App Open Ad loading skipped - stub implementation");
        isAppOpenAdAvailable = false;
    }
    
    public void showAppOpenAd() {
        Log.d(TAG, "App Open Ad show skipped - stub implementation");
    }
    
    // Interstitial Ad Methods - Stub implementation
    private void loadInterstitialAd() {
        Log.d(TAG, "Interstitial Ad loading skipped - stub implementation");
        isInterstitialAdAvailable = false;
    }
    
    public void showInterstitialAd() {
        Log.d(TAG, "Interstitial Ad show skipped - stub implementation");
    }
    
    // Rewarded Ad Methods - Stub implementation
    private void loadRewardedAd() {
        Log.d(TAG, "Rewarded Ad loading skipped - stub implementation");
        isRewardedAdAvailable = false;
    }
    
    public void showRewardedAd() {
        Log.d(TAG, "Rewarded Ad show skipped - stub implementation");
    }
    
    // Public methods for native calls
    public boolean isAppOpenAdReady() {
        return isAppOpenAdAvailable;
    }
    
    public boolean isInterstitialAdReady() {
        return isInterstitialAdAvailable;
    }
    
    public boolean isRewardedAdReady() {
        return isRewardedAdAvailable;
    }
    
    public void onAppPause() {
        Log.d(TAG, "App pause - stub implementation");
    }
    
    public void onAppResume() {
        Log.d(TAG, "App resume - stub implementation");
        // Load ads when app resumes
        if (!isAppOpenAdAvailable) {
            loadAppOpenAd();
        }
        if (!isInterstitialAdAvailable) {
            loadInterstitialAd();
        }
        if (!isRewardedAdAvailable) {
            loadRewardedAd();
        }
    }
    
    public void onGameOver() {
        Log.d(TAG, "Game over - stub implementation");
        // Show rewarded ad on game over
        showRewardedAd();
    }
    
    public void checkInterstitialTimer() {
        Log.d(TAG, "Interstitial timer check - stub implementation");
        // Check if 2 minutes have passed and show interstitial
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastInterstitialTime) >= INTERSTITIAL_INTERVAL) {
            showInterstitialAd();
        }
    }
}