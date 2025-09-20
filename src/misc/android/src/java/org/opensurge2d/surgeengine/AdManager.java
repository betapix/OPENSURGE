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
import android.os.Handler;
import android.os.Looper;

// AdMob imports for testing
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.appopen.AppOpenAdLoadCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Date;

public class AdManager {
    private static final String TAG = "AdManager";
    
    // Google Test Ad Unit IDs - These are safe for testing
    private static final String APP_OPEN_AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294"; // Test App Open Ad
    private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"; // Test Interstitial Ad
    private static final String REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"; // Test Rewarded Ad
    
    private static AdManager instance;
    private Activity activity;
    private Context context;
    
    // Ads
    private AppOpenAd appOpenAd;
    private InterstitialAd interstitialAd;
    private RewardedAd rewardedAd;
    
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
        Log.d(TAG, "Initializing AdMob for testing...");
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d(TAG, "AdMob initialized successfully for testing");
                loadAppOpenAd();
                loadInterstitialAd();
                loadRewardedAd();
            }
        });
    }
    
    // App Open Ad Methods
    private void loadAppOpenAd() {
        Log.d(TAG, "Loading App Open Ad for testing...");
        AdRequest request = new AdRequest.Builder().build();
        
        AppOpenAd.load(context, APP_OPEN_AD_UNIT_ID, request, new AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(AppOpenAd ad) {
                Log.d(TAG, "App Open Ad loaded successfully for testing");
                appOpenAd = ad;
                isAppOpenAdAvailable = true;
                onAppOpenAdLoaded();
            }
            
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.d(TAG, "App Open Ad failed to load: " + loadAdError.getMessage());
                isAppOpenAdAvailable = false;
                onAppOpenAdFailedToLoad();
            }
        });
    }
    
    public void showAppOpenAd() {
        if (appOpenAd != null && isAppOpenAdAvailable) {
            Log.d(TAG, "Showing App Open Ad for testing...");
            appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.d(TAG, "App Open Ad dismissed");
                    appOpenAd = null;
                    isAppOpenAdAvailable = false;
                    onAppOpenAdClosed();
                    loadAppOpenAd(); // Load next ad
                }
                
                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Log.d(TAG, "App Open Ad failed to show: " + adError.getMessage());
                    appOpenAd = null;
                    isAppOpenAdAvailable = false;
                    loadAppOpenAd(); // Load next ad
                }
            });
            
            appOpenAd.show(activity);
        } else {
            Log.d(TAG, "App Open Ad not ready for testing");
        }
    }
    
    // Interstitial Ad Methods
    private void loadInterstitialAd() {
        Log.d(TAG, "Loading Interstitial Ad for testing...");
        AdRequest request = new AdRequest.Builder().build();
        
        InterstitialAd.load(context, INTERSTITIAL_AD_UNIT_ID, request, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(InterstitialAd ad) {
                Log.d(TAG, "Interstitial Ad loaded successfully for testing");
                interstitialAd = ad;
                isInterstitialAdAvailable = true;
                onInterstitialAdLoaded();
            }
            
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.d(TAG, "Interstitial Ad failed to load: " + loadAdError.getMessage());
                isInterstitialAdAvailable = false;
                onInterstitialAdFailedToLoad();
            }
        });
    }
    
    public void showInterstitialAd() {
        long currentTime = System.currentTimeMillis();
        
        if (interstitialAd != null && isInterstitialAdAvailable && 
            (currentTime - lastInterstitialTime) >= INTERSTITIAL_INTERVAL) {
            
            Log.d(TAG, "Showing Interstitial Ad for testing...");
            interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Interstitial Ad dismissed");
                    interstitialAd = null;
                    isInterstitialAdAvailable = false;
                    lastInterstitialTime = currentTime;
                    onInterstitialAdClosed();
                    loadInterstitialAd(); // Load next ad
                }
                
                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Log.d(TAG, "Interstitial Ad failed to show: " + adError.getMessage());
                    interstitialAd = null;
                    isInterstitialAdAvailable = false;
                    loadInterstitialAd(); // Load next ad
                }
            });
            
            interstitialAd.show(activity);
        } else {
            Log.d(TAG, "Interstitial Ad not ready or too soon for testing");
        }
    }
    
    // Rewarded Ad Methods
    private void loadRewardedAd() {
        Log.d(TAG, "Loading Rewarded Ad for testing...");
        AdRequest request = new AdRequest.Builder().build();
        
        RewardedAd.load(context, REWARDED_AD_UNIT_ID, request, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(RewardedAd ad) {
                Log.d(TAG, "Rewarded Ad loaded successfully for testing");
                rewardedAd = ad;
                isRewardedAdAvailable = true;
                onRewardedAdLoaded();
            }
            
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.d(TAG, "Rewarded Ad failed to load: " + loadAdError.getMessage());
                isRewardedAdAvailable = false;
                onRewardedAdFailedToLoad();
            }
        });
    }
    
    public void showRewardedAd() {
        if (rewardedAd != null && isRewardedAdAvailable) {
            Log.d(TAG, "Showing Rewarded Ad for testing...");
            rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Rewarded Ad dismissed");
                    rewardedAd = null;
                    isRewardedAdAvailable = false;
                    onRewardedAdClosed();
                    loadRewardedAd(); // Load next ad
                }
                
                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Log.d(TAG, "Rewarded Ad failed to show: " + adError.getMessage());
                    rewardedAd = null;
                    isRewardedAdAvailable = false;
                    loadRewardedAd(); // Load next ad
                }
            });
            
            rewardedAd.show(activity, rewardItem -> {
                Log.d(TAG, "Rewarded Ad earned reward: " + rewardItem.getAmount() + " " + rewardItem.getType());
                onRewardedAdEarnedReward();
            });
        } else {
            Log.d(TAG, "Rewarded Ad not ready for testing");
        }
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
        Log.d(TAG, "App pause - checking for app open ad");
        // Show app open ad when app comes to foreground
        if (isAppOpenAdAvailable) {
            showAppOpenAd();
        }
    }
    
    public void onAppResume() {
        Log.d(TAG, "App resume - reloading ads for testing");
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
        Log.d(TAG, "Game over - showing rewarded ad for testing");
        // Show rewarded ad on game over
        showRewardedAd();
    }
    
    public void checkInterstitialTimer() {
        Log.d(TAG, "Checking interstitial timer for testing");
        // Check if 2 minutes have passed and show interstitial
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastInterstitialTime) >= INTERSTITIAL_INTERVAL) {
            showInterstitialAd();
        }
    }
}