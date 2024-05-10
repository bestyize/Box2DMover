package com.thewind.box2dmover.effects.module.page.beziereditor

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thewind.box2dmover.effects.module.EffectType
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateElement
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateItem
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierEditorPageAction
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierPoint
import com.thewind.box2dmover.effects.module.page.beziereditor.vm.BezierStudioViewModel
import com.thewind.box2dmover.effects.router.LocalEffectNavigation
import com.thewind.box2dmover.ui.theme.Bg1
import com.thewind.box2dmover.ui.theme.Bg2
import com.thewind.box2dmover.ui.theme.BiliPink
import com.thewind.box2dmover.ui.theme.Ga1
import com.thewind.box2dmover.ui.theme.Ga2
import com.thewind.box2dmover.ui.theme.Text3
import com.thewind.box2dmover.widget.TitleHeader
import kotlinx.coroutines.launch

@Composable
@Preview
fun BezierParamEditorPage() {
    val vm = viewModel(
        modelClass = BezierStudioViewModel::class.java,
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
    val pageState by vm.pageState.collectAsState()
    val scope = rememberCoroutineScope()

    val navController = LocalEffectNavigation.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(
                    color = Ga1, shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                )
                .fillMaxWidth()
                .fillMaxHeight()

        ) {
            TitleHeader("元素动画编辑器", color = Color.White, backgroundColor = BiliPink)

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Spacer(modifier = Modifier.height(2.dp))
                    ElementBaseParamCard(element = pageState.element) {
                        scope.launch {
                            vm.updateBasicElementParam(position = it.position, width = it.width)
                        }
                    }
                }
                items(count = pageState.element.list.size) { index ->
                    val animateItem = pageState.element.list[index]
                    WholeAnimatorItemCard(title = "动画$index",
                        animateItem = animateItem,
                        onChange = {
                            scope.launch {
                                vm.updateAnimateItem(index, it)
                            }
                        },
                        onRemove = {
                            scope.launch {
                                vm.removeAnimateItem(index)
                            }
                        })
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }

            Spacer(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Bg2)
            )
        }

        Card(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomEnd)
                .clickable {
                    scope.launch {
                        vm.openOptionDialog()
                    }
                },
            colors = CardDefaults.cardColors(containerColor = BiliPink),
            shape = RoundedCornerShape(100.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.padding(10.dp)
            )
        }

    }

    if (pageState.openCopyDialog) {
        AnimatorShareDialog(content = pageState.animatorJson) {
            vm.closeCopyDialog()
        }
    }

    if (pageState.openOptionDialog) {
        BezierEditorOptionDialog(onAction = {
            scope.launch {
                when (it) {
                    BezierEditorPageAction.Add -> vm.addAnimateItem()
                    BezierEditorPageAction.Preview -> navController.navigate(EffectType.BezierPreview.router)
                    BezierEditorPageAction.Export -> vm.openCopyDialog()
                }
                vm.closeOptionDialog()
            }

        }, onClose = {
            vm.closeOptionDialog()
        })
    }

}


@Composable
@Preview
private fun WholeAnimatorItemCard(
    title: String = "运动动画1",
    animateItem: BezierAnimateItem = BezierAnimateItem(),
    onChange: (BezierAnimateItem) -> Unit = {},
    onRemove: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Bg1),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(15.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Bg2)
            )
            SingleParamInputCard(title = "动画类型", value = animateItem.type.toLong()) {
                onChange.invoke(animateItem.copy(type = it.toInt()))
            }
            SingleParamInputCard(title = "起始时间", value = animateItem.delay) {
                onChange.invoke(animateItem.copy(delay = it))
            }
            SingleParamInputCard(
                title = "动画时长", value = animateItem.duration
            ) {
                onChange.invoke(animateItem.copy(duration = it))
            }
            BezierItemEditorCard(title = "起      点", point = animateItem.param.start) {
                onChange.invoke(animateItem.copy(param = animateItem.param.copy(start = it)))
            }
            BezierItemEditorCard(title = "终      点", point = animateItem.param.end) {
                onChange.invoke(animateItem.copy(param = animateItem.param.copy(end = it)))
            }
            BezierItemEditorCard(title = "控制点一", point = animateItem.param.control1) {
                onChange.invoke(animateItem.copy(param = animateItem.param.copy(control1 = it)))
            }
            BezierItemEditorCard(title = "控制点二", point = animateItem.param.control2) {
                onChange.invoke(animateItem.copy(param = animateItem.param.copy(control2 = it)))
            }
            Text(
                text = "删除",
                fontSize = 12.sp,
                modifier = Modifier
                    .background(color = BiliPink, shape = RoundedCornerShape(6.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                    .align(
                        Alignment.End
                    )
                    .clickable(interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = null, onClick = {
                        onRemove.invoke()
                    }),
                color = Color.White
            )
        }


    }


}

@Composable
@Preview
private fun BezierItemEditorCard(
    title: String = "起点1",
    point: BezierPoint = BezierPoint(),
    onChange: (BezierPoint) -> Unit = {}
) {

    var x by remember {
        mutableStateOf("${point.x}")
    }

    var y by remember {
        mutableStateOf("${point.y}")
    }

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title, modifier = Modifier.wrapContentWidth(), color = BiliPink)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "x ", color = Text3)
        Spacer(modifier = Modifier.width(4.dp))
        BasicTextField(value = x,
            onValueChange = {
                x = it
                it.toFloatOrNull()?.let { validX ->
                    onChange.invoke(point.copy(x = validX))
                }

            },
            modifier = Modifier
                .background(Ga2, shape = RoundedCornerShape(3.dp))
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .width(40.dp)
                .onFocusChanged {
                    x = "${point.x}"
                })
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "y ", color = Text3)
        Spacer(modifier = Modifier.width(4.dp))
        BasicTextField(value = y,
            onValueChange = {
                y = it
                it.toFloatOrNull()?.let { validY ->
                    onChange.invoke(point.copy(y = validY))
                }
            },
            modifier = Modifier
                .background(Ga2, shape = RoundedCornerShape(3.dp))
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .width(40.dp)
                .onFocusChanged {
                    y = "${point.y}"
                })
    }
}

@Composable
@Preview
private fun SingleParamInputCard(
    modifier: Modifier = Modifier,
    title: String = "起始时间(ms)",
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


@Composable
@Preview
private fun ElementBaseParamCard(
    element: BezierAnimateElement = BezierAnimateElement(),
    onChange: (element: BezierAnimateElement) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Bg1)
            .padding(15.dp)
    ) {
        Text(
            text = "元素参数",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Bg2)
        )
        SingleParamInputCard(title = "初始大小", value = element.width.toLong()) {
            onChange.invoke(element.copy(width = it.toInt()))
        }
        BezierItemEditorCard(title = "初始坐标", point = element.position) {
            onChange.invoke(element.copy(position = it))
        }
    }

}