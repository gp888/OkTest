#extension GL_OES_EGL_image_external : require
precision mediump float;
uniform samplerExternalOES videoTex;
varying vec2 textureCoordinate;

void main() {
    vec4 tc = texture2D(videoTex, textureCoordinate);
    float color = tc.r * 0.3 + tc.g * 0.59 + tc.b * 0.11;//这里进行的颜色变换处理，传说中的黑白滤镜。
    gl_FragColor = vec4(color,color,color,1.0);
}