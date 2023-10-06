//
// Created by 愛滅 on 2023/9/19.
//

#ifndef VEGSP_V_1_OPENGLRENDER_H
#define VEGSP_V_1_OPENGLRENDER_H

#include "android/asset_manager.h"
#include "android/asset_manager_jni.h"
#include "utils/GLUtil.h"
#include "GLES3/gl3.h"
#include "glm/glm.hpp"
#include "glm/gtc/matrix_transform.hpp"
#include "glm/gtc/type_ptr.hpp"

#include <stdio.h>
#include <time.h>
#include <unistd.h>

using namespace glm;

class OpenGLRender {
public:
    GLuint mProgram;
    GLuint mVertexShader;
    GLuint mFragmentShader;
    GLint mPositionHandle;
    GLint mColorHandle;
    GLint mMVPMatrixHandle;
    mat4 mProjectionMatrix;
    mat4 mViewMatrix;
    mat4 mModelMatrix;
    mat4 mMVPMatrix;

    OpenGLRender();

    ~OpenGLRender();

    void init(AAssetManager *assetManager, const char *vShaderFileName, const char *fShaderFileName);

    void draw();

};

#endif //VEGSP_V_1_OPENGLRENDER_H
