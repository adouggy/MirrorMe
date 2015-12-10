package android.adouggy.github.com.mirrorme;

import android.adouggy.github.com.mirrorme.helper.FFmpegHelper;
import android.adouggy.github.com.mirrorme.helper.ProgressHelper;
import android.adouggy.github.com.mirrorme.helper.ResourceHelper;
import android.adouggy.github.com.mirrorme.helper.ToastHelper;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import static android.adouggy.github.com.mirrorme.util.LogUtil.logi;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "MirrorMe";
    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressHelper.INSTANCE.init(this);
        ToastHelper.INSTANCE.init(this);
        ResourceHelper.INSTANCE.init(this);
        FFmpegHelper.INSTANCE.init(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        // Configure the GLSurfaceView.  This will start the Renderer thread, with an
        // appropriate EGL context.
//        mGLView = (GLSurfaceView) findViewById(R.id.cameraPreview_surfaceView);
//        mGLView.setEGLContextClientVersion(2);     // select GLES 2.0
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch( v.getId() ){
            case R.id.fab:
                FFmpegCommand.VERSION.start();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId() ){
            case R.id.menu_cut:
                FFmpegCommand.CUT.start();
                break;
            case R.id.menu_cut_no_encode:
                FFmpegCommand.CUT_NO_ENCODE.start();
                break;
            case R.id.menu_concat:
                FFmpegCommand.CONCAT.start();
                break;
            case R.id.menu_sideBySide:
                FFmpegCommand.SIDE_BY_SIDE.start();
                break;
            case R.id.menu_scale:
                FFmpegCommand.SCALE.start();
                break;
            case R.id.menu_scale_ks:
                FFmpegCommand.SCALE_KS.start();
                break;
        }
        return (super.onOptionsItemSelected(item));
    }
}
