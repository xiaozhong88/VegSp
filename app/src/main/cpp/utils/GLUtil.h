//
// Created by 愛滅 on 2023/9/18.
//

#ifndef VEGSP_V_1_GLUTIL_H
#define VEGSP_V_1_GLUTIL_H

#include "GLES3/gl3.h"
#include <cstdlib>
#include "LogUtil.h"


class GLUtil {
public:

    GLUtil();

    ~GLUtil();

    static GLuint createProgram(const char *pVertexShaderSource, const char *pFragShaderSource,
                         GLuint &vertexShaderHandle, GLuint &fragShaderHandle);

    static void checkGLError(const char *pGLOperation);

    static void deleteProgram(GLuint &program);
private:
    static GLuint loadShader(GLenum shaderType, const char *pSource);
};


#endif //VEGSP_V_1_GLUTIL_H
