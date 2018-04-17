package com.piano.littleponi;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@SuppressWarnings("deprecation")
public class GameMain extends AppCompatActivity {

    ImageView imgDo, imgRe, imgMi, imgFa, imgSol, imgLa, imgSi, imgDoo, imgNote1, imgNote2, imgNote3, imgBack;
    LinearLayout imgPianika, imgPiano, layNote;
    RelativeLayout layUtama, rlPiano;
    TextView teksNote;
    Button btnClose;
    private int ss1;
    private int ss2;
    private int ss3;
    private int ss4;
    private int ss5;
    private int ss6;
    private int ss7;
    private int ss8;
    MediaPlayer mpSound1 = new MediaPlayer();
    MediaPlayer mpSound2 = new MediaPlayer();
    MediaPlayer mpSound3 = new MediaPlayer();
    MediaPlayer mpSound4 = new MediaPlayer();
    MediaPlayer mpSound5 = new MediaPlayer();
    MediaPlayer mpSound6 = new MediaPlayer();
    MediaPlayer mpSound7 = new MediaPlayer();
    MediaPlayer mpSound8 = new MediaPlayer();
    MediaPlayer mediaPlayer = new MediaPlayer();
    SoundPool sp;
    int music = 1;
    boolean isFirst1 = false, isFirst2 = false, isFirst3 = false;
    AdView mAdView, mAdViewTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);
        deklarasi();
        action();
    }

    @Override
    public void onPause() {
        // This method should be called in the parent Activity's onPause() method.
        if (mAdView != null) {
            mAdView.pause();
        }
        if (mAdViewTop != null) {
            mAdViewTop.pause();
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
        if (mAdViewTop != null) {
            mAdViewTop.resume();
        }
        Menu.isUtama = true;
    }

    @Override
    public void onDestroy() {
        // This method should be called in the parent Activity's onDestroy() method.
        if (mAdView != null) {
            mAdView.destroy();
        }
        if (mAdViewTop != null) {
            mAdViewTop.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        cekKoneksi();
    }

    private void deklarasi() {
        mediaPlayer = MediaPlayer.create(GameMain.this, R.raw.tok);
        mpSound1 = MediaPlayer.create(GameMain.this, R.raw.hohner_soprano_melodica_01_c3);
        mpSound2 = MediaPlayer.create(GameMain.this, R.raw.hohner_soprano_melodica_02_d3);
        mpSound3 = MediaPlayer.create(GameMain.this, R.raw.hohner_soprano_melodica_03_e3);
        mpSound4 = MediaPlayer.create(GameMain.this, R.raw.hohner_soprano_melodica_04_f3);
        mpSound5 = MediaPlayer.create(GameMain.this, R.raw.hohner_soprano_melodica_05_g3);
        mpSound6 = MediaPlayer.create(GameMain.this, R.raw.hohner_soprano_melodica_06_a3);
        mpSound7 = MediaPlayer.create(GameMain.this, R.raw.hohner_soprano_melodica_07_b3);
        mpSound8 = MediaPlayer.create(GameMain.this, R.raw.hohner_soprano_melodica_08_c4);

        mAdView = findViewById(R.id.adview);
        mAdViewTop = findViewById(R.id.adviewTop);
        layNote = findViewById(R.id.linearTeks);
        layNote.setVisibility(View.GONE);
        teksNote = findViewById(R.id.teksNote);
        btnClose = findViewById(R.id.btnClose);
        layUtama = findViewById(R.id.layUtama);
        imgBack = findViewById(R.id.imgBack);
        imgDo = findViewById(R.id.imgDo);
        imgRe = findViewById(R.id.imgRe);
        imgMi = findViewById(R.id.imgMi);
        imgFa = findViewById(R.id.imgFa);
        imgSol = findViewById(R.id.imgSol);
        imgLa = findViewById(R.id.imgLa);
        imgSi = findViewById(R.id.imgSi);
        imgDoo = findViewById(R.id.imgDoo);
        imgNote1 = findViewById(R.id.imgNote1);
        imgNote2 = findViewById(R.id.imgNote2);
        imgNote3 = findViewById(R.id.imgNote3);
        imgPianika = findViewById(R.id.imgMusicPianika);
        imgPiano = findViewById(R.id.imgMusicPiano);
        imgNote1.setVisibility(View.GONE);
        imgNote2.setVisibility(View.GONE);
        imgNote3.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sp = new SoundPool.Builder().setMaxStreams(5).build();
        } else {
            sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        setMusic();
        rlPiano = findViewById(R.id.rlPiano);
    }

    private void action() {
        mAdView.setVisibility(View.GONE);
        mAdViewTop.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("isTop");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value != null) {
                    if (Integer.parseInt(value) == 1) {
                        mAdView.setVisibility(View.VISIBLE);
                        mAdViewTop.setVisibility(View.GONE);
                    } else {
                        mAdView.setVisibility(View.GONE);
                        mAdViewTop.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
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
        mAdViewTop.loadAd(adRequest);
        mAdViewTop.setAdListener(new AdListener() {

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
        teksNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                layNote.setVisibility(View.VISIBLE);
                teksNote.setVisibility(View.GONE);
                btnClose.setVisibility(View.VISIBLE);
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                btnClose.setVisibility(View.GONE);
                layNote.setVisibility(View.GONE);
                teksNote.setVisibility(View.VISIBLE);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                onBackPressed();
            }
        });
        if (Menu.tema == 0) {
            layUtama.setBackground(getResources().getDrawable(R.drawable.background1));
        } else if (Menu.tema == 1) {
            layUtama.setBackground(getResources().getDrawable(R.drawable.background2));
        } else if (Menu.tema == 2) {
            layUtama.setBackground(getResources().getDrawable(R.drawable.background3));
        } else if (Menu.tema == 3) {
            layUtama.setBackground(getResources().getDrawable(R.drawable.background4));
        } else if (Menu.tema == 4) {
            layUtama.setBackground(getResources().getDrawable(R.drawable.background5));
        } else if (Menu.tema == 5) {
            layUtama.setBackground(getResources().getDrawable(R.drawable.background6));
        }

        if (Menu.temaPiano == 0) {
            rlPiano.setBackground(getResources().getDrawable(R.drawable.pianikamini));
        } else if (Menu.temaPiano == 1) {
            rlPiano.setBackground(getResources().getDrawable(R.drawable.pianikamini2));
        } else if (Menu.temaPiano == 2) {
            rlPiano.setBackground(getResources().getDrawable(R.drawable.pianikamini3));
        } else if (Menu.temaPiano == 3) {
            rlPiano.setBackground(getResources().getDrawable(R.drawable.pianikamini4));
        } else if (Menu.temaPiano == 4) {
            rlPiano.setBackground(getResources().getDrawable(R.drawable.pianikamini5));
        }

        imgPiano.setAlpha(1);
        imgPianika.setAlpha(0.5f);
        imgPiano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music = 1;
                imgPiano.setAlpha(1);
                imgPianika.setAlpha(0.5f);
                setMusic();
            }
        });

        imgPianika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music = 0;
                imgPiano.setAlpha(0.5f);
                imgPianika.setAlpha(1);
                setMusic();
            }
        });

        imgDo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return playSound1(ss1, event);
            }
        });

        imgRe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return playSound2(ss2, event);
            }
        });

        imgMi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return playSound3(ss3, event);
            }
        });

        imgFa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return playSound4(ss4, event);
            }
        });

        imgSol.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return playSound5(ss5, event);
            }
        });

        imgLa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return playSound6(ss6, event);
            }
        });

        imgSi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return playSound7(ss7, event);
            }
        });

        imgDoo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return playSound8(ss8, event);
            }
        });
    }

    private void setMusic() {
        if (music == 0) {
            ss1 = sp.load(GameMain.this, R.raw.hohner_soprano_melodica_01_c3, 1);
            ss2 = sp.load(GameMain.this, R.raw.hohner_soprano_melodica_02_d3, 1);
            ss3 = sp.load(GameMain.this, R.raw.hohner_soprano_melodica_03_e3, 1);
            ss4 = sp.load(GameMain.this, R.raw.hohner_soprano_melodica_04_f3, 1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ss5 = sp.load(GameMain.this, R.raw.hohner_soprano_melodica_05_g3, 1);
                    ss6 = sp.load(GameMain.this, R.raw.hohner_soprano_melodica_06_a3, 1);
                    ss7 = sp.load(GameMain.this, R.raw.hohner_soprano_melodica_07_b3, 1);
                    ss8 = sp.load(GameMain.this, R.raw.hohner_soprano_melodica_08_c4, 1);
                }
            }, 100);
        } else {
            ss1 = sp.load(GameMain.this, R.raw.w40, 1);
            ss2 = sp.load(GameMain.this, R.raw.w41, 1);
            ss3 = sp.load(GameMain.this, R.raw.w42, 1);
            ss4 = sp.load(GameMain.this, R.raw.w43, 1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ss5 = sp.load(GameMain.this, R.raw.w44, 1);
                    ss6 = sp.load(GameMain.this, R.raw.w45, 1);
                    ss7 = sp.load(GameMain.this, R.raw.w46, 1);
                    ss8 = sp.load(GameMain.this, R.raw.w50, 1);
                }
            }, 100);
        }
    }

    private boolean playSound1(int id, MotionEvent motionEvent) {
        Log.d("Motionsss  ", String.valueOf(motionEvent));
        if (motionEvent == null || motionEvent.getAction() == 0) {
            imgDo.setBackgroundResource(R.drawable.do2);
            if (music == 0) {
                mpSound1.start();
            } else {
                sp.play(id, 1.0f, 1.0f, 0, 0, 1.0f);
            }
            imgNote1.setVisibility(View.VISIBLE);
            if (isFirst1) {
                isFirst1 = false;
                imgNote1.setImageDrawable(getResources().getDrawable(R.drawable.musical_note3));
            } else {
                isFirst1 = true;
                imgNote1.setImageDrawable(getResources().getDrawable(R.drawable.musical_note));
            }
            Animation animation = AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin);
            imgNote1.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imgNote1.setVisibility(View.GONE);
                    imgNote1.startAnimation(AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin2));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            if (music != 1) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sp.autoPause();
                    }
                }, 2000);
            }
            imgDo.setBackgroundResource(R.drawable.do1);
        }
        return true;
    }

    private boolean playSound2(int id, MotionEvent motionEvent) {
        Log.d("Motionsss  ", String.valueOf(motionEvent));
        if (motionEvent == null || motionEvent.getAction() == 0) {
            imgRe.setBackgroundResource(R.drawable.re2);
            if (music == 0) {
                mpSound2.start();
            } else {
                sp.play(id, 1.0f, 1.0f, 0, 0, 1.0f);
            }
            imgNote1.setVisibility(View.VISIBLE);
            if (isFirst1) {
                isFirst1 = false;
                imgNote1.setImageDrawable(getResources().getDrawable(R.drawable.musical_note3));
            } else {
                isFirst1 = true;
                imgNote1.setImageDrawable(getResources().getDrawable(R.drawable.musical_note));
            }
            Animation animation = AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin);
            imgNote1.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imgNote1.setVisibility(View.GONE);
                    imgNote1.startAnimation(AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin2));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            if (music != 1) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sp.autoPause();
                    }
                }, 2000);
            }
            imgRe.setBackgroundResource(R.drawable.re1);
        }
        return true;
    }

    private boolean playSound3(int id, MotionEvent motionEvent) {
        Log.d("Motionsss  ", String.valueOf(motionEvent));
        if (motionEvent == null || motionEvent.getAction() == 0) {
            imgMi.setBackgroundResource(R.drawable.mi2);
            if (music == 0) {
                mpSound3.start();
            } else {
                sp.play(id, 1.0f, 1.0f, 0, 0, 1.0f);
            }
            imgNote1.setVisibility(View.VISIBLE);
            if (isFirst1) {
                isFirst1 = false;
                imgNote1.setImageDrawable(getResources().getDrawable(R.drawable.musical_note3));
            } else {
                isFirst1 = true;
                imgNote1.setImageDrawable(getResources().getDrawable(R.drawable.musical_note));
            }
            Animation animation = AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin);
            imgNote1.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imgNote1.setVisibility(View.GONE);
                    imgNote1.startAnimation(AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin2));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {

            imgMi.setBackgroundResource(R.drawable.mi1);
        }
        return true;
    }

    private boolean playSound4(int id, MotionEvent motionEvent) {
        Log.d("Motionsss  ", String.valueOf(motionEvent));
        if (motionEvent == null || motionEvent.getAction() == 0) {
            imgFa.setBackgroundResource(R.drawable.fa2);
            imgNote2.setVisibility(View.VISIBLE);
            if (isFirst2) {
                isFirst2 = false;
                imgNote2.setImageDrawable(getResources().getDrawable(R.drawable.musical_note4));
            } else {
                isFirst2 = true;
                imgNote2.setImageDrawable(getResources().getDrawable(R.drawable.musical_note1));
            }
            if (music == 0) {
                mpSound4.start();
            } else {
                sp.play(id, 1.0f, 1.0f, 0, 0, 1.0f);
            }
            Animation animation = AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin);
            imgNote2.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imgNote2.setVisibility(View.GONE);
                    imgNote2.startAnimation(AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin2));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {

            imgFa.setBackgroundResource(R.drawable.fa1);
        }
        return true;
    }

    private boolean playSound5(int id, MotionEvent motionEvent) {
        Log.d("Motionsss  ", String.valueOf(motionEvent));
        if (motionEvent == null || motionEvent.getAction() == 0) {
            imgNote2.setVisibility(View.VISIBLE);
            if (isFirst2) {
                isFirst2 = false;
                imgNote2.setImageDrawable(getResources().getDrawable(R.drawable.musical_note4));
            } else {
                isFirst2 = true;
                imgNote2.setImageDrawable(getResources().getDrawable(R.drawable.musical_note1));
            }
            imgSol.setBackgroundResource(R.drawable.sol2);
            if (music == 0) {
                mpSound5.start();
            } else {
                sp.play(id, 1.0f, 1.0f, 0, 0, 1.0f);
            }
            Animation animation = AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin);
            imgNote2.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imgNote2.setVisibility(View.GONE);
                    imgNote2.startAnimation(AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin2));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {

            imgSol.setBackgroundResource(R.drawable.sol1);
        }
        return true;
    }

    private boolean playSound6(int id, MotionEvent motionEvent) {
        Log.d("Motionsss  ", String.valueOf(motionEvent));
        if (motionEvent == null || motionEvent.getAction() == 0) {
            imgLa.setBackgroundResource(R.drawable.la2);
            imgNote2.setVisibility(View.VISIBLE);
            if (isFirst2) {
                isFirst2 = false;
                imgNote2.setImageDrawable(getResources().getDrawable(R.drawable.musical_note4));
            } else {
                isFirst2 = true;
                imgNote2.setImageDrawable(getResources().getDrawable(R.drawable.musical_note1));
            }
            if (music == 0) {
                mpSound6.start();
            } else {
                sp.play(id, 1.0f, 1.0f, 0, 0, 1.0f);
            }
            Animation animation = AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin);
            imgNote2.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imgNote2.setVisibility(View.GONE);
                    imgNote2.startAnimation(AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin2));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {

            imgLa.setBackgroundResource(R.drawable.la1);
        }
        return true;
    }

    private boolean playSound7(int id, MotionEvent motionEvent) {
        Log.d("Motionsss  ", String.valueOf(motionEvent));
        if (motionEvent == null || motionEvent.getAction() == 0) {
            imgSi.setBackgroundResource(R.drawable.si2);
            imgNote3.setVisibility(View.VISIBLE);
            if (isFirst3) {
                isFirst3 = false;
                imgNote3.setImageDrawable(getResources().getDrawable(R.drawable.musical_note5));
            } else {
                isFirst3 = true;
                imgNote3.setImageDrawable(getResources().getDrawable(R.drawable.musical_note2));
            }
            Animation animation = AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin);
            imgNote3.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imgNote3.setVisibility(View.GONE);
                    imgNote3.startAnimation(AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin2));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            if (music == 0) {
                mpSound7.start();
            } else {
                sp.play(id, 1.0f, 1.0f, 0, 0, 1.0f);
            }
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {

            imgSi.setBackgroundResource(R.drawable.si);
        }
        return true;
    }

    private boolean playSound8(int id, MotionEvent motionEvent) {
        Log.d("Motionsss  ", String.valueOf(motionEvent));
        if (motionEvent == null || motionEvent.getAction() == 0) {
            imgDoo.setBackgroundResource(R.drawable.doo2);
            imgNote3.setVisibility(View.VISIBLE);
            if (isFirst3) {
                isFirst3 = false;
                imgNote3.setImageDrawable(getResources().getDrawable(R.drawable.musical_note5));
            } else {
                isFirst3 = true;
                imgNote3.setImageDrawable(getResources().getDrawable(R.drawable.musical_note2));
            }
            if (music == 0) {
                mpSound8.start();
            } else {
                sp.play(id, 1.0f, 1.0f, 0, 0, 1.0f);
            }
            Animation animation = AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin);
            imgNote3.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    imgNote3.setVisibility(View.GONE);
                    imgNote3.startAnimation(AnimationUtils.loadAnimation(GameMain.this, R.anim.zoomin2));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {

            imgDoo.setBackgroundResource(R.drawable.doo1);
        }
        return true;
    }

    private void iklan() {
        final InterstitialAd mInterstitialAd = new InterstitialAd(GameMain.this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5730449577374867/1846252490");
        mInterstitialAd.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                finish();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                finish();
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void cekKoneksi() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr != null ? conMgr.getActiveNetworkInfo() : null;
        if (netInfo == null) {
            finish();
        } else {
            iklan();
        }
    }
}
