package android.adouggy.github.com.mirrorme.util;

import android.util.Log;

/**
 * Created by liyazi on 15/12/10.
 */
public class LogUtil {
    public static final String TAG = "MirrorMe";
    public static final boolean VOBOSE = true;

    public static void logi(String msg) {
        if (VOBOSE) {
            Log.i(TAG, msg);
        }
    }

    public static void loge(String msg) {
        if (VOBOSE) {
            Log.e(TAG, msg);
        }
    }

}
