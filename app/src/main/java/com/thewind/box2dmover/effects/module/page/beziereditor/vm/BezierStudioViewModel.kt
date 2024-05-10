package com.thewind.box2dmover.effects.module.page.beziereditor.vm

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.thewind.box2dmover.app.toast
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateItem
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierEditorPageState
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class BezierStudioViewModel : ViewModel() {
    private val _editorPageState: MutableStateFlow<BezierEditorPageState> = MutableStateFlow(
        BezierEditorPageState()
    )

    val pageState = _editorPageState.asStateFlow()

    fun updateBasicElementParam(position: BezierPoint, width: Int) {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(
            element = data.element.copy(
                position = position, width = width
            )
        )
    }

    fun updateAnimateItem(position: Int, animateItem: BezierAnimateItem) {
        val data = _editorPageState.value
        _editorPageState.value =
            data.copy(element = data.element.copy(list = data.element.list.toMutableList().apply {
                this[position] = animateItem
            }))
    }


    fun addAnimateItem() {
        val data = _editorPageState.value
        _editorPageState.value =
            data.copy(element = data.element.copy(list = data.element.list.toMutableList().apply {
                add(BezierAnimateItem())
            }))
        toast("添加成功")
    }

    fun removeAnimateItem(position: Int) {
        val data = _editorPageState.value
        _editorPageState.value =
            data.copy(element = data.element.copy(list = data.element.list.toMutableList().apply {
                if (position in 0..<size) {
                    removeAt(position)
                }
            }))
        toast("删除成功")
    }

    suspend fun openCopyDialog() {
        val data = _editorPageState.value
        _editorPageState.value =
            data.copy(openCopyDialog = true, animatorJson = withContext(Dispatchers.IO) {
                Gson().toJson(data.element)
            })
    }


    fun closeCopyDialog() {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(openCopyDialog = false, animatorJson = "")
    }


    fun openOptionDialog() {
        val data = _editorPageState.value
        _editorPageState.value =
            data.copy(openOptionDialog = true)
    }


    fun closeOptionDialog() {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(openOptionDialog = false)
    }
}