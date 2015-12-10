package android.adouggy.github.com.mirrorme.helper;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

/**
 * Created by liyazi on 15/12/10.
 */
public enum ResourceHelper {
    INSTANCE;

    private WeakReference<Context> ref = null;

    public void init(Context context){
        this.ref = new WeakReference<Context>(context);
    }

    public String loadStringResource(final int resourceId) {
        try (
                InputStream is = this.ref.get().getResources().openRawResource(resourceId);
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
        ) {
            String nextLine;
            final StringBuilder body = new StringBuilder();

            try {
                while ((nextLine = br.readLine()) != null) {
                    body.append(nextLine);
                    body.append('\n');
                }
            } catch (IOException e) {
                return null;
            }

            return body.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
