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
val all_tasks: ImageVector
  get() {
    if (_all_tasks != null) {
      return _all_tasks!!
    }
    _all_tasks =
      ImageVector.Builder(
          name = "all_tasks",
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
            moveTo(5f, 21f)
            lineTo(3f, 9f)
            horizontalLineTo(21f)
            lineTo(19f, 21f)
            horizontalLineTo(5f)
            close()
            moveTo(6.68f, 19f)
            horizontalLineTo(17.33f)
            lineTo(18.6f, 11f)
            horizontalLineTo(5.4f)
            lineToRelative(1.28f, 8f)
            close()
            moveTo(10f, 15f)
            horizontalLineToRelative(4f)
            quadToRelative(0.43f, 0f, 0.71f, -0.29f)
            reflectiveQuadTo(15f, 14f)
            reflectiveQuadTo(14.71f, 13.29f)
            reflectiveQuadTo(14f, 13f)
            horizontalLineTo(10f)
            quadTo(9.58f, 13f, 9.29f, 13.29f)
            reflectiveQuadTo(9f, 14f)
            reflectiveQuadToRelative(0.29f, 0.71f)
            quadTo(9.58f, 15f, 10f, 15f)
            close()
            moveTo(6f, 8f)
            quadTo(5.58f, 8f, 5.29f, 7.71f)
            quadTo(5f, 7.43f, 5f, 7f)
            reflectiveQuadTo(5.29f, 6.29f)
            reflectiveQuadTo(6f, 6f)
            horizontalLineTo(18f)
            quadToRelative(0.43f, 0f, 0.71f, 0.29f)
            reflectiveQuadTo(19f, 7f)
            reflectiveQuadTo(18.71f, 7.71f)
            reflectiveQuadTo(18f, 8f)
            horizontalLineTo(6f)
            close()
            moveTo(8f, 5f)
            quadTo(7.58f, 5f, 7.29f, 4.71f)
            reflectiveQuadTo(7f, 4f)
            quadTo(7f, 3.57f, 7.29f, 3.29f)
            reflectiveQuadTo(8f, 3f)
            horizontalLineToRelative(8f)
            quadToRelative(0.43f, 0f, 0.71f, 0.29f)
            reflectiveQuadTo(17f, 4f)
            quadToRelative(0f, 0.42f, -0.29f, 0.71f)
            reflectiveQuadTo(16f, 5f)
            horizontalLineTo(8f)
            close()
            moveTo(6.68f, 19f)
            horizontalLineTo(17.33f)
            horizontalLineTo(6.68f)
            close()
          }
        }
        .build()
    return _all_tasks!!
  }

private var _all_tasks: ImageVector? = null
