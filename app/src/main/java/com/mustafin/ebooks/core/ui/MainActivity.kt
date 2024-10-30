package com.mustafin.ebooks.core.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mustafin.ebooks.core.ui.navigation.NavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        Запрещаем горизонтальное положение экрана
        Это необходимо при текущем подходе к реализации ридера.
        При повороте экрана текст в ридере может отображаться неправильно
        */
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        // Запрещаем экрану гаснуть
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        enableEdgeToEdge()
        setContent {
            NavigationGraph()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Снимаем флаг при завершении активности, чтобы экран мог гаснуть
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}