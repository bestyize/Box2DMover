package com.thewind.box2dmover.effects.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
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

@Composable
@Preview
fun EffectPageBackgroundCard(title: String = "雪飘人间") {
    val context = LocalContext.current
    val bgImage = remember {
        ContextCompat.getDrawable(context, R.drawable.duanwu)
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .drawBehind {
            bgImage?.updateBounds(0, 0, size.width.toInt(), size.height.toInt())
            bgImage?.draw(drawContext.canvas.nativeCanvas)
        }
        .padding(10.dp)) {
        Text(
            text = "雪花飘落",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}