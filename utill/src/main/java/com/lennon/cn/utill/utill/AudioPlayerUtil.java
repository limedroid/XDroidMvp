package com.lennon.cn.utill.utill;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import cn.droidlover.xdroidmvp.log.XLog;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author lennon
 * <p>
 * 作者：lennon on 2019/2/18 16:30
 * <p>
 * 邮箱：1136160757@qq.com
 */
public class AudioPlayerUtil {
    private static final String TAG = "AudioRecordTest";
    private MediaPlayer mPlayer;

    public AudioPlayerUtil() {
    }

    public MediaPlayer start(String mFileName, MediaPlayer.OnCompletionListener listener) {
        if (this.mPlayer == null) {
            this.mPlayer = new MediaPlayer();
        } else {
            this.mPlayer.reset();
        }

        try {
            this.mPlayer.setDataSource(mFileName);
            this.mPlayer.prepareAsync();
            this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            if (listener != null) {
                this.mPlayer.setOnCompletionListener(listener);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }
        return mPlayer;

    }

    public MediaPlayer start(Context context, String mFileUrl, MediaPlayer.OnCompletionListener listener) {
        if (this.mPlayer == null) {
            this.mPlayer = new MediaPlayer();
        } else {
            this.mPlayer.reset();
        }
        try {
            Uri uri = Uri.parse(mFileUrl);
            this.mPlayer.setDataSource(context, uri);
            this.mPlayer.prepareAsync();
            this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });
            if (listener != null) {
                this.mPlayer.setOnCompletionListener(listener);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }
        return mPlayer;

    }

    public void stop() {
        if (this.mPlayer != null) {
            this.mPlayer.stop();
            this.mPlayer.release();
            this.mPlayer = null;
        }

    }
}
