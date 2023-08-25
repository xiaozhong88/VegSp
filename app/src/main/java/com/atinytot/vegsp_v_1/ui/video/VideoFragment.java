package com.atinytot.vegsp_v_1.ui.video;

import android.annotation.SuppressLint;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider.Factory;

import com.atinytot.vegsp_v_1.R;
import com.atinytot.vegsp_v_1.base.BaseFragment;
import com.atinytot.vegsp_v_1.databinding.FragmentVideoBinding;
import com.atinytot.vegsp_v_1.factory.MyViewModelFactory;
import com.atinytot.vegsp_v_1.opengl.MyGLRenderer;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.StyledPlayerView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class VideoFragment extends BaseFragment<FragmentVideoBinding, VideoViewModel> implements View.OnClickListener {

    private static VideoFragment videoFragment;
    private FragmentVideoBinding binding;
    private VideoViewModel videoViewModel;

    private GLSurfaceView surfaceView;
    private List<float[]> mVertices; // 存储顶点坐标的数组
    private int mVertexCount;
    private Random mRandom;
    private MyGLRenderer renderer;

    /**
     * 视频
     **/
    private StyledPlayerView video;
    private ExoPlayer player;
    private RtmpDataSource.Factory dataSourceFactory;
    private MediaSource videoSource;
    private String url = "rtmp://43.139.27.210/live/livestream";
    private Button left, right;

    private VideoFragment() {
        super(FragmentVideoBinding::inflate, VideoViewModel.class, false);
    }

    public static VideoFragment newInstance() {
        if (videoFragment == null) {
            synchronized (VideoFragment.class) {
                if (videoFragment == null) {
                    videoFragment = new VideoFragment();
                }
            }
        }

        return videoFragment;
    }

    @Override
    public void initFragment(@NonNull FragmentVideoBinding binding, @Nullable VideoViewModel viewModel, @Nullable Bundle savedInstanceState) {

        this.binding = binding;
        videoViewModel = viewModel;

        // TODO 实例化
        initView();

        renderer = new MyGLRenderer(requireContext());
        // 指定OpenGL版本
        surfaceView.setEGLContextClientVersion(3);
        surfaceView.setRenderer(renderer);
        surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        // TODO 连接阿里云
        videoViewModel.connect();

        // TODO 准备播放器
        player.setMediaSource(videoSource);
        player.setPlayWhenReady(true);

        left.setOnClickListener(this);
        right.setOnClickListener(this);
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        SavedStateHandle handle = new SavedStateHandle();
//        handle.set("productKey", PRODUCTKEY);
//        handle.set("deviceName", DEVICENAME);
//        handle.set("deviceSecret", DEVICESECRET);
//
//        // 在onCreate实例化displayViewModel，防止内存泄漏，确保fragment重建数据不丢失
//        videoViewModel = new ViewModelProvider(this,
//                (Factory) new MyViewModelFactory(requireActivity().getApplication(), handle))
//                .get(VideoViewModel.class);
//
//    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        binding = FragmentVideoBinding.inflate(inflater, container, false);
//
//        // TODO 实例化
//        initView();
//
//        renderer = new MyGLRenderer(requireContext());
//        // 指定OpenGL版本
//        surfaceView.setEGLContextClientVersion(3);
//        surfaceView.setRenderer(renderer);
//        surfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
//
//        // TODO 连接阿里云
//        videoViewModel.connect();
//
//        // TODO 准备播放器
//        player.setMediaSource(videoSource);
//        player.setPlayWhenReady(true);
//
//        left.setOnClickListener(this);
//        right.setOnClickListener(this);
//
//        return binding.getRoot();
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRandom = new Random();
        mVertices = new ArrayList<>();
        mVertexCount = 0;
        // 定时器定时更新顶点坐标
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                addVertex();
                renderer.setVertices(mVertices, mVertexCount);
            }
        }, 0, 100);

    }

    /**
     * 实例化UI控件
     */
    private void initView() {
        // 实例化 数值 控件
        // 实例化地膜转动按钮
        left = binding.left;
        right = binding.right;
        // 视频
        video = binding.video;
        player = new ExoPlayer.Builder(requireActivity()).
                setRenderersFactory(new DefaultRenderersFactory(requireContext()).
                        setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF)).
                build();

        video.setPlayer(player);
        dataSourceFactory = new RtmpDataSource.Factory();
        videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(url));
        // opengl
        surfaceView = binding.openglView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                videoViewModel.setStatus(0);
                break;
            case R.id.right:
                videoViewModel.setStatus(1);
                break;
        }
    }

    // 生成随机顶点坐标
    private void addVertex() {
        if (mVertexCount < 500) {
            float[] vertex = new float[7];
            vertex[0] = mRandom.nextFloat() * 2 - 1;
            vertex[1] = mRandom.nextFloat() * 2 - 1;
            vertex[2] = mRandom.nextFloat() * 2 - 1;
            vertex[3] = mRandom.nextFloat();
            ;
            vertex[4] = mRandom.nextFloat();
            ;
            vertex[5] = mRandom.nextFloat();
            ;
            vertex[6] = 1.0f;
            mVertices.add(vertex);
            mVertexCount++;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        surfaceView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        surfaceView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        videoViewModel.disconnect();
        videoViewModel.onCleared();
        player.release();
    }

}
