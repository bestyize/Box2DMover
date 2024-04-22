package com.thewind.box2dmover.effects.module.page.balloonfly

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thewind.box2dmover.effects.main.EffectPageBackgroundCard
import com.thewind.box2dmover.effects.module.EffectType

@Composable
@Preview
fun BalloonFlyPage() {
    EffectPageBackgroundCard(EffectType.BalloonFly.title)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

    }
}