package com.thewind.box2dmover.effects.module.page.beziereditor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thewind.box2dmover.app.toast
import com.thewind.box2dmover.ui.theme.Bg1
import com.thewind.box2dmover.ui.theme.Ga1
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
internal fun AnimatorShareDialog(
    title: String = "动画参数", content: String = "", onClose: () -> Unit = {}
) {

    val cm = LocalClipboardManager.current

    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            onClose.invoke()
        },
        properties = ModalBottomSheetDefaults.properties(),
        windowInsets = WindowInsets(0.dp),
        containerColor = Ga1,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Ga1),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                )
                Text(text = title)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                )

                SelectionContainer {
                    Text(
                        text = content, modifier = Modifier.defaultMinSize(minHeight = 60.dp)
                    )
                }
                Text(
                    text = "复制",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(color = Bg1)
                        .padding(vertical = 20.dp)
                        .clickable {
                            scope.launch {
                                cm.setText(AnnotatedString(content))
                                onClose.invoke()
                                toast("复制成功!")
                            }

                        },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

        }

    }
}