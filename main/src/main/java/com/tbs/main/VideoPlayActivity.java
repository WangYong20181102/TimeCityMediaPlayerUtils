package com.tbs.main;

import android.Manifest;
import android.app.Service;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Mr.Wang on 2019/3/27 16:42.
 */
public class VideoPlayActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView play;
    private TextView musicLength, musicCur;
    private SeekBar seekBar;

    private MediaPlayer mediaPlayer;

    private AudioManager audioManager;

    private Timer timer;

    private boolean isSeekBarChanging;//互斥变量，防止进度条与定时器冲突。
    private int currentPosition;//当前音乐播放的进度

    SimpleDateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);

        format = new SimpleDateFormat("mm:ss");

        play = findViewById(R.id.play);

        musicLength = (TextView) findViewById(R.id.music_length);
        musicCur = (TextView) findViewById(R.id.music_cur);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new MySeekBar());

        play.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initMediaPlayer();//初始化mediaplayer
        }

    }

    private void initMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource("http://sc1.111ttt.cn:8282/2018/1/03m/13/396131213056.m4a?tflag=1546606800&pin=97bb2268ae26c20fe093fd5b0f04be80#.mp3");//指定音频文件的路径
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.seekTo(0);
                        play.setImageResource(android.R.drawable.ic_media_play);
                    }
                });
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    public void onPrepared(MediaPlayer mp) {
                        seekBar.setMax(mediaPlayer.getDuration());
                        musicLength.setText(format.format(mediaPlayer.getDuration()) + "");
                        musicCur.setText("00:00");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMediaPlayer();
                } else {
                    Toast.makeText(this, "denied access", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                if (!mediaPlayer.isPlaying()) {
                    play.setImageResource(android.R.drawable.ic_media_pause);
                    mediaPlayer.start();//开始播放
                    //监听播放时回调函数
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!isSeekBarChanging) {
                                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        musicCur.setText(format.format(mediaPlayer.getCurrentPosition()) + "");
                                    }
                                });
                            }
                        }
                    }, 0, 50);
                }else {
                    play.setImageResource(android.R.drawable.ic_media_play);
                    mediaPlayer.pause();//暂停播放
                    currentPosition = seekBar.getProgress();
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isSeekBarChanging = true;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /*进度条处理*/
    public class MySeekBar implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
        }

        /*滚动时,应当暂停后台定时器*/
        public void onStartTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = true;
        }

        /*滑动结束后，重新设置值*/
        public void onStopTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = false;
            currentPosition = seekBar.getProgress();
            mediaPlayer.seekTo(seekBar.getProgress());
        }
    }
}
