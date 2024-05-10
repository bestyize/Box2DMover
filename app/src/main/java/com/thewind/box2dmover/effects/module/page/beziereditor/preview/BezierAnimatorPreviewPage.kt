package com.thewind.box2dmover.effects.module.page.beziereditor.preview

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thewind.box2dmover.effects.main.EffectPageActionButton
import com.thewind.box2dmover.effects.module.page.beziereditor.vm.BezierStudioViewModel

@Composable
fun BezierAnimatorPreviewPage() {

    val vm = viewModel(
        modelClass = BezierStudioViewModel::class.java,
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )

    val state by vm.pageState.collectAsState()

    val context = LocalContext.current
    val view = remember {
        BezierAnimatorPreviewView(context)
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
            view.play(listOf(state.element))
        }

    }
}