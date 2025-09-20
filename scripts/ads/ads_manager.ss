// -----------------------------------------------------------------------------
// File: ads_manager.ss
// Description: AdMob Ads Manager for Open Surge Engine
// Author: Abdul Mueed
// License: GPL-3.0-or-later
// -----------------------------------------------------------------------------

using SurgeEngine.Level;
using SurgeEngine.Input;
using SurgeEngine.Actor;
using SurgeEngine.Vector2;
using SurgeEngine.Transform;
using SurgeEngine.UI.Text;
using SurgeEngine.Time;
using SurgeEngine.AndroidPlatform;

// Global ads manager object
object "AdsManager"
{
    // Timing variables
    private lastInterstitialTime = 0;
    private interstitialInterval = 120000; // 2 minutes in milliseconds
    private gameStartTime = 0;
    
    // Ad states
    private interstitialAdReady = false;
    private rewardedAdReady = false;
    
    state "main"
    {
        // Initialize ads timing
        gameStartTime = Time.currentTime();
        lastInterstitialTime = gameStartTime;
        
        // Check if ads are ready
        interstitialAdReady = AndroidPlatform.isInterstitialAdReady();
        rewardedAdReady = AndroidPlatform.isRewardedAdReady();
        
        // Start ads timer
        changeState("ads_timer");
    }
    
    state "ads_timer"
    {
        // Check every second for interstitial ads
        if(Time.currentTime() - lastInterstitialTime >= interstitialInterval) {
            if(AndroidPlatform.isInterstitialAdReady()) {
                AndroidPlatform.showInterstitialAd();
                lastInterstitialTime = Time.currentTime();
            }
        }
        
        // Update ad readiness status
        interstitialAdReady = AndroidPlatform.isInterstitialAdReady();
        rewardedAdReady = AndroidPlatform.isRewardedAdReady();
        
        // Wait 1 second before next check
        Time.sleep(1000);
    }
    
    // Public methods for other objects to call
    public function showInterstitialAd()
    {
        if(AndroidPlatform.isInterstitialAdReady()) {
            AndroidPlatform.showInterstitialAd();
            lastInterstitialTime = Time.currentTime();
            return true;
        }
        return false;
    }
    
    public function showRewardedAd()
    {
        if(AndroidPlatform.isRewardedAdReady()) {
            AndroidPlatform.showRewardedAd();
            return true;
        }
        return false;
    }
    
    public function isInterstitialAdReady()
    {
        return AndroidPlatform.isInterstitialAdReady();
    }
    
    public function isRewardedAdReady()
    {
        return AndroidPlatform.isRewardedAdReady();
    }
    
    public function resetInterstitialTimer()
    {
        lastInterstitialTime = Time.currentTime();
    }
}
