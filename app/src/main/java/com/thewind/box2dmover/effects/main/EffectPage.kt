package com.thewind.box2dmover.effects.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.updateBounds
import com.thewind.box2dmover.R
import com.thewind.box2dmover.app.toast
import com.thewind.box2dmover.effects.module.EffectType
import com.thewind.box2dmover.ui.theme.BiliPink
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun EffectPage(switchPage: (String) -> Unit = {}) {
    val context = LocalContext.current
    val bgImage = remember {
        ContextCompat.getDrawable(context, R.drawable.duanwu)
    }
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind {
            bgImage?.updateBounds(0, 0, size.width.toInt(), size.height.toInt())
            bgImage?.draw(drawContext.canvas.nativeCanvas)
        }
        .padding(10.dp)) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            EffectType.entries.forEach {
                EffectOptionView(it.title) {
                    scope.launch {
                        switchPage.invoke(it.router)
                    }
                }
            }
        }

    }
}

@Composable
@Preview
private fun EffectOptionView(
    title: String = "雪花降落",
    support: Boolean = true,
    onClick: () -> Unit = {}
) {

    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .shadow(5.dp)
            .background(color = BiliPink, shape = RoundedCornerShape(12.dp))
            .alpha(if (support) 1f else 0.5f)
            .padding(vertical = 15.dp, horizontal = 20.dp)
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = null, onClick = {
                scope.launch {
                    if (support) {
                        onClick.invoke()
                    } else {
                        toast("Not Support")
                    }
                }

            })
    ) {
        Text(
            color = Color.White,
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}