package android.adouggy.github.com.mirrorme.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by liyazi on 15/12/10.
 */
public enum ToastHelper {
    INSTANCE;

    private WeakReference<Context> actRef = null;

    public void init(Context context){
        this.actRef = new WeakReference<Context>(context);
    }


    public void msg(String msg){
        if( this.actRef != null && this.actRef.get()!=null && msg != null ){
            Toast.makeText(this.actRef.get(), msg, Toast.LENGTH_LONG).show();
        }
    }
}
