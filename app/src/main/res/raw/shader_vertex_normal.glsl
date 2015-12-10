uniform mat4 uMVPMatrix;
uniform mat4 uTexMatrix;
attribute vec4 aPosition;
varying vec2 vTextureCoord;
attribute vec4 aTextureCoord;
attribute float aInterfereSwitch;
varying float vInterfereSwitch;
void main() {
    gl_Position = uMVPMatrix * aPosition;
    vTextureCoord = (uTexMatrix * aTextureCoord).xy;
    vInterfereSwitch = aInterfereSwitch;
}
