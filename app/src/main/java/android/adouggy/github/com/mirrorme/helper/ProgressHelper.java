package android.adouggy.github.com.mirrorme.helper;

import android.adouggy.github.com.mirrorme.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by liyazi on 15/12/10.
 */
public enum ProgressHelper {
    INSTANCE;

    private ProgressDialog progressDialog;
    private TextView tv;
    private WeakReference<Activity> actRef = null;

    public void init(Activity context){
        this.actRef = new WeakReference<Activity>(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(null);

        tv = (TextView) context.findViewById(R.id.tv_log);
        tv.setMovementMethod(new ScrollingMovementMethod());
    }


    public void msg(String msg){
        if( this.actRef != null && this.actRef.get()!=null && msg != null ){
            if( !this.progressDialog.isShowing() ){
                this.progressDialog.show();
            }
            this.progressDialog.setMessage(msg);
            tv.append("\n");
            tv.append(msg);
        }
    }

    public void dismiss(){
        if( this.actRef != null && this.actRef.get()!=null ){
            this.progressDialog.dismiss();
        }
    }
}
