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
                MMKV.defaultMMKV().getString("animate_data", bubbleAnimJson),
                BezierEditorPageState::class.java
            )
            bean?.let {
                _editorPageState.value = it.copy(elements = fixDuration(it))
            }
        }


    }

    fun updateBasicElementParam(
        elementIndex: Int,
        element: BezierAnimateElement
    ) {
        val data = _editorPageState.value
        _editorPageState.value = data.copy(elements = data.elements.toMutableList().apply {
            this[elementIndex] = element.copy(
                position = element.position,
                width = element.width,
                height = element.height,
                topImageWidth = element.topImageWidth,
                topImageHeight = element.topImageHeight,
                imageWidth = element.imageWidth,
                imageHeight = element.imageHeight
            )
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
                Gson().toJson(fixDuration(data))
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
                openCopyDialog = false,
                elements = fixDuration(_editorPageState.value)
            )
        )
        MMKV.defaultMMKV().putString("animate_data", data)
        async(Dispatchers.Main) {
            toast("保存成功")
        }
    }


    private fun fixDuration(data: BezierEditorPageState): List<BezierAnimateElement> {
        return data.elements.toMutableList().apply {
            forEachIndexed { index, element ->
                this[index] =
                    element.copy(duration = element.list.maxOfOrNull { it.duration + it.delay }
                        ?: 0)
            }
        }.toList()
    }
}

private const val defaultAnimJson = """
    {
        "previewContainerWidth":-1,
        "previewContainerHeight":-1,
        "elements":[{"duration":3800,"guide_image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/df3287494737f6b7e1d6b90b0750c031.json","guide_image_md5":"2725e6feeeae118ce5e00d0a4458f3a2","guide_show_duration":667,"guide_show_time":500,"image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/ffb157199d8bc2ed4fe20c4b247b6360.png","image_md5":"139f1927b46535958df68ecb67dc9cb5","list":[{"delay":0,"duration":4200,"param":{"control1":{"x":0.5,"y":0.5},"control2":{"x":-0.02,"y":0.6},"end":{"x":0.3,"y":1.1},"start":{"x":0.35,"y":0.2}},"type":1},{"delay":667,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":0.0}},"type":4},{"delay":800,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":-10.0},"start":{"x":0.0,"y":10.0}},"type":4},{"delay":967,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":-10.0}},"type":4},{"delay":1100,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":0.0},"start":{"x":0.0,"y":10.0}},"type":4}],"position":{"x":0.2,"y":0.3},"width":85},{"duration":5000,"guide_image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/df3287494737f6b7e1d6b90b0750c031.json","guide_image_md5":"2725e6feeeae118ce5e00d0a4458f3a2","guide_show_duration":667,"guide_show_time":1500,"image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/a25558c0120d147dc71fc721b41b10ee.png","image_md5":"a7cc2701d94e56bf1ee210534231d423","list":[{"delay":0,"duration":5000,"param":{"control1":{"x":1.0,"y":0.5},"control2":{"x":0.48,"y":0.5},"end":{"x":0.8,"y":0.95},"start":{"x":0.64,"y":0.06}},"type":1},{"delay":1666,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":0.0}},"type":4},{"delay":1800,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":-10.0},"start":{"x":0.0,"y":10.0}},"type":4},{"delay":1967,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":-10.0}},"type":4},{"delay":2100,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":0.0},"start":{"x":0.0,"y":10.0}},"type":4}],"position":{"x":0.64,"y":0.06},"width":85},{"duration":5000,"guide_image_url":"","guide_show_duration":0,"guide_show_time":0,"image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/ffb157199d8bc2ed4fe20c4b247b6360.png","image_md5":"139f1927b46535958df68ecb67dc9cb5","list":[{"delay":0,"duration":5000,"param":{"control1":{"x":0.2,"y":0.26},"control2":{"x":0.08,"y":0.6},"end":{"x":0.33,"y":0.82},"start":{"x":0.3,"y":-0.1}},"type":1},{"delay":2600,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":0.0}},"type":4},{"delay":2733,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":-10.0},"start":{"x":0.0,"y":10.0}},"type":4},{"delay":2900,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":-10.0}},"type":4},{"delay":3033,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":0.0},"start":{"x":0.0,"y":10.0}},"type":4}],"position":{"x":0.2,"y":-0.1},"width":85},{"duration":5000,"guide_image_url":"","guide_show_duration":0,"guide_show_time":0,"image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/a25558c0120d147dc71fc721b41b10ee.png","image_md5":"a7cc2701d94e56bf1ee210534231d423","list":[{"delay":1000,"duration":4000,"param":{"control1":{"x":0.2,"y":0.26},"control2":{"x":0.48,"y":0.4},"end":{"x":0.5,"y":0.66},"start":{"x":0.5,"y":-0.1}},"type":1},{"delay":3600,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":0.0}},"type":4},{"delay":3733,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":-10.0},"start":{"x":0.0,"y":10.0}},"type":4},{"delay":3900,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":-10.0}},"type":4},{"delay":4067,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":0.0},"start":{"x":0.0,"y":10.0}},"type":4}],"position":{"x":0.5,"y":-0.1},"width":85},{"duration":5000,"guide_image_url":"","guide_show_duration":0,"guide_show_time":0,"image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/ffb157199d8bc2ed4fe20c4b247b6360.png","image_md5":"139f1927b46535958df68ecb67dc9cb5","list":[{"delay":1000,"duration":4000,"param":{"control1":{"x":0.72,"y":0.3},"control2":{"x":0.8,"y":0.3},"end":{"x":0.74,"y":0.54},"start":{"x":0.88,"y":-0.1}},"type":1}],"position":{"x":0.88,"y":-0.1},"width":85},{"duration":5000,"guide_image_url":"","guide_show_duration":0,"guide_show_time":0,"image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/a25558c0120d147dc71fc721b41b10ee.png","image_md5":"a7cc2701d94e56bf1ee210534231d423","list":[{"delay":2100,"duration":2900,"param":{"control1":{"x":0.48,"y":-0.02},"control2":{"x":0.64,"y":0.15},"end":{"x":0.45,"y":0.36},"start":{"x":0.4,"y":-0.1}},"type":1}],"position":{"x":0.4,"y":-0.1},"width":85}]
    }
"""

