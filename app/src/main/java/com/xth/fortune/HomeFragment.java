package com.xth.fortune;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.util.Random;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

public class HomeFragment extends Fragment implements SensorEventListener {

    private static final float SHAKE_THRESHOLD = 12.0f;
    private static final int MIN_TIME_BETWEEN_SHAKES = 1000; // 毫秒
    
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastShakeTime = 0;
    private float lastX = 0, lastY = 0, lastZ = 0;
    
    private Button shakeButton;
    private ImageView fortuneTubeImage;
    private Animation shakeAnimation;
    private Vibrator vibrator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        // 初始化传感器
        sensorManager = (SensorManager) requireActivity().getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        vibrator = (Vibrator) requireActivity().getSystemService(VIBRATOR_SERVICE);
        
        // 初始化视图
        shakeButton = view.findViewById(R.id.shake_button);
        fortuneTubeImage = view.findViewById(R.id.fortune_tube_image);
        
        // 加载动画
        shakeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake_animation);
        
        // 设置按钮点击监听器
        shakeButton.setOnClickListener(v -> shakeFortuneSticks());
        
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // 注册传感器监听器
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    @Override
    public void onPause() {
        super.onPause();
        // 取消注册传感器监听器
        sensorManager.unregisterListener(this);
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        // 检测摇动手势
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            
            float deltaX = Math.abs(lastX - x);
            float deltaY = Math.abs(lastY - y);
            float deltaZ = Math.abs(lastZ - z);
            
            // 如果摇动力度超过阈值，则触发摇签
            if ((deltaX > SHAKE_THRESHOLD || deltaY > SHAKE_THRESHOLD || deltaZ > SHAKE_THRESHOLD) 
                    && System.currentTimeMillis() - lastShakeTime > MIN_TIME_BETWEEN_SHAKES) {
                lastShakeTime = System.currentTimeMillis();
                shakeFortuneSticks();
            }
            
            lastX = x;
            lastY = y;
            lastZ = z;
        }
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 不需要处理
    }
    
    private void shakeFortuneSticks() {
        // 播放震动
        vibrator.vibrate(300);
        
        // 播放摇动动画
        fortuneTubeImage.startAnimation(shakeAnimation);
        
        // 禁用按钮，防止重复点击
        shakeButton.setEnabled(false);
        
        // 延迟后显示结果
        new Handler().postDelayed(() -> {
            // 随机生成签号
            int fortuneNumber = new Random().nextInt(100) + 1;
            
            // 启动结果页面
            Intent intent = new Intent(requireActivity(), ResultActivity.class);
            intent.putExtra("fortune_number", fortuneNumber);
            startActivity(intent);
            
            // 重新启用按钮
            shakeButton.setEnabled(true);
        }, 1500); // 1.5秒后显示结果
    }
} 