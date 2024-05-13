package com.thewind.box2dmover.effects.module.page.beziereditor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimationType
import com.thewind.box2dmover.ui.theme.ThemeColor4

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BezierAnimationTypeSelectDialog(
    onAction: (action: BezierAnimationType) -> Unit = {}, onClose: () -> Unit = {}
) {
    val actions = remember {
        listOf(
            BezierAnimationType.NONE,
            BezierAnimationType.MOVE,
            BezierAnimationType.ALPHA,
            BezierAnimationType.SIZE,
            BezierAnimationType.ANGLE,
        )
    }

    ModalBottomSheet(
        onDismissRequest = { onClose.invoke() },
        properties = ModalBottomSheetDefaults.properties(),
        windowInsets = WindowInsets(0.dp),
        containerColor = ThemeColor4,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = ThemeColor4,
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            actions.forEach { action ->
                OptionCard(title = action.title) {
                    onAction.invoke(action)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

        }
    }

}

@Composable
private fun OptionCard(title: String = "", onClick: () -> Unit = {}) {
    Text(text = title,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = ThemeColor4,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xCFFFFFFF), shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = null) {
                onClick.invoke()
            })
}