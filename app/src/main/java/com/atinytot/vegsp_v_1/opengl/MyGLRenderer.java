package com.atinytot.vegsp_v_1.opengl;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

import com.atinytot.vegsp_v_1.R;
import com.atinytot.vegsp_v_1.domain.VertexEvent;
import com.atinytot.vegsp_v_1.utils.FileUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyGLRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "MyGLRenderer";

    static {
        System.loadLibrary("native");
    }

    private static final int COORDS_PER_VERTEX = 3;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int VERTEX_COUNT = 500;
    private static final int VERTEX_STRIDE = COORDS_PER_VERTEX * BYTES_PER_FLOAT;
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int COLOR_COMPONENT_COUNT = 4;
    private static final int TOTAL_COMPONENT_COUNT = POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT;
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    private float[] mModelMatrix = new float[16];
    private int mProgram;
    private int mPositionHandle; // 顶点坐标句柄
    private int mColorHandle; // 颜色矩阵句柄
    private int mMVPMatrixHandle; // 变换矩阵句柄

    private FloatBuffer mVertexBuffer;
    private final Random mRandom = new Random();
    private Context context;
    private List<float[]> mVertices;
    private int mVertexCount;

    private AssetManager assetManager;

    private native void ndkSurfaceCreated(AssetManager assetManager);
    private native void ndkSurfaceChanged(int x, int y, int width, int height);
    private native void ndkDrawFrame(List<float[]> mVertices, int mVertexCount);

    public MyGLRenderer(Context context, AssetManager assetManager) {
        this.context = context;
        this.assetManager = assetManager;

        mVertices = new ArrayList<>();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        } else {
            Log.w(TAG, "MyGLRenderer: " + "请勿重复注册Eventbus");
        }
    }

    public void setVertices(List<float[]> mVertices, int mVertexCount) {
        this.mVertices = mVertices;
        this.mVertexCount = mVertexCount;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(@NonNull VertexEvent event) {
        this.mVertices = event.getMVertices();
        this.mVertexCount = event.getMVertexCount();
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
//        GLES32.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
////        initVertexBuffer();
//        initProgram();
//
//        // 获取句柄
//        mPositionHandle = GLES32.glGetAttribLocation(mProgram, "vPosition");
//        mColorHandle = GLES32.glGetAttribLocation(mProgram, "vColor");
//        mMVPMatrixHandle = GLES32.glGetUniformLocation(mProgram, "uMVPMatrix");
//
//        // 开启深度测试
//        GLES32.glEnable(GLES32.GL_DEPTH_TEST);

        ndkSurfaceCreated(assetManager);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
//        GLES32.glViewport(0, 0, width, height);
//        float aspectRatio = (float) width / height;
//        Matrix.perspectiveM(mProjectionMatrix, 0, 45, aspectRatio, 1f, 100f);
//        Matrix.setLookAtM(mViewMatrix, 0, 0f, 0f, -6f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        ndkSurfaceChanged(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
//        // 清空颜色缓冲区和深度缓冲区
//        GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT | GLES32.GL_DEPTH_BUFFER_BIT);
//        // Set the program to be used by OpenGL
//        GLES32.glUseProgram(mProgram);
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
//        Matrix.setIdentityM(mModelMatrix, 0);
//        Matrix.rotateM(mModelMatrix, 0, angle, 0.0f, 1.0f, 0.0f);
//        // Calculate the projection and view transformation
//        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
//        Matrix.multiplyMM(mMVPMatrix, 0, mMVPMatrix, 0, mModelMatrix, 0);
//        // Set the position attribute
////        mPositionHandle = GLES32.glGetAttribLocation(mProgram, "vPosition");
////        mVertexBuffer.position(0);
////        GLES32.glVertexAttribPointer(mPositionHandle, POSITION_COMPONENT_COUNT, GLES32.GL_FLOAT, false, VERTEX_STRIDE, mVertexBuffer);
////        GLES32.glEnableVertexAttribArray(mPositionHandle);
//        // Set the color attribute
////        mColorHandle = GLES32.glGetAttribLocation(mProgram, "vColor");
////        mVertexBuffer.position(POSITION_COMPONENT_COUNT);
////        GLES32.glVertexAttribPointer(mColorHandle, COLOR_COMPONENT_COUNT, GLES32.GL_FLOAT, false, VERTEX_STRIDE, mVertexBuffer);
////        GLES32.glEnableVertexAttribArray(mColorHandle);
//        // Set the MVP matrix
////        mMVPMatrixHandle = GLES32.glGetUniformLocation(mProgram, "uMVPMatrix");
//        GLES32.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
//        // Draw the vertices
////        GLES32.glDrawArrays(GLES32.GL_POINTS, 0, VERTEX_COUNT);
//
//        for (int i = 0; i < mVertexCount; i++) {
//            drawVertex(mVertices.get(i));
//        }
//        for (int i = 0; i < mVertexCount - 1; i++) {
//            drawLine(mVertices.get(i), mVertices.get(i + 1));
//        }
        ndkDrawFrame(mVertices, mVertexCount);
    }

//    private void initVertexBuffer() {
////        float[] vertices = generateRandomVertices();
//        mVertexBuffer = FloatUtils.getFloatBuffer(vertices, BYTES_PER_FLOAT);
//    }

    private float[] generateRandomVertices() {
        float[] vertices = new float[VERTEX_COUNT * TOTAL_COMPONENT_COUNT];
        for (int i = 0; i < VERTEX_COUNT; i++) {
            int offset = i * TOTAL_COMPONENT_COUNT;
            // Generate random position
            vertices[offset] = mRandom.nextFloat() * 2 - 1;
            vertices[offset + 1] = mRandom.nextFloat() * 2 - 1;
            vertices[offset + 2] = mRandom.nextFloat() * 2 - 1;
            // Generate random color
            vertices[offset + 3] = mRandom.nextFloat();     // R
            vertices[offset + 4] = mRandom.nextFloat();     // G
            vertices[offset + 5] = mRandom.nextFloat();     // B
            vertices[offset + 6] = 1.0f;                    // A
        }
        return vertices;
    }

    // 初始化OpenGL程序
    private void initProgram() {
        // Load vertex shader
        String vertexShaderCode = FileUtils.readRawTextFile(context, R.raw.plant_vert);
//        String vertexShaderCode =
//                "uniform mat4 uMVPMatrix;" +
//                        "attribute vec4 vPosition;" +
//                        "attribute vec4 vColor;" +
//                        "varying vec4 fColor;" +
//                        "void main() {" +
//                        "  gl_Position = uMVPMatrix * vPosition;" +
//                        "  fColor = vColor;" +
//                        "}";
        int vertexShader = loadShader(GLES32.GL_VERTEX_SHADER, vertexShaderCode);

        // Load fragment shader
        String fragmentShaderCode = FileUtils.readRawTextFile(context, R.raw.plant_frag);
//        String fragmentShaderCode =
//                "precision mediump float;" +
//                        "varying vec4 fColor;" +
//                        "void main() {" +
//                        "  gl_FragColor = fColor;" +
//                        "}";
        int fragmentShader = loadShader(GLES32.GL_FRAGMENT_SHADER, fragmentShaderCode);

        // Link shaders to create a program
        mProgram = GLES32.glCreateProgram();
        GLES32.glAttachShader(mProgram, vertexShader);
        GLES32.glAttachShader(mProgram, fragmentShader);
        GLES32.glLinkProgram(mProgram);
        // Set the program to be used by OpenGL
//        GLES32.glUseProgram(mProgram);
    }

    // 加载着色器
    private int loadShader(int type, String shaderCode) {
        int shader = GLES32.glCreateShader(type);
        // 替换着色器对象中的源代码
        GLES32.glShaderSource(shader, shaderCode);
        // 编译 shader 对象
        GLES32.glCompileShader(shader);
        return shader;
    }

    private void drawVertex(float[] vertex) {
        // TODO: Implement vertex drawing using OpenGL ES.
//        GLES32.glUseProgram(mProgram);
        GLES32.glVertexAttrib3f(mPositionHandle, vertex[0], vertex[1], vertex[2]);
        GLES32.glDrawArrays(GLES32.GL_POINTS, 0, 1);
    }

    private void drawLine(float[] vertex1, float[] vertex2) {
        // TODO: Implement line drawing using OpenGL ES.
//        GLES32.glUseProgram(mProgram);
        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(4 * 2 * 3)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        vertexBuffer.put(vertex1[0]).put(vertex1[1]).put(vertex1[2]);
        vertexBuffer.put(vertex2[0]).put(vertex2[1]).put(vertex2[2]);
        vertexBuffer.position(0);
        GLES32.glVertexAttribPointer(mPositionHandle, 3, GLES32.GL_FLOAT, false, 0, vertexBuffer);
        GLES32.glEnableVertexAttribArray(mPositionHandle);

        FloatBuffer fragmentBuffer = ByteBuffer.allocateDirect(4 * 2 * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        fragmentBuffer.put(vertex1[3]).put(vertex1[4]).put(vertex1[5]).put(vertex1[6]);
        fragmentBuffer.put(vertex2[3]).put(vertex2[4]).put(vertex2[5]).put(vertex2[6]);
        fragmentBuffer.position(0);
        GLES32.glVertexAttribPointer(mColorHandle, 4, GLES32.GL_FLOAT, false, 0, fragmentBuffer);
        GLES32.glEnableVertexAttribArray(mColorHandle);
        GLES32.glDrawArrays(GLES32.GL_LINES, 0, 2);
    }
}

//public class MainActivity extends AppCompatActivity implements GLSurfaceView.Renderer {
//    private GLSurfaceView mGLSurfaceView;
//    private float[] mVertices = new float[24]; // 存储顶点坐标的数组
//    private int mProgram; // OpenGL程序
//    private int mPositionHandle; // 顶点坐标句柄
//    private int mMVPMatrixHandle; // 变换矩阵句柄
//    private float mAngleX; // 绕X轴旋转的角度
//    private float mAngleY; // 绕Y轴旋转的角度
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // 创建GLSurfaceView，并设置Renderer
//        mGLSurfaceView = new GLSurfaceView(this);
//        mGLSurfaceView.setEGLContextClientVersion(2);
//        mGLSurfaceView.setRenderer(this);
//        setContentView(mGLSurfaceView);
//        // 定时器定时更新顶点坐标
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                generateVertices();
//
//            }
//        }, 0, 100);
//    }
//    @Override
//    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        // 初始化OpenGL程序
//        mProgram = initProgram();
//        // 获取句柄
//        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
//        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
//        // 开启深度测试
//        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
//    }
//    @Override
//    public void onSurfaceChanged(GL10 gl, int width, int height) {
//        // 设置视口大小
//        GLES20.glViewport(0, 0, width, height);
//    }
//    @Override
//    public void onDrawFrame(GL10 gl) {
//        // 清空颜色缓冲区和深度缓冲区
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
//        // 设置变换矩阵
//        float[] mvpMatrix = new float[16];
//        Matrix.setIdentityM(mvpMatrix, 0);
//        Matrix.rotateM(mvpMatrix, 0, mAngleX, 1, 0, 0);
//        Matrix.rotateM(mvpMatrix, 0, mAngleY, 0, 1, 0);
//        // 绑定顶点坐标数据
//        FloatBuffer vertexBuffer = ByteBuffer.allocateDirect(mVertices.length * 4)
//                .order(ByteOrder.nativeOrder()).asFloatBuffer();
//        vertexBuffer.put(mVertices).position(0);
//        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
//        GLES20.glEnableVertexAttribArray(mPositionHandle);
//        // 绘制立方体
//        GLES20.glUseProgram(mProgram);
//        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);
//        // 更新旋转角度
//        mAngleX += 1.0f;
//        mAngleY += 0.5f;
//    }
//    // 初始化OpenGL程序
//    private int initProgram() {
//        String vertexShaderCode =
//                "attribute vec4 aPosition;\n" +
//                        "uniform mat4 uMVPMatrix;\n" +
//                        "void main() {\n" +
//                        "  gl_Position = uMVPMatrix * aPosition;\n" +
//                        "}";
//        String fragmentShaderCode =
//                "precision mediump float;\n" +
//                        "void main() {\n" +
//                        "  gl_FragColor = vec4(0.5, 0.5, 1.0, 1.0);\n" +
//                        "}";
//        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
//        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
//        int program = GLES20.glCreateProgram();
//        GLES20.glAttachShader(program, vertexShader);
//        GLES20.glAttachShader(program, fragmentShader);
//        GLES20.glLinkProgram(program);
//        return program;
//    }
//    // 加载着色器
//    private int loadShader(int type, String shaderCode) {
//        int shader = GLES20.glCreateShader(type);
//        // 替换着色器对象中的源代码
//        GLES20.glShaderSource(shader, shaderCode);
//        // 编译 shader 对象
//        GLES20.glCompileShader(shader);
//        return shader;
//    }
//    // 生成随机顶点坐标
//    private void generateVertices() {
//        for (int i = 0; i < 24; i++) {
//            mVertices[i] = (float) (Math.random() * 2 - 1);
//        }
//    }
//}