#include <jni.h>
#include "OpenGLRender.h"
#include "vector"

using namespace std;

//
// Created by 愛滅 on 2023/9/17.
//

OpenGLRender openGlRender;

void drawVertex(vector<float> &vertex);

void drawLine(vector<float> &vertex1, vector<float> &vertex2);

extern "C"
JNIEXPORT void JNICALL
Java_com_atinytot_vegsp_1v_11_opengl_MyGLRenderer_ndkSurfaceCreated(JNIEnv *env, jobject thiz,
                                                                    jobject asset_manager) {
    // 清空颜色
    glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

    AAssetManager *manager = AAssetManager_fromJava(env, asset_manager);
    if (manager != nullptr) {
        openGlRender.init(manager, "vShader.vert", "fShader.frag");
    }

    glEnable(GL_DEPTH_TEST);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_atinytot_vegsp_1v_11_opengl_MyGLRenderer_ndkSurfaceChanged(JNIEnv *env, jobject thiz,
                                                                    jint x, jint y, jint width,
                                                                    jint height) {
    glViewport(x, y, width, height);
    float aspectRation = (float) width / (float) height;
    // 垂直视场角（以弧度为单位），纵横比，近裁剪面的距离和远裁剪面的距离
    openGlRender.mProjectionMatrix = perspective(radians(45.0f), aspectRation, 1.0f, 100.0f);

    // 眼睛的位置、目标位置和上向量
    vec3 eyePosition(0.0f, 0.0f, -6.0f);
    vec3 targetPosition(0.0f, 0.0f, 0.0f);
    vec3 upVector(0.0f, 1.0f, 0.0f);
    openGlRender.mViewMatrix = lookAt(eyePosition, targetPosition, upVector);
}

float g_angle = 0.0;

extern "C"
JNIEXPORT void JNICALL
Java_com_atinytot_vegsp_1v_11_opengl_MyGLRenderer_ndkDrawFrame(JNIEnv *env, jobject thiz,
                                                               jobject m_vertices,
                                                               jint m_vertex_count) {
    // 清空颜色
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//    openGlRender.draw();

    glUseProgram(openGlRender.mProgram);
//    time_t current_time = time(nullptr);
//    long long time = current_time * 1000LL;
//    long long upTime = current_time % 4000LL;
//    float angle = 0.090f * ((int) upTime);
    // 设置模型矩阵为单位矩阵
    openGlRender.mModelMatrix = mat4(1.0f);
    // 应用旋转变换到模型矩阵
    openGlRender.mModelMatrix = rotate(openGlRender.mModelMatrix, g_angle, vec3(0.0f, 1.0f, 0.0f));
    // Calculate the projection and view transformation
    // 计算投影和视图变换矩阵的乘积，将对象从世界坐标系变换到裁剪空间的变换矩阵
    openGlRender.mMVPMatrix = openGlRender.mProjectionMatrix * openGlRender.mViewMatrix;
    // 计算MVP（模型视图投影）变换矩阵的乘积，将对象从模型坐标系变换到裁剪空间的变换矩阵
    openGlRender.mMVPMatrix = openGlRender.mMVPMatrix * openGlRender.mModelMatrix;

    glUniformMatrix4fv(openGlRender.mMVPMatrixHandle, 1, GL_FALSE,
                       value_ptr(openGlRender.mMVPMatrix));

    // 获取 List 类引用
    jclass listClass = env->GetObjectClass(m_vertices);
    // 获取 List.get 方法的 ID
    jmethodID getMethodID = env->GetMethodID(listClass, "get", "(I)Ljava/lang/Object;");
    // 获取 float[] 类引用
    jclass floatArrayClass = env->FindClass("[F");
    // 获取列表的长度
    jsize listSize = env->CallIntMethod(m_vertices, env->GetMethodID(listClass, "size", "()I"));
    if (listSize != m_vertex_count) return;
    // 创建 vector<float[]> 对象
    vector<vector<float>> vertexVector;
    // 遍历列表中的每个元素
    for (int i = 0; i < listSize; ++i) {
        // 调用 List.get 方法获取元素对象
        jobject floatArrayObject = env->CallObjectMethod(m_vertices, getMethodID, i);
        // 将 jobject 转换为 jfloatArray
        jfloatArray floatArray = (jfloatArray) floatArrayObject;
        // 获取 jfloatArray 对应的指针
        jfloat *floatArrayPtr = env->GetFloatArrayElements(floatArray, nullptr);
        // 获取 jfloatArray 的长度
        jsize arrayLength = env->GetArrayLength(floatArray);
        // 创建 std::vector<float> 对象，并将数据复制到其中
        vector<float> floatVector(floatArrayPtr, floatArrayPtr + arrayLength);
        // 将 std::vector<float> 添加到 vertexVector 中
        vertexVector.push_back(floatVector);
        // 释放 floatArrayPtr
        env->ReleaseFloatArrayElements(floatArray, floatArrayPtr, 0);
    }

    GLuint vbo; // 顶点缓冲对象的标识符
    glGenBuffers(1, &vbo); // 生成一个顶点缓冲对象的标识符
    glBindBuffer(GL_ARRAY_BUFFER, vbo); // 绑定顶点缓冲对象到OpenGL的数组缓冲区
    GLuint cbo; // 颜色缓冲对象的标识符
    glGenBuffers(1, &cbo); // 生成一个颜色缓冲对象的标识符
    glBindBuffer(GL_ARRAY_BUFFER, cbo); // 绑定颜色缓冲对象到OpenGL的数组缓冲区
//        for (int i = 0; i < m_vertex_count; ++i) {
//            drawVertex(vertexVector[i]);
//        }
    for (int i = 0; i < m_vertex_count - 1; ++i) {
        drawLine(vertexVector[i], vertexVector[i + 1]);
    }

    glBindBuffer(GL_ARRAY_BUFFER, 0); // 解绑顶点缓冲对象

    g_angle += 0.1f;
    if (g_angle > 360.0f) {
        g_angle -= 360.0f;
    }
}

OpenGLRender::OpenGLRender() {

}

OpenGLRender::~OpenGLRender() {
    if (mProgram) {
        GLUtil::deleteProgram(mProgram);
    }
}

void OpenGLRender::init(AAssetManager *assetManager, const char *vShaderFileName,
                        const char *fShaderFileName) {
    // 顶点着色器
    AAsset *vFile = AAssetManager_open(assetManager, vShaderFileName, AASSET_MODE_BUFFER);
    size_t vSize = AAsset_getLength(vFile);
    char *vContentBuffer = (char *) malloc(vSize);
    AAsset_read(vFile, vContentBuffer, vSize);
    LOGD("VSHADERS: %s", vContentBuffer);
    AAsset_close(vFile);

    // 片段着色器
    AAsset *fFile = AAssetManager_open(assetManager, fShaderFileName, AASSET_MODE_BUFFER);
    size_t fSize = AAsset_getLength(fFile);
    char *fContentBuffer = (char *) malloc(fSize);
    AAsset_read(fFile, fContentBuffer, fSize);
    LOGD("FSHADERS: %s", fContentBuffer);
    AAsset_close(fFile);

    mProgram = GLUtil::createProgram(vContentBuffer, fContentBuffer, mVertexShader,
                                     mFragmentShader);

    // 获取引用或代理
    mPositionHandle = glGetAttribLocation(mProgram, "vPosition");
    mColorHandle = glGetAttribLocation(mProgram, "vColor");
    mMVPMatrixHandle = glGetUniformLocation(mProgram, "uMVPMatrix");

    // 释放资源
    free(vContentBuffer);
    free(fContentBuffer);
}

void OpenGLRender::draw() {

    GLfloat vVertices[] = {
            1.0f, 1.0f, 0.0f,
            -1.0f, 1.0f, 0.0f,
            -1.0f, -1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
    };

    GLfloat textureCoords[] = {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    GLushort indices[] = {0, 1, 2, 0, 2, 3};

    if (mProgram == 0) return;

    // 使用Program
    glUseProgram(mProgram);

    // 加载
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), vVertices);
    glVertexAttribPointer(3, 2, GL_FLOAT, GL_FALSE, 2 * sizeof(GLfloat), textureCoords);

    // 启动
    glEnableVertexAttribArray(0);

    // 绘制
    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_SHORT, indices);
}

