
// 外部application程序传递
uniform mat4 uMVPMatrix;
// 只能在vertex shader中使用的变量，通过函数绑定
attribute vec4 vPosition;
attribute vec4 vColor;
// vertex和fragment shader之间做数据传递
varying vec4 fColor;
void main() {
    gl_Position = uMVPMatrix * vPosition;
    fColor = vColor;
}