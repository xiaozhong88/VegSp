#version 300 es
uniform mat4 uMVPMatrix;
in vec4 vPosition;
in vec4 vColor;
out vec4 fColor;
void main() {
    gl_Position = uMVPMatrix * vPosition;
    fColor = vColor;
}