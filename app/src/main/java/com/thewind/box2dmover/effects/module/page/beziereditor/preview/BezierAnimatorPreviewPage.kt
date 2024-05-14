package com.thewind.box2dmover.effects.module.page.beziereditor.preview

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thewind.box2dmover.effects.main.EffectPageActionButton
import com.thewind.box2dmover.effects.module.page.beziereditor.vm.BezierStudioViewModel
import com.thewind.box2dmover.ui.theme.ThemeColor4Half

@Composable
fun BezierAnimatorPreviewPage() {

    val vm = viewModel(
        modelClass = BezierStudioViewModel::class.java,
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )

    val state by vm.pageState.collectAsState()

    val context = LocalContext.current
    val view = remember(state.previewContainerWidth + state.previewContainerHeight) {
        BezierAnimatorPreviewView(context)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        BezierPathViewerLayer(
            modifier = Modifier.align(Alignment.Center),
            elements = state.elements,
            containerWidth = state.previewContainerWidth,
            containerHeight = state.previewContainerHeight
        )
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            if (state.previewContainerWidth != -1 && state.previewContainerHeight != -1) {
                AndroidView(
                    factory = {
                        view
                    }, modifier = Modifier.size(
                        state.previewContainerWidth.dp, state.previewContainerHeight.dp
                    )
                )
            } else {
                AndroidView(factory = {
                    view
                }, modifier = Modifier.fillMaxSize())
            }
        }
        EffectPageActionButton(modifier = Modifier.align(Alignment.BottomCenter), "播放动画") {
            view.play()
        }

        Text(
            text = remember {
                "画布大小:${state.previewContainerWidth}x${state.previewContainerHeight}"
            },
            fontSize = 10.sp,
            color = Color.White,
            modifier = Modifier
                .padding(25.dp)
                .align(Alignment.TopEnd)
                .background(ThemeColor4Half, shape = RoundedCornerShape(3.dp))
                .padding(horizontal = 4.dp, vertical = 2.dp)
        )
    }

    LaunchedEffect(state.previewContainerWidth + state.previewContainerHeight) {
        view.initAnimation(state.elements)
    }


}