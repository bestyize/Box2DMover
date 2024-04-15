package com.thewind.box2dmover.xml

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.thewind.box2dmover.R
import com.thewind.box2dmover.databinding.ActivitySplashBinding
import com.thewind.box2dmover.mov.model.WorldParam
import com.thewind.box2dmover.rain.createRain
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        supportActionBar?.hide()

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(100)
            binding.rain.play(
                param = WorldParam(
                    xGravity = 0f,
                    yGravity = 20f,
                    doSleep = true,
                    playTime = 20000
                ),
                list = createRain()
            )
        }

    }
}