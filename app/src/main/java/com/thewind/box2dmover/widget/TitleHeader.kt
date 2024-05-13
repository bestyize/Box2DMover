package com.thewind.box2dmover.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thewind.box2dmover.ui.theme.Bg1
import com.thewind.box2dmover.ui.theme.Text1

@Composable
@Preview
fun TitleHeader(
    title: String = "标题", color: Color = Text1, backgroundColor: Color = Bg1
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = backgroundColor)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = color,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 6.dp, top = 30.dp)
                .wrapContentSize()
                .align(Alignment.Center)
        )
    }
}