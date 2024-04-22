package com.thewind.box2dmover.effects.module.page.snowdrop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.thewind.box2dmover.effects.main.EffectPageActionButton
import com.thewind.box2dmover.effects.main.EffectPageBackgroundCard
import com.thewind.box2dmover.effects.module.EffectType
import com.thewind.box2dmover.rain2.RainViewV2

@Composable
@Preview
fun SnowDropPage() {
    val context = LocalContext.current
    val rainView = remember {
        RainViewV2(context)
    }

    val scope = rememberCoroutineScope()
    EffectPageBackgroundCard(EffectType.SnowDrop.title)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(factory = { rainView }, modifier = Modifier.fillMaxSize())

    }

    EffectPageActionButton("Play") {

    }
}
