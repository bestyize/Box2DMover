package com.thewind.box2dmover

import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.updateBounds
import androidx.core.view.doOnPreDraw
import com.thewind.box2dmover.app.App
import com.thewind.box2dmover.effects.router.EffectRouterPage
import com.thewind.box2dmover.mov.model.WorldParam
import com.thewind.box2dmover.rain2.RainViewV2
import com.thewind.box2dmover.rain2.createSnowByType
import com.thewind.box2dmover.ui.theme.BiliPink
import com.thewind.box2dmover.ui.theme.Box2DMoverTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box2DMoverTheme {
               EffectRouterPage()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        window.decorView.findViewById<ViewGroup>(android.R.id.content).doOnPreDraw {

            App.heightPx = window.decorView.height
            App.widthPx = window.decorView.width
        }
    }

    @Composable
    private fun MainPage() {
        val drawable = remember {
            ContextCompat.getDrawable(App.app, R.drawable.duanwu)
        }
        val rainView = remember {
            RainViewV2(this)
        }

        val scope = rememberCoroutineScope()

        Box(modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawable?.updateBounds(0, 0, size.width.toInt(), size.height.toInt())
                drawable?.draw(drawContext.canvas.nativeCanvas)
            }) {
            AndroidView(factory = { rainView }, modifier = Modifier.fillMaxSize())
            Box(modifier = Modifier
                .padding(bottom = 60.dp)
                .height(40.dp)
                .width(200.dp)
                .background(color = BiliPink, shape = RoundedCornerShape(100.dp))
                .align(Alignment.BottomCenter)
                .clickable {
                    scope.launch {
                        rainView.play(
                            param = WorldParam(
                                xGravity = 0f,
                                yGravity = 0.5f,
                                doSleep = false,
                                playTime = 20000,
                                timeStep = 1 / 120f
                            ), list = createSnowByType(1)
                        )
                    }

                }) {
                Text(
                    text = "Play",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
