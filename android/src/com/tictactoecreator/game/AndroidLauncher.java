package com.tictactoecreator.game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {
    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111";
    private static final String REWARD_VIDEO_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
    private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;
    protected Handler handler;
    protected AdView adView;
    private RewardedVideoAd mAd;
    private InterstitialAd interstitialAd;

    public AndroidLauncher() {
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case SHOW_ADS:
                    {
                        adView.setVisibility(View.VISIBLE);
                        break;
                    }
                    case HIDE_ADS:
                    {
                        adView.setVisibility(View.GONE);
                        break;
                    }
                }
            }
        };
    }

    @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        // Create the layout
        RelativeLayout layout = new RelativeLayout(this);

        // Create the LibGdx View
        View gameView = initializeForView(new TictactoeGame(this), config);

        // Create and setup the AdMob view
        adView = new AdView(this);
        // adView.setBackgroundColor(0xff000000);
        adView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
        adView.setAdUnitId(BANNER_AD_UNIT_ID);

        AdRequest.Builder builder = new AdRequest.Builder();
        AdRequest ad = builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(ad);
        showAds(false);

        // Add the LibGdx view
        layout.addView(gameView);

        // Add the AdMob view
        RelativeLayout.LayoutParams adParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        layout.addView(adView, adParams);

        // Hook it all up
        setContentView(layout);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544/5224354917");
        ///
        // Rewarded video ad
        //
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                loadRewardedVideoAd();
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                loadRewardedVideoAd();
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }
        });
        loadRewardedVideoAd();
        //
        // Interstitial ad
        //
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {}

            @Override
            public void onAdClosed() {
                loadIntersitialAd();
            }
        });
        loadIntersitialAd();
	}

    private void loadIntersitialAd(){
        AdRequest interstitialRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(interstitialRequest);
    }

    private void loadRewardedVideoAd() {
        mAd.loadAd(REWARD_VIDEO_AD_UNIT_ID, new AdRequest.Builder().build());
    }

    // This is the callback that posts a message for the handler
    @Override
    public void showAds(boolean show) {
        handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
    }
    @Override
    public void showVideoAd() {
        runOnUiThread(new Runnable() {
            public void run() {

                if (mAd.isLoaded()) {
                    mAd.show();
                } else {
                    loadRewardedVideoAd();
                }
            }
        });
    }
    @Override
    public void showInterstitial() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (interstitialAd.isLoaded())
                    interstitialAd.show();
                else
                    loadIntersitialAd();
            }
        });
    }
}