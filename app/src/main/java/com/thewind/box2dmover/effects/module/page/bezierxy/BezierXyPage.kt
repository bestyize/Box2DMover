package com.thewind.box2dmover.effects.module.page.bezierxy


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.thewind.box2dmover.ui.theme.BiliPink

@Composable
@Preview
fun BezierXyPage() {

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        val axiosPath = Path()
        axiosPath.moveTo(0f, size.height / 2)
        axiosPath.lineTo(size.width, size.height / 2)
        axiosPath.moveTo(size.width / 2, 0f)
        axiosPath.lineTo(size.width / 2, size.height)
        drawPath(path = axiosPath, color = BiliPink, style = Stroke(width = 5f))

        val meshPath = Path()

        var currentColumn: Float = 0f
        var lineHeight = 200f
        while (currentColumn < size.height) {
            meshPath.moveTo(0f, currentColumn)
            meshPath.lineTo(size.width, currentColumn)
            currentColumn += lineHeight
        }

        var currentRow: Float = 0f

        while (currentRow < size.width) {
            currentRow += lineHeight
            meshPath.moveTo(currentRow, 0f)
            meshPath.lineTo(currentRow, size.height)
        }
        drawPath(path = meshPath, color = Color.Gray, style = Stroke(width = 3f))


        var smallMeshPath = Path()

        currentColumn = 0f
        currentRow = 0f
        lineHeight /= 5

        while (currentColumn < size.height) {
            smallMeshPath.moveTo(0f, currentColumn)
            smallMeshPath.lineTo(size.width, currentColumn)
            currentColumn += lineHeight
        }

        while (currentRow < size.width) {
            currentRow += lineHeight
            smallMeshPath.moveTo(currentRow, 0f)
            smallMeshPath.lineTo(currentRow, size.height)
        }

        drawPath(path = smallMeshPath, color = Color.Gray, style = Stroke(width = 1f))


        drawCircle(color = BiliPink, radius = 10f)


    }
}

