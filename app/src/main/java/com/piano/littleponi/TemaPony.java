package com.piano.littleponi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class TemaPony extends AppCompatActivity {

    ImageView imgTema1,imgTema2,imgTema3,imgTema4,imgTema5,imgTema6, imgBack;
    public static int tema = 1;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tema_pony);
        deklarasi();
        action();
    }

    @Override
    public void onPause() {
        // This method should be called in the parent Activity's onPause() method.
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        // This method should be called in the parent Activity's onResume() method.
        if (mAdView != null) {
            mAdView.resume();
        }

    }

    @Override
    public void onDestroy() {
        // This method should be called in the parent Activity's onDestroy() method.
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void deklarasi() {
        mAdView = findViewById(R.id.adview);
        imgBack = findViewById(R.id.imgBack);
        imgTema1 = findViewById(R.id.btnChoose1);
        imgTema2 = findViewById(R.id.btnChoose2);
        imgTema3 = findViewById(R.id.btnChoose3);
        imgTema4 = findViewById(R.id.btnChoose4);
        imgTema5 = findViewById(R.id.btnChoose5);
        imgTema6 = findViewById(R.id.btnChoose6);
    }

    private void action() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgTema1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tema = 1;
                onBackPressed();
            }
        });
        imgTema2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tema = 2;
                onBackPressed();
            }
        });
        imgTema3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tema = 3;
                onBackPressed();
            }
        });
        imgTema4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tema = 4;
                onBackPressed();
            }
        });
        imgTema5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tema = 5;
                onBackPressed();
            }
        });
        imgTema6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tema = 6;
                onBackPressed();
            }
        });
    }
}
