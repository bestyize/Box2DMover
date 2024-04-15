package com.thewind.box2dmover

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.thewind.box2dmover.mov.model.WorldParam
import com.thewind.box2dmover.rain.RainTextureView
import com.thewind.box2dmover.rain.createRain
import com.thewind.box2dmover.ui.theme.BiliPink
import com.thewind.box2dmover.ui.theme.Box2DMoverTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box2DMoverTheme {

                val rainView = remember {
                    RainTextureView(this)
                }

                val scope = rememberCoroutineScope()

                Box(modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                    }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                    AndroidView(factory = { rainView }, modifier = Modifier.fillMaxSize())
                    Box(
                        modifier = Modifier
                            .padding(bottom = 30.dp)
                            .height(60.dp)
                            .width(250.dp)
                            .background(color = BiliPink, shape = RoundedCornerShape(100.dp))
                            .align(Alignment.BottomCenter)
                            .clickable {
                                scope.launch {
                                    rainView.play(
                                        param = WorldParam(
                                            xGravity = 0f,
                                            yGravity = 20f,
                                            doSleep = true,
                                            playTime = 20000
                                        ),
                                        list = createRain()
                                    )
                                    //startActivity(Intent(this@MainActivity, SplashActivity::class.java))
                                }

                            }
                    ) {
                        Text(
                            text = "Play",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }

    }
}
