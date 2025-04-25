package com.dahlaran.newmovshow.common.presentation

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color


/**
 * Draws a horizontal divider line at the bottom of the component.
 */
fun Modifier.drawItemDividerHorizontal(color: Color = Color.DarkGray): Modifier = composed {
    drawWithContent {
        drawContent()
        if (this.size.height > 0) {
            drawLine(
                color = color,
                start = Offset(0f, size.height - 1f),
                end = Offset(size.width, size.height - 1f),
                strokeWidth = 1f
            )
        }
    }
}