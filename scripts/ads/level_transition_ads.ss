// -----------------------------------------------------------------------------
// File: level_transition_ads.ss
// Description: Level Transition Ads Integration
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

// Level Transition Ads Handler
object "LevelTransitionAdsHandler"
{
    private adsManager = null;
    private levelStartTime = 0;
    private lastAdTime = 0;
    private adInterval = 120000; // 2 minutes
    
    state "main"
    {
        // Find the ads manager
        adsManager = Level.findObject("AdsManager");
        
        if(adsManager == null) {
            // Create ads manager if not found
            adsManager = Level.spawn("AdsManager");
        }
        
        levelStartTime = Time.currentTime();
        lastAdTime = levelStartTime;
        
        changeState("monitoring");
    }
    
    state "monitoring"
    {
        // Check if 2 minutes have passed
        if(Time.currentTime() - lastAdTime >= adInterval) {
            if(adsManager != null && adsManager.isInterstitialAdReady()) {
                adsManager.showInterstitialAd();
                lastAdTime = Time.currentTime();
            }
        }
        
        // Wait 5 seconds before next check
        Time.sleep(5000);
    }
    
    state "level_complete"
    {
        // Show interstitial ad on level complete
        if(adsManager != null && adsManager.isInterstitialAdReady()) {
            adsManager.showInterstitialAd();
            lastAdTime = Time.currentTime();
        }
        
        changeState("monitoring");
    }
    
    state "level_failed"
    {
        // Show interstitial ad on level failed
        if(adsManager != null && adsManager.isInterstitialAdReady()) {
            adsManager.showInterstitialAd();
            lastAdTime = Time.currentTime();
        }
        
        changeState("monitoring");
    }
    
    // Public methods
    public function onLevelComplete()
    {
        changeState("level_complete");
    }
    
    public function onLevelFailed()
    {
        changeState("level_failed");
    }
    
    public function showInterstitialAd()
    {
        if(adsManager != null) {
            return adsManager.showInterstitialAd();
        }
        return false;
    }
    
    public function resetTimer()
    {
        lastAdTime = Time.currentTime();
    }
}
