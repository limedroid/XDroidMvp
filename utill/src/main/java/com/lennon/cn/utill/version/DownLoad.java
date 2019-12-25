package com.lennon.cn.utill.version;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import cn.droidlover.xdroidmvp.log.XLog;
import com.lennon.cn.utill.base.BaseApplication;
import com.lennon.cn.utill.bean.Download;
import com.lennon.cn.utill.utill.StringUtils;
import com.lennon.cn.utill.utill.Utill;
import com.lennon.cn.utill.version.download.DownloadProgressListener;
import io.reactivex.functions.Consumer;

import java.io.File;


/**
 * Created by lennon on 2017/5/25.
 */

public class DownLoad extends Service {
    private String url;
    private DownLoadListener listener;
    int downloadCount = 0;
    private File outputFile;
    private boolean isReDown;
    private Activity activity;

    public DownLoad(Activity activity, String url, boolean isReDown, DownLoadListener listener) {
        this.url = url;
        this.listener = listener;
        this.isReDown = isReDown;
        this.activity = activity;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        DownloadProgressListener listener = new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                int progress = (int) ((bytesRead * 100) / contentLength);
                if ((downloadCount == 0) || progress > downloadCount) {
                    final Download download = new Download();
                    download.setTotalFileSize(contentLength);
                    download.setCurrentFileSize(bytesRead);
                    download.setProgress(progress);
                    downloadCount = progress;
                    if (DownLoad.this.listener != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DownLoad.this.listener.update(download);
                            }
                        });
                    }
                }

            }
        };
        outputFile = new File(BaseApplication.Companion.getDataFile(), Utill.INSTANCE.getFileName(url));

        if (outputFile.exists()) {
            if (isReDown) {
                outputFile.delete();
            } else {
                this.listener.downloadCompleted(outputFile);
                return null;
            }
        }


        String baseUrl = StringUtils.getHostName(url);

        new DownloadAPI(baseUrl, listener).downloadAPK(url, outputFile, new Consumer() {

            @Override
            public void accept(Object o) throws Exception {
                if (DownLoad.this.listener != null) {
                    DownLoad.this.listener.downloadCompleted(outputFile);
                }
            }
        },new Consumer<Throwable>(){

            @Override
            public void accept(Throwable e) throws Exception {
                e.printStackTrace();
                XLog.e("onError: " + e.getMessage());
                outputFile.delete();
                if (DownLoad.this.listener != null) {
                    DownLoad.this.listener.downloadError(outputFile);
                }
                XLog.e("onError: " + e.getMessage());
            }
        });
        return null;
    }

    public interface DownLoadListener {
        void update(Download download);

        void downloadCompleted(File outputFile);

        void downloadError(File outputFile);
    }
}
