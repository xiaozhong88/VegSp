//
// Created by 愛滅 on 2023/9/18.
//

#include "jni.h"
#include "GLUtil.h"

GLUtil::GLUtil() = default;

GLUtil::~GLUtil() = default;

GLuint GLUtil::loadShader(GLenum shaderType, const char *pSource) {
    GLuint shader = GL_FALSE; // 创建一个变量shader，初始值为0
    shader = glCreateShader(shaderType); // 创建一个指定类型的着色器，并将其ID赋值给shader
    if (shader) { // 如果shader非零（创建着色器成功）
        glShaderSource(shader, 1, &pSource, nullptr); // 将着色器源代码绑定到shader对象上
        glCompileShader(shader); // 编译着色器
        GLint compiled = 0; // 创建一个变量compiled，用于存储编译状态
        glGetShaderiv(shader, GL_COMPILE_STATUS, &compiled); // 获取着色器的编译状态
        if (!compiled) { // 如果编译失败
            GLint infoLen = 0; // 创建一个变量infoLen，用于存储日志信息的长度
            glGetShaderiv(shader, GL_INFO_LOG_LENGTH, &infoLen); // 获取日志信息的长度
            if (infoLen) { // 如果日志信息的长度非零
                char *buf = (char *) malloc((size_t) infoLen); // 根据日志信息的长度动态分配内存
                if (buf) {
                    glGetShaderInfoLog(shader, infoLen, nullptr, buf); // 获取着色器的日志信息
                    LOGE("LoadShader Could not compile shader %d:\n%s\n", shaderType,
                         buf); // 打印日志信息
                    free(buf); // 释放内存
                }
            }
            glDeleteShader(shader); // 删除着色器对象
            shader = 0;
        }
    }
    return shader;
}

GLuint GLUtil::createProgram(const char *pVertexShaderSource, const char *pFragShaderSource,
                             GLuint &vertexShaderHandle, GLuint &fragShaderHandle) {
    GLuint program = 0;
    vertexShaderHandle = loadShader(GL_VERTEX_SHADER, pVertexShaderSource);
    if (!vertexShaderHandle) return program;

    fragShaderHandle = loadShader(GL_FRAGMENT_SHADER, pFragShaderSource);
    if (!fragShaderHandle) return program;

    program = glCreateProgram();
    if (program) {
        glAttachShader(program, vertexShaderHandle);
        checkGLError("glAttachShader");
        glAttachShader(program, fragShaderHandle);
        checkGLError("glAttachShader");
        glLinkProgram(program);
        GLint linkStatus = GL_FALSE; // 创建一个变量linkStatus，用于存储链接状态
        glGetProgramiv(program, GL_LINK_STATUS, &linkStatus);

        // 分离着色器的目的并不是为了停止使用它们，而是为了释放着色器对象的资源，因为在链接完成后，程序对象已经包含了着色器的完整信息。着色器对象不再需要保留，可以通过删除它们来释放资源。
        glDetachShader(program, vertexShaderHandle);
        glDeleteShader(vertexShaderHandle);
        vertexShaderHandle = 0;
        glDetachShader(program, fragShaderHandle);
        glDeleteShader(fragShaderHandle);
        fragShaderHandle = 0;

        if (linkStatus != GL_TRUE) {
            GLint infoLen = 0;
            glGetProgramiv(program, GL_INFO_LOG_LENGTH, &infoLen);
            if (infoLen) {
                char *buf = (char *) malloc((size_t) infoLen);
                if (buf) {
                    glGetProgramInfoLog(program, infoLen, nullptr, buf);
                    LOGE("CreateProgram Could not link program:\n%s\n", buf);
                    free(buf);
                }
            }
            glDeleteProgram(program);
            program = 0;
        }
    }
    LOGE("GLUtils::CreateProgram program = %d", program);
    return program;
}

void GLUtil::checkGLError(const char *pGLOperation) {
    for (unsigned int error = glGetError(); error; error = glGetError()) {
        LOGE("CheckGLError GL Operation %s() glError (0x%x)\n", pGLOperation, error);
    }
}

void GLUtil::deleteProgram(GLuint &program) {
    if (program) {
        glUseProgram(0);
        glDeleteProgram(program);
        program = 0;
    }
}