package com.thewind.box2dmover.effects.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thewind.box2dmover.ui.theme.BiliPink

@Composable
@Preview
fun EffectPageActionButton(
    modifier: Modifier = Modifier,
    title: String = "Action",
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(bottom = 60.dp)
            .height(40.dp)
            .width(200.dp)
            .background(color = BiliPink, shape = RoundedCornerShape(100.dp))
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = null, onClick = onClick), contentAlignment = Alignment.Center
    ) {
        Text(text = title, color = Color.White, fontSize = 18.sp)
    }
}