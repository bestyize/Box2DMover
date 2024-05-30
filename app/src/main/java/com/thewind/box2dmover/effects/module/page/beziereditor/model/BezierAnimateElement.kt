package com.thewind.box2dmover.effects.module.page.beziereditor.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class BezierAnimateElement(
    @SerializedName("type") val type: Int = 0,
    @SerializedName("top_image_url") val topImageUrl: String? = null,
    @SerializedName("top_image_md5") val topImageMd5: String? = null,
    @SerializedName("line_color") val lineColor: String? = "#FFFFFFFF",
    @SerializedName("top_image_width") val topImageWidth: Int = 0,
    @SerializedName("top_image_height") val topImageHeight: Int = 0,
    @SerializedName("image_width") val imageWidth: Int = 0,
    @SerializedName("image_height") val imageHeight: Int = 0,
    @SerializedName("guide_image_url") val guideImageUrl: String? = null,
    @SerializedName("guide_image_md5") val guideImageMd5: String? = null,
    @SerializedName("guide_show_time") val guideShowTime: Long = 0L,
    @SerializedName("guide_show_duration") val guideShowDuration: Long = 0L,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("image_md5") val imageMd5: String? = null,
    @SerializedName("duration") val duration: Long = 0L,
    @SerializedName("width") val width: Int = 30,
    @SerializedName("height") val height: Int = 30,
    @SerializedName("position") val position: BezierPoint = BezierPoint(x = 0.5f, y = 0.5f),
    @SerializedName("list") val list: List<BezierAnimateItem> = emptyList()
)
