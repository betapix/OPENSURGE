/*
 * Open Surge Engine
 * AdManager.java - AdMob Ads Manager (Testing Implementation)
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
    
    // Google Test Ad Unit IDs - These are safe for testing
    private static final String APP_OPEN_AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294"; // Test App Open Ad
    private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"; // Test Interstitial Ad
    private static final String REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"; // Test Rewarded Ad
    
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
        Log.d(TAG, "Initializing AdMob for testing (stub implementation)...");
        // Stub implementation - will be replaced with real AdMob when AAR is available
        isAppOpenAdAvailable = false;
        isInterstitialAdAvailable = false;
        isRewardedAdAvailable = false;
        
        // Simulate AdMob initialization
        Log.d(TAG, "AdMob test initialization completed");
        Log.d(TAG, "Test Ad Unit IDs configured:");
        Log.d(TAG, "  App Open: " + APP_OPEN_AD_UNIT_ID);
        Log.d(TAG, "  Interstitial: " + INTERSTITIAL_AD_UNIT_ID);
        Log.d(TAG, "  Rewarded: " + REWARDED_AD_UNIT_ID);
    }
    
    // App Open Ad Methods - Stub implementation
    private void loadAppOpenAd() {
        Log.d(TAG, "Loading App Open Ad for testing (stub)...");
        isAppOpenAdAvailable = false;
    }
    
    public void showAppOpenAd() {
        Log.d(TAG, "Showing App Open Ad for testing (stub) - ID: " + APP_OPEN_AD_UNIT_ID);
        // Stub implementation - no real ad shown
    }
    
    // Interstitial Ad Methods - Stub implementation
    private void loadInterstitialAd() {
        Log.d(TAG, "Loading Interstitial Ad for testing (stub)...");
        isInterstitialAdAvailable = false;
    }
    
    public void showInterstitialAd() {
        Log.d(TAG, "Showing Interstitial Ad for testing (stub) - ID: " + INTERSTITIAL_AD_UNIT_ID);
        // Stub implementation - no real ad shown
    }
    
    // Rewarded Ad Methods - Stub implementation
    private void loadRewardedAd() {
        Log.d(TAG, "Loading Rewarded Ad for testing (stub)...");
        isRewardedAdAvailable = false;
    }
    
    public void showRewardedAd() {
        Log.d(TAG, "Showing Rewarded Ad for testing (stub) - ID: " + REWARDED_AD_UNIT_ID);
        // Stub implementation - no real ad shown
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
        Log.d(TAG, "App pause - checking for app open ad (stub)");
        // Show app open ad when app comes to foreground
        if (isAppOpenAdAvailable) {
            showAppOpenAd();
        }
    }
    
    public void onAppResume() {
        Log.d(TAG, "App resume - reloading ads for testing (stub)");
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
        Log.d(TAG, "Game over - showing rewarded ad for testing (stub)");
        // Show rewarded ad on game over
        showRewardedAd();
    }
    
    public void checkInterstitialTimer() {
        Log.d(TAG, "Checking interstitial timer for testing (stub)");
        // Check if 2 minutes have passed and show interstitial
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastInterstitialTime) >= INTERSTITIAL_INTERVAL) {
            showInterstitialAd();
        }
    }
}