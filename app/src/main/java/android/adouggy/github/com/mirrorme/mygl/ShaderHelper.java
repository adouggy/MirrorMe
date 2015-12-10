package android.adouggy.github.com.mirrorme.mygl;

import android.adouggy.github.com.mirrorme.gles.Drawable2d;
import android.adouggy.github.com.mirrorme.gles.GlUtil;
import android.app.Activity;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

/**
 * Created by liyazi on 15/12/3.
 */
public class ShaderHelper {
    private static final String TAG = "ShaderHelper";
    private final Drawable2d mRectDrawable = new Drawable2d(Drawable2d.Prefab.FULL_RECTANGLE);

    private int programId;
    private int textureTarget = GLES11Ext.GL_TEXTURE_EXTERNAL_OES;
    private int positionLocation;
    private int muMVPMatrixLoc;
    private int muTexMatrixLoc;
    private int maTextureCoordLoc;
    private int maInterfereSwitchLoc;
    private long index = 0;

    public static final float[] IDENTITY_MATRIX;

    static {
        IDENTITY_MATRIX = new float[16];
        Matrix.setIdentityM(IDENTITY_MATRIX, 0);
    }

    private ShaderHelper(int programId) {
        this.programId = programId;
        Log.i(TAG, "using program:" + programId);

        muMVPMatrixLoc = GLES20.glGetUniformLocation(this.programId, "uMVPMatrix");
        Log.i(TAG, "muMVPMatrixLoc:" + this.muMVPMatrixLoc);
        GlUtil.checkLocation(muMVPMatrixLoc, "uMVPMatrix");
        GlUtil.checkGlError("glGetUniformLocation");

        positionLocation = GLES20.glGetAttribLocation(this.programId, "aPosition");
        GlUtil.checkLocation(positionLocation, "aPosition");
        Log.i(TAG, "aPosition attribute location:" + positionLocation);
        GlUtil.checkGlError("glGetAttribLocation");

        muTexMatrixLoc = GLES20.glGetUniformLocation(this.programId, "uTexMatrix");
        GlUtil.checkLocation(muTexMatrixLoc, "uTexMatrix");

        maTextureCoordLoc = GLES20.glGetAttribLocation(this.programId, "aTextureCoord");
        GlUtil.checkLocation(maTextureCoordLoc, "aTextureCoord");

        maInterfereSwitchLoc = GLES20.glGetAttribLocation(this.programId, "aInterfereSwitch");
        GlUtil.checkLocation(maInterfereSwitchLoc, "aInterfereSwitch");
    }

    public static class Builder {

        WeakReference<Activity> activityRef;
        private int vertexShaderResourceId;
        private int fragmentShaderResourceId;

        public Builder setActivity(Activity activity) {
            this.activityRef = new WeakReference<>(activity);
            return this;
        }

        public Builder setVertexShaderResourceId(int id) {
            this.vertexShaderResourceId = id;
            return this;
        }

        public Builder setFragmentShaderResourceId(int id) {
            this.fragmentShaderResourceId = id;
            return this;
        }

        private boolean check() {
            if (this.activityRef == null || this.vertexShaderResourceId <= 0 || this.fragmentShaderResourceId <= 0) {
                return false;
            }
            return true;
        }

        public ShaderHelper build() {
            if (!check()) {
                Log.e(TAG, "Can't build shaderHelper");
                return null;
            }
            int programId = createProgram(this.vertexShaderResourceId, this.fragmentShaderResourceId);
            Log.i(TAG, "Program created: " + programId);

            return new ShaderHelper(programId);
        }

        public String loadStringResource(final int resourceId) {
            try (
                    InputStream is = this.activityRef.get().getResources().openRawResource(resourceId);
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

        /**
         * load shader from android resource
         *
         * @param shaderType        vertex/fragment
         * @param androidResourceId raw id
         * @return
         */
        public int loadShader(int shaderType, int androidResourceId) {
            int shader = GLES20.glCreateShader(shaderType);
            checkGlError("glCreateShader type=" + shaderType);
            String s = loadStringResource(androidResourceId);
            Log.i(TAG, "Shader:" + s);
            GLES20.glShaderSource(shader, s);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, " " + GLES20.glGetShaderInfoLog(shader));
                Log.e(TAG, s);
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
            return shader;
        }

        public int createProgram(int vertexShaderResourceId, int fragmentShaderResourceId) {
            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderResourceId);
            if (vertexShader == 0) {
                return 0;
            }
            Log.i(TAG, "vertex shader:" + vertexShader);
            int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderResourceId);
            if (pixelShader == 0) {
                return 0;
            }
            Log.i(TAG, "fragment shader:" + pixelShader);

