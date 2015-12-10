package android.adouggy.github.com.mirrorme.helper;

/**
 * Created by liyazi on 15/12/10.
 */
public class JavaCVHelper {
    public static void init(){
        //        FFmpegLogCallback.set();
    }
    /**
     * 简单粗暴的把视频直接拼接起来
     *
     * @param former
     * @param latter
     * @param output
     */
//    private void concat(File former, File latter, File output) {
//        long timestamp = System.currentTimeMillis();
//
//        logi("left:" + former.getAbsolutePath());
//        logi("right:" + latter.getAbsolutePath());
//        logi("out:" + output.getAbsolutePath());
//
//        FrameGrabber grabber1 = new FFmpegFrameGrabber(former.getAbsoluteFile());
//        try {
//            grabber1.start();
//            loge("graber1.started");
//
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//            loge(e.getMessage());
//        }
//
//        FrameGrabber grabber2 = new FFmpegFrameGrabber(latter.getAbsoluteFile());
//        try {
//            grabber2.start();
//            logi("graber2.started");
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//            loge(e.getMessage());
//        }
//
//        logi("w:" + grabber1.getImageWidth());
//        logi("h:" + grabber1.getImageHeight());
//        logi("rate:" + grabber1.getFrameRate());
//        logi("bitrate:" + grabber1.getVideoBitrate());
//        logi("format:" + grabber1.getSampleFormat());
//        logi("sample rate:" + grabber1.getSampleRate());
//
//        FrameRecorder recorder = new FFmpegFrameRecorder(
//                output.getAbsoluteFile(),
//                grabber1.getImageWidth(),
//                grabber1.getImageHeight());
//
//        recorder.setAudioChannels(1);
//        recorder.setTimestamp(System.currentTimeMillis());
//        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
//        recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
//        recorder.setFormat("mp4");
//
////        recorder.setFrameRate(grabber1.getFrameRate());
////        recorder.setSampleFormat(grabber1.getSampleFormat());
////        recorder.setSampleRate(grabber1.getSampleRate());
//
//
//        try {
//            recorder.start();
//            logi("Recorder.started");
//        } catch (FrameRecorder.Exception e) {
//            e.printStackTrace();
//            loge(e.getMessage());
//        }
//        Frame frame;
//        int j = 0;
//        try {
//            while ((frame = grabber1.grabFrame()) != null) {
//                j++;
//                recorder.record(frame);
//            }
//            logi("total frames:" + j);
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//            loge(e.getMessage());
//        } catch (FrameRecorder.Exception e) {
//            e.printStackTrace();
//            loge(e.getMessage());
//        }
//        logi("after while1");
//        try {
//            j = 0;
//            while ((frame = grabber2.grabFrame()) != null) {
//                j++;
//                recorder.record(frame);
//            }
//            logi("total frames:" + j);
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//            loge(e.getMessage());
//        } catch (FrameRecorder.Exception e) {
//            e.printStackTrace();
//            loge(e.getMessage());
//        }
//        logi("Recorder.stop");
//
//        try {
//            recorder.stop();
//            grabber2.stop();
//            grabber1.stop();
//            logi("All stoped");
//        } catch (FrameRecorder.Exception e) {
//            e.printStackTrace();
//            loge(e.getMessage());
//        } catch (FrameGrabber.Exception e) {
//            e.printStackTrace();
//            loge(e.getMessage());
//        }
//
//        logi("end concatenate");
//
//        logi("Total time:" + (System.currentTimeMillis() - timestamp) / 1000f + " seconds");
//    }
}
