package com.thewind.box2dmover.effects.module.page.beziereditor.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class BezierAnimateElement(
    @SerializedName("guide_image_url") val guideImageUrl: String? = null,
    @SerializedName("guide_image_hash") val guideImageHash: String? = null,
    @SerializedName("guide_show_time") val guideShowTime: Long = 0L,
    @SerializedName("guide_show_duration") val guideShowDuration: Long = 0L,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("image_hash") val imageHash: String? = null,
    @SerializedName("duration") val duration: Long = 0L,
    @SerializedName("width") val width: Int = 30,
    @SerializedName("position") val position: BezierPoint = BezierPoint(x = 0.5f, y = 0.5f),
    @SerializedName("list") val list: List<BezierAnimateItem> = emptyList()
)
