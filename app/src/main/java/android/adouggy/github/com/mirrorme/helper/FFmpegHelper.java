package android.adouggy.github.com.mirrorme.helper;

import android.app.Activity;
import android.widget.Toast;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicLong;

import static android.adouggy.github.com.mirrorme.util.LogUtil.loge;
import static android.adouggy.github.com.mirrorme.util.LogUtil.logi;


/**
 * Created by liyazi on 15/12/10.
 */
public enum FFmpegHelper {
    INSTANCE;

    private WeakReference<Activity> actRef = null;
    private FFmpeg ffmpeg = null;

    public void init(Activity act) {
        this.actRef = new WeakReference<Activity>(act);
        ffmpeg = loadFFMpegBinary();
    }

    private FFmpeg loadFFMpegBinary() {
        FFmpeg ffmpeg = FFmpeg.getInstance(this.actRef.get().getApplicationContext());
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    loge("loadBinary failed");
                }

                @Override
                public void onSuccess() {
                    logi("loadBinary success");
                }
            });
        } catch (FFmpegNotSupportedException e) {
            loge(e.getMessage());
        }
        return ffmpeg;
    }

    public void runCommand(String[] cmds) {
        final AtomicLong l = new AtomicLong(System.currentTimeMillis());
        try {
            ffmpeg.execute(cmds, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {
                    loge("onFailure:" + s);
                    ToastHelper.INSTANCE.msg("fail");
                }

                @Override
                public void onSuccess(String s) {
                    logi("onSuccess:" + s);
                    ToastHelper.INSTANCE.msg("success:" + s);
                }

                @Override
                public void onProgress(String s) {
                    logi("onProgress:" + s);
                    ProgressHelper.INSTANCE.msg(s);
                }

                @Override
                public void onStart() {
                    logi("onStart");
                }

                @Override
                public void onFinish() {
                    logi("onFinish");
                    long interval = System.currentTimeMillis() - l.get();
                    logi("interval:" + (interval / 1000f));
                    ProgressHelper.INSTANCE.dismiss();
                    ToastHelper.INSTANCE.msg("total:" + (interval/1000f) + " Seconds" );
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            e.printStackTrace();
            loge(e.getMessage());
        }
    }
}
