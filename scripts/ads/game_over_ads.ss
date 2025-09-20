// -----------------------------------------------------------------------------
// File: game_over_ads.ss
// Description: Game Over Ads Integration
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

// Game Over Ads Handler
object "GameOverAdsHandler"
{
    private adsManager = null;
    private gameOverTime = 0;
    private adShown = false;
    
    state "main"
    {
        // Find the ads manager
        adsManager = Level.findObject("AdsManager");
        
        if(adsManager == null) {
            // Create ads manager if not found
            adsManager = Level.spawn("AdsManager");
        }
        
        changeState("waiting");
    }
    
    state "waiting"
    {
        // Wait for game over condition
        // This will be triggered by the game over scene
    }
    
    state "game_over"
    {
        gameOverTime = Time.currentTime();
        adShown = false;
        
        // Show rewarded ad after 1 second delay
        Time.sleep(1000);
        
        if(adsManager != null && !adShown) {
            if(adsManager.isRewardedAdReady()) {
                adsManager.showRewardedAd();
                adShown = true;
            }
        }
        
        changeState("waiting");
    }
    
    // Public method to trigger game over ads
    public function onGameOver()
    {
        changeState("game_over");
    }
    
    // Public method to show interstitial ad
    public function showInterstitialAd()
    {
        if(adsManager != null) {
            return adsManager.showInterstitialAd();
        }
        return false;
    }
    
    // Public method to show rewarded ad
    public function showRewardedAd()
    {
        if(adsManager != null) {
            return adsManager.showRewardedAd();
        }
        return false;
    }
}
