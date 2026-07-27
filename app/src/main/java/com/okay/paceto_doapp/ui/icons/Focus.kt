package com.okay.test

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
val focus: ImageVector
  get() {
    if (_lightbulb_2 != null) {
      return _lightbulb_2!!
    }
    _lightbulb_2 =
      ImageVector.Builder(
          name = "lightbulb_2",
          defaultWidth = 24.dp,
          defaultHeight = 24.dp,
          viewportWidth = 24f,
          viewportHeight = 24f,
        )
        .apply {
          path(
            fill = SolidColor(Color.Black),
            fillAlpha = 1f,
            stroke = null,
            strokeAlpha = 1f,
            strokeLineWidth = 1f,
            strokeLineCap = StrokeCap.Butt,
            strokeLineJoin = StrokeJoin.Bevel,
            strokeLineMiter = 1f,
            pathFillType = PathFillType.Companion.NonZero,
          ) {
            moveTo(10f, 18f)
            quadTo(9.18f, 18f, 8.59f, 17.41f)
            reflectiveQuadTo(8f, 16f)
            verticalLineTo(14.75f)
            quadTo(6.58f, 13.77f, 5.79f, 12.25f)
            reflectiveQuadTo(5f, 9f)
            quadTo(5f, 6.07f, 7.04f, 4.04f)
            reflectiveQuadTo(12f, 2f)
            reflectiveQuadToRelative(4.96f, 2.04f)
            reflectiveQuadTo(19f, 9f)
            quadToRelative(0f, 1.72f, -0.79f, 3.24f)
            reflectiveQuadTo(16f, 14.75f)
            verticalLineTo(16f)
            quadToRelative(0f, 0.82f, -0.59f, 1.41f)
            reflectiveQuadTo(14f, 18f)
            horizontalLineTo(10f)
            close()
            moveToRelative(0f, -2f)
            horizontalLineToRelative(4f)
            verticalLineTo(13.7f)
            lineToRelative(0.85f, -0.6f)
            quadToRelative(1.02f, -0.7f, 1.59f, -1.79f)
            reflectiveQuadTo(17f, 9f)
            quadTo(17f, 6.93f, 15.54f, 5.46f)
            reflectiveQuadTo(12f, 4f)
            quadTo(9.93f, 4f, 8.46f, 5.46f)
            quadTo(7f, 6.93f, 7f, 9f)
            quadToRelative(0f, 1.22f, 0.56f, 2.31f)
            reflectiveQuadTo(9.15f, 13.1f)
            lineTo(10f, 13.7f)
            verticalLineTo(16f)
            close()
            moveToRelative(0f, 6f)
            quadTo(9.58f, 22f, 9.29f, 21.71f)
            quadTo(9f, 21.43f, 9f, 21f)
            verticalLineTo(20f)
            horizontalLineToRelative(6f)
            verticalLineToRelative(1f)
            quadToRelative(0f, 0.43f, -0.29f, 0.71f)
            reflectiveQuadTo(14f, 22f)
            horizontalLineTo(10f)
            close()
            moveTo(12f, 9f)
            close()
          }
        }
        .build()
    return _lightbulb_2!!
  }

private var _lightbulb_2: ImageVector? = null