            int program = GLES20.glCreateProgram();
            checkGlError("glCreateProgram");
            if (program == 0) {
                Log.e(TAG, "Could not create program");
            }
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }

            return program;
        }
    }

//    private void printMatrix(float[] matrix, int dim) {
//        Log.i(TAG, "print matrix");
//        String tmp = "";
//        for (int i = 0; i < matrix.length; i++) {
//            tmp += matrix[i] + ",";
//            if (i != 0 && i % dim == dim - 1) {
//                Log.i(TAG, tmp);
//                tmp = "";
//            }
//        }
//    }

    public void release() {
        Log.d(TAG, "deleting program " + this.programId);
        GLES20.glDeleteProgram(this.programId);
        this.programId = -1;
    }

//    public void changeProgram(int programId) {
//        release();
//        this.programId = programId;
//    }

    public int createTextureObject() {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        GlUtil.checkGlError("glGenTextures");

        int texId = textures[0];
        GLES20.glBindTexture(this.textureTarget, texId);
        GlUtil.checkGlError("glBindTexture " + texId);

        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S,
                GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T,
                GLES20.GL_CLAMP_TO_EDGE);
        GlUtil.checkGlError("glTexParameter");

        return texId;
    }


    /**
     * Checks to see if a GLES error has been raised.
     */
    public static void checkGlError(String op) {
        int error = GLES20.glGetError();
        if (error != GLES20.GL_NO_ERROR) {
            String msg = op + ": glError 0x" + Integer.toHexString(error);
            Log.e(TAG, msg);
            throw new RuntimeException(msg);
        }
    }

    /**
     * simple draw....for..each..frame
     *
     * @param textureId
     */
    public void draw(int textureId, float[] texMatrix) {
        GlUtil.checkGlError("draw start");

        // Select the program.
        GLES20.glUseProgram(this.programId);
        GlUtil.checkGlError("glUseProgram");

        // Set the texture.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(this.textureTarget, textureId);
        GlUtil.checkGlError("glBindTexture");

        // Copy the model / view / projection matrix over.
        GLES20.glUniformMatrix4fv(muMVPMatrixLoc, 1, false, IDENTITY_MATRIX, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");

        // Copy the texture transformation matrix over.
        GLES20.glUniformMatrix4fv(muTexMatrixLoc, 1, false, texMatrix, 0);
        GlUtil.checkGlError("glUniformMatrix4fv");

        // Enable the "aPosition" vertex attribute.
        GLES20.glEnableVertexAttribArray(this.positionLocation);
        GlUtil.checkGlError("glEnableVertexAttribArray");


        // Connect vertexBuffer to "aPosition".
        GLES20.glVertexAttribPointer(this.positionLocation, mRectDrawable.getCoordsPerVertex(), GLES20.GL_FLOAT, false, mRectDrawable.getVertexStride(), mRectDrawable.getVertexArray());
        GlUtil.checkGlError("glVertexAttribPointer");

        // Enable the "aTextureCoord" vertex attribute.

        GLES20.glEnableVertexAttribArray(maTextureCoordLoc);
        GlUtil.checkGlError("glEnableVertexAttribArray");

        // Connect texBuffer to "aTextureCoord".
        GLES20.glVertexAttribPointer(maTextureCoordLoc, 2, GLES20.GL_FLOAT, false, mRectDrawable.getTexCoordStride(), mRectDrawable.getTexCoordArray());
        GlUtil.checkGlError("glVertexAttribPointer");

        //for interfere switch
        long remain = index %10;
        if( remain==0 || remain==1 || remain==2 ){
            GLES20.glVertexAttrib1f(this.maInterfereSwitchLoc, 1f);
        }else{
            GLES20.glVertexAttrib1f(this.maInterfereSwitchLoc, 0f);
        }

        GlUtil.checkGlError("glUniform1f");

        // Draw the rect.
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, mRectDrawable.getVertexCount());
        GlUtil.checkGlError("glDrawArrays");

        // Done -- disable vertex array, texture, and program.
        GLES20.glDisableVertexAttribArray(this.positionLocation);
        GLES20.glDisableVertexAttribArray(maTextureCoordLoc);
        GLES20.glBindTexture(this.textureTarget, 0);
        GLES20.glUseProgram(0);
        GlUtil.checkGlError("finally");
        index++;
    }



}