void drawVertex(vector<float> &vertex) {
    glVertexAttrib3f(openGlRender.mPositionHandle, vertex[0], vertex[1], vertex[2]);
    glDrawArrays(GL_POINTS, 0, 1);
}

void drawLine(vector<float> &vertex1, vector<float> &vertex2) {
    vector<float> vertexBuffer;
    // 顶点数据
    float v1[] = {vertex1[0], vertex1[1], vertex1[2]};
    float v2[] = {vertex2[0], vertex2[1], vertex2[2]};
    vertexBuffer.insert(vertexBuffer.end(), begin(v1), end(v1));
    vertexBuffer.insert(vertexBuffer.end(), begin(v2), end(v2));

//    glBufferSubData(GL_ARRAY_BUFFER, 0, vertexBuffer.size() * sizeof(float),
//                    vertexBuffer.data()); // 更新顶点缓冲区数据
    glBufferData(GL_ARRAY_BUFFER, vertexBuffer.size() * sizeof(float), vertexBuffer.data(),
                 GL_STATIC_DRAW);
    glVertexAttribPointer(openGlRender.mPositionHandle, 3, GL_FLOAT, GL_FALSE, 0,
                          nullptr);
    glEnableVertexAttribArray(openGlRender.mPositionHandle);

    vector<float> fragmentBuffer;
    // 片段数据
    float f1[] = {vertex1[3], vertex1[4], vertex1[5], vertex1[6]};
    float f2[] = {vertex2[3], vertex2[4], vertex2[5], vertex2[6]};
    fragmentBuffer.insert(fragmentBuffer.end(), begin(f1), end(f1));
    fragmentBuffer.insert(fragmentBuffer.end(), begin(f2), end(f2));

//    glBufferSubData(GL_ARRAY_BUFFER, 0, fragmentBuffer.size() * sizeof(float),
//                    fragmentBuffer.data()); // 更新颜色缓冲区数据
    glBufferData(GL_ARRAY_BUFFER, fragmentBuffer.size() * sizeof(float), fragmentBuffer.data(),
                 GL_STATIC_DRAW);
    glVertexAttribPointer(openGlRender.mColorHandle, 4, GL_FLOAT, GL_FALSE, 0,
                          nullptr);
    glEnableVertexAttribArray(openGlRender.mColorHandle);

    glDrawArrays(GL_LINES, 0, 2);



//    vertexBuffer.clear();
//    fragmentBuffer.clear();
}