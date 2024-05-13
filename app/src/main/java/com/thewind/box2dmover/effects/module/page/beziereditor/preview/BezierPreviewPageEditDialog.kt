package com.thewind.box2dmover.effects.module.page.beziereditor.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.thewind.box2dmover.ui.theme.Bg1
import com.thewind.box2dmover.ui.theme.BiliPink
import com.thewind.box2dmover.ui.theme.Ga1
import com.thewind.box2dmover.ui.theme.Ga2

@Composable
@Preview
fun BezierPreviewPageEditDialog(
    containerWidth: Int = -1,
    containerHeight: Int = -1,
    onChange: (width: Int, height: Int) -> Unit = { _, _ -> },
    onClose: () -> Unit = {}
) {
    Dialog(onDismissRequest = { onClose.invoke() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Ga1, shape = RoundedCornerShape(12.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(text = "修改容器大小", modifier = Modifier.align(Alignment.CenterHorizontally))
            SingleParamInputCard(title = "容器宽度", value = containerWidth.toLong()) { w ->
                if (w > 0) {
                    onChange.invoke(w.toInt(), containerHeight)
                } else {
                    onChange.invoke(-1, -1)
                }

            }
            SingleParamInputCard(title = "容器高度", value = containerHeight.toLong()) { h ->
                if (h > 0) {
                    onChange.invoke(containerWidth, h.toInt())
                } else {
                    onChange.invoke(-1, -1)
                }
            }

            Text(
                text = "关闭",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Bg1, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .clickable {
                        onClose.invoke()
                    },
                textAlign = TextAlign.Center
            )
        }
    }

}


@Composable
@Preview
private fun SingleParamInputCard(
    modifier: Modifier = Modifier,
    title: String = "容器宽度",
    value: Long = 0L,
    onChange: (param: Long) -> Unit = {}
) {
    var v by remember {
        mutableStateOf("$value")
    }
    Row(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title, modifier = Modifier.wrapContentWidth(), color = BiliPink)
        Spacer(modifier = Modifier.width(4.dp))
        BasicTextField(value = v,
            onValueChange = {
                v = it
                it.toLongOrNull()?.let { validV ->
                    onChange.invoke(validV)
                }
            },
            modifier = Modifier
                .background(Ga2, shape = RoundedCornerShape(3.dp))
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .width(40.dp)
                .onFocusChanged {
                    v = "$value"
                })
    }
}