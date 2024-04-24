package com.thewind.box2dmover.effects.module.page.beziermove

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.thewind.box2dmover.effects.main.EffectPageActionButton
import com.thewind.box2dmover.effects.main.EffectPageBackgroundCard
import com.thewind.box2dmover.effects.module.EffectType

@Composable
@Preview
fun BezierMovePage() {
    EffectPageBackgroundCard(EffectType.BezierPath.title)
    val context = LocalContext.current
    val view = remember {
        BezierView2(context)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        AndroidView(factory = {
            view
        }, modifier = Modifier.fillMaxSize())

        EffectPageActionButton(modifier = Modifier.align(Alignment.BottomCenter), "Play") {
            view.play()
        }

    }


}