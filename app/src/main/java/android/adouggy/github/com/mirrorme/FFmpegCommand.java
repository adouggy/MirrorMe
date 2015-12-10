package android.adouggy.github.com.mirrorme;

import android.adouggy.github.com.mirrorme.helper.FFmpegHelper;
import android.adouggy.github.com.mirrorme.helper.ResourceHelper;
import android.adouggy.github.com.mirrorme.helper.ToastHelper;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

import static android.adouggy.github.com.mirrorme.util.LogUtil.logi;

/**
 * Created by liyazi on 15/12/10.
 */
public enum FFmpegCommand {
    SIDE_BY_SIDE(R.raw.ffmpeg_cmd_side_by_side),
    CONCAT(R.raw.ffmpeg_cmd_concat),
    VERSION(R.raw.ffmpeg_cmd_version),
    FILTERS(R.raw.ffmpeg_cmd_filter),
    CUT(R.raw.ffmpeg_cmd_cut),
    CUT_NO_ENCODE(R.raw.ffmpeg_cmd_cut_no_encode),
    SCALE(R.raw.ffmpeg_cmd_scale),
    SCALE_KS(R.raw.ffmpeg_cmd_scale_ks),
    ;

    private int resourceId = -1;

    FFmpegCommand(int resourceId){
        this.resourceId = resourceId;
    }

    public void start(){
        String commandStr = ResourceHelper.INSTANCE.loadStringResource(resourceId);
        switch(resourceId){
            case R.raw.ffmpeg_cmd_concat: {
                final File f1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Video/8ss.mp4");
                final File end = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Video/end.mov");
                final File outFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Video/concat.mp4");
                commandStr = String.format(commandStr, f1.getAbsoluteFile(), end.getAbsoluteFile(), outFile.getAbsoluteFile());
            }
            break;
            case R.raw.ffmpeg_cmd_side_by_side: {
                final File left = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Video/left.mp4");
                final File right = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Video/right.mp4");
                final File outFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Video/sideBySide.mp4");
                commandStr = String.format(commandStr, left.getAbsoluteFile(), right.getAbsoluteFile(), outFile.getAbsoluteFile());
            }
            case R.raw.ffmpeg_cmd_cut:
            case R.raw.ffmpeg_cmd_cut_no_encode:
            {
                final File big = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Video/big.mp4");
                final File cut = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Video/cut.mp4");
                commandStr = String.format(commandStr, big.getAbsoluteFile(), cut.getAbsoluteFile());
            }
            case R.raw.ffmpeg_cmd_scale:
            case R.raw.ffmpeg_cmd_scale_ks:
            {
                final File big = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Video/8s.mp4");
                final File cut = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Video/8ss.mp4");
                commandStr = String.format(commandStr, big.getAbsoluteFile(), cut.getAbsoluteFile());
            }
            break;
        }

        String msg = "running command: ffmpeg" + commandStr;
        logi(msg);
        ToastHelper.INSTANCE.msg(msg);
        String[] cmds = commandStr.split("\n");
        FFmpegHelper.INSTANCE.runCommand(cmds);

    }
}