private const val bubbleAnimJson = """
{"previewContainerWidth":-1,"previewContainerHeight":-1,"elements":[{"type":1,"duration":3800,"guide_image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/df3287494737f6b7e1d6b90b0750c031.json","guide_image_md5":"2725e6feeeae118ce5e00d0a4458f3a2","guide_show_duration":667,"guide_show_time":500,"image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/ffb157199d8bc2ed4fe20c4b247b6360.png","image_md5":"139f1927b46535958df68ecb67dc9cb5","list":[{"delay":0,"duration":4200,"param":{"control1":{"x":0.5,"y":0.5},"control2":{"x":-0.02,"y":0.6},"end":{"x":0.3,"y":1.1},"start":{"x":0.35,"y":0.2}},"type":1},{"delay":667,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":0.0}},"type":4},{"delay":800,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":-10.0},"start":{"x":0.0,"y":10.0}},"type":4},{"delay":967,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":-10.0}},"type":4},{"delay":1100,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":0.0},"start":{"x":0.0,"y":10.0}},"type":4}],"position":{"x":0.2,"y":0.3},"width":100,"height":225,"top_image_width":100,"top_image_height":100,"image_width":80,"image_height":80},{"type":1,"duration":5000,"guide_image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/df3287494737f6b7e1d6b90b0750c031.json","guide_image_md5":"2725e6feeeae118ce5e00d0a4458f3a2","guide_show_duration":667,"guide_show_time":1500,"image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/a25558c0120d147dc71fc721b41b10ee.png","image_md5":"a7cc2701d94e56bf1ee210534231d423","list":[{"delay":0,"duration":5000,"param":{"control1":{"x":1.0,"y":0.5},"control2":{"x":0.48,"y":0.5},"end":{"x":0.8,"y":0.95},"start":{"x":0.64,"y":0.06}},"type":1},{"delay":1666,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":0.0}},"type":4},{"delay":1800,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":-10.0},"start":{"x":0.0,"y":10.0}},"type":4},{"delay":1967,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":-10.0}},"type":4},{"delay":2100,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":0.0},"start":{"x":0.0,"y":10.0}},"type":4}],"position":{"x":0.64,"y":0.06},"width":100,"height":225,"top_image_width":100,"top_image_height":100,"image_width":80,"image_height":80},{"type":1,"duration":5000,"guide_image_url":"","guide_show_duration":0,"guide_show_time":0,"image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/ffb157199d8bc2ed4fe20c4b247b6360.png","image_md5":"139f1927b46535958df68ecb67dc9cb5","list":[{"delay":0,"duration":5000,"param":{"control1":{"x":0.2,"y":0.26},"control2":{"x":0.08,"y":0.6},"end":{"x":0.33,"y":0.82},"start":{"x":0.3,"y":-0.1}},"type":1},{"delay":2600,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":0.0}},"type":4},{"delay":2733,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":-10.0},"start":{"x":0.0,"y":10.0}},"type":4},{"delay":2900,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":-10.0}},"type":4},{"delay":3033,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":0.0},"start":{"x":0.0,"y":10.0}},"type":4}],"position":{"x":0.2,"y":-0.1},"width":100,"height":225,"top_image_width":100,"top_image_height":100,"image_width":80,"image_height":80},{"type":1,"duration":5000,"guide_image_url":"","guide_show_duration":0,"guide_show_time":0,"image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/a25558c0120d147dc71fc721b41b10ee.png","image_md5":"a7cc2701d94e56bf1ee210534231d423","list":[{"delay":1000,"duration":4000,"param":{"control1":{"x":0.2,"y":0.26},"control2":{"x":0.48,"y":0.4},"end":{"x":0.5,"y":0.66},"start":{"x":0.5,"y":-0.1}},"type":1},{"delay":3600,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":0.0}},"type":4},{"delay":3733,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":-10.0},"start":{"x":0.0,"y":10.0}},"type":4},{"delay":3900,"duration":167,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":10.0},"start":{"x":0.0,"y":-10.0}},"type":4},{"delay":4067,"duration":133,"param":{"control1":{"x":0.42,"y":0.0},"control2":{"x":0.58,"y":1.0},"end":{"x":0.0,"y":0.0},"start":{"x":0.0,"y":10.0}},"type":4}],"position":{"x":0.5,"y":-0.1},"width":100,"height":225,"top_image_width":100,"top_image_height":100,"image_width":80,"image_height":80},{"type":1,"duration":5000,"guide_image_url":"","guide_show_duration":0,"guide_show_time":0,"image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/ffb157199d8bc2ed4fe20c4b247b6360.png","image_md5":"139f1927b46535958df68ecb67dc9cb5","list":[{"delay":1000,"duration":4000,"param":{"control1":{"x":0.72,"y":0.3},"control2":{"x":0.8,"y":0.3},"end":{"x":0.74,"y":0.54},"start":{"x":0.88,"y":-0.1}},"type":1}],"position":{"x":0.88,"y":-0.1},"width":100,"height":225,"top_image_width":100,"top_image_height":100,"image_width":80,"image_height":80},{"type":1,"duration":5000,"guide_image_url":"","guide_show_duration":0,"guide_show_time":0,"image_url":"https://i0.hdslb.com/bfs/sycp/creative_img/202405/a25558c0120d147dc71fc721b41b10ee.png","image_md5":"a7cc2701d94e56bf1ee210534231d423","list":[{"delay":2100,"duration":2900,"param":{"control1":{"x":0.48,"y":-0.02},"control2":{"x":0.64,"y":0.15},"end":{"x":0.45,"y":0.36},"start":{"x":0.4,"y":-0.1}},"type":1},{"delay":0,"duration":0,"param":{"control1":{"x":0.0,"y":0.7},"control2":{"x":0.0,"y":0.7},"end":{"x":0.0,"y":0.7},"start":{"x":0.0,"y":0.7}},"type":3}],"position":{"x":0.4,"y":-0.1},"width":100,"height":225,"top_image_width":100,"top_image_height":100,"image_width":80,"image_height":80}]}
"""