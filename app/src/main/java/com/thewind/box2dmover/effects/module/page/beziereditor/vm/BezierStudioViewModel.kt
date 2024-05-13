package com.thewind.box2dmover.effects.module.page.beziereditor.vm

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.tencent.mmkv.MMKV
import com.thewind.box2dmover.app.toast
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateElement
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierAnimateItem
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierEditorPageState
import com.thewind.box2dmover.effects.module.page.beziereditor.model.BezierPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class BezierStudioViewModel : ViewModel() {
    private val _editorPageState: MutableStateFlow<BezierEditorPageState> = MutableStateFlow(
        BezierEditorPageState()
    )

    val pageState = _editorPageState.asStateFlow()

    init {
        runCatching {
            val bean: BezierEditorPageState? = Gson().fromJson(
                MMKV.defaultMMKV().getString("animate_data", ""), BezierEditorPageState::class.java
            )
            bean?.let {
                _editorPageState.value = it
            }
        }


    }

    fun updateBasicElementParam(elementIndex: Int, position: BezierPoint, width: Int) {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(elements = data.elements.toMutableList().apply {
            val element = get(elementIndex)
            this[elementIndex] = element.copy(position = position, width = width)
        })
    }

    fun updateAnimateItem(elementIndex: Int, position: Int, animateItem: BezierAnimateItem) {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(elements = data.elements.toMutableList().apply {
            val element = get(elementIndex)
            this[elementIndex] = element.copy(list = element.list.toMutableList().apply {
                this[position] = animateItem
            })
        })
    }


    fun addAnimateItem(elementIndex: Int) {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(elements = data.elements.toMutableList().apply {
            val element = this[elementIndex]
            this[elementIndex] = element.copy(list = element.list.toMutableList().apply {
                add(BezierAnimateItem())
            })
        })
        toast("添加成功")
    }

    fun removeAnimateItem(elementIndex: Int, position: Int) {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(elements = data.elements.toMutableList().apply {
            val element = this[elementIndex]
            this[elementIndex] = element.copy(list = element.list.toMutableList().apply {
                removeAt(position)
            })
        })
        toast("删除成功")
    }

    fun addElement() {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(elements = data.elements.toMutableList().apply {
            add(BezierAnimateElement())
        })
    }

    fun removeElement(elementIndex: Int) {
        val data = _editorPageState.value
        if (data.elements.size == 1) {
            toast("至少需要保留一个元素")
            return
        }
        _editorPageState.value = data.copy(elements = data.elements.toMutableList().apply {
            removeAt(elementIndex)
        })
    }

    suspend fun openCopyDialog() {
        val data = _editorPageState.value
        _editorPageState.value =
            data.copy(openCopyDialog = true, animatorJson = withContext(Dispatchers.IO) {
                Gson().toJson(data.elements)
            })
    }


    fun closeCopyDialog() {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(openCopyDialog = false, animatorJson = "")
    }


    fun openOptionDialog() {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(openOptionDialog = true)
    }


    fun closeOptionDialog() {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(openOptionDialog = false)
    }

    fun updateContainerSize(width: Int, height: Int) {
        val data = _editorPageState.value
        _editorPageState.value =
            data.copy(previewContainerWidth = width, previewContainerHeight = height)
    }


    fun showContainerAdjustDialog() {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(openContainerEditorPage = true)
    }

    fun closeContainerAdjustDialog() {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(openContainerEditorPage = false)
    }


    suspend fun saveAnimation() = withContext(Dispatchers.IO) {
        val data = Gson().toJson(
            _editorPageState.value.copy(
                openOptionDialog = false,
                openContainerEditorPage = false,
                openCopyDialog = false
            )
        )
        MMKV.defaultMMKV().putString("animate_data", data)
        async(Dispatchers.Main) {
            toast("保存成功")
        }
    }
}