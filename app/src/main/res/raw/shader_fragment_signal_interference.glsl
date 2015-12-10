#extension GL_OES_EGL_image_external : require
precision mediump float;
varying vec2 vTextureCoord;
uniform samplerExternalOES sTexture;
varying float vInterfereSwitch;
void main() {
    vec2 tmp = vTextureCoord;

    if( vInterfereSwitch > 0.5){
        if(vTextureCoord.x < 0.21 && vTextureCoord.x >= 0.2 )
           tmp = vec2(vTextureCoord.x,vTextureCoord.y+0.01);
        if(vTextureCoord.x < 0.2 && vTextureCoord.x >= 0.19 )
            tmp = vec2(vTextureCoord.x,vTextureCoord.y - 0.01);
    }

    if( tmp.x < 0.5){
        gl_FragColor = texture2D(sTexture, tmp);
    } else{
        gl_FragColor = vec4 (0.0, 0.0, 0.0, 0.0);
    }

}