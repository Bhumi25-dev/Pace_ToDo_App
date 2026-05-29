package com.example.paceto_doapp.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Suppress("CheckReturnValue")
val health: ImageVector
  get() {
    if (_health_and_safety != null) {
      return _health_and_safety!!
    }
    _health_and_safety =
      ImageVector.Builder(
          name = "health_and_safety",
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
            moveTo(10.5f, 15.5f)
            horizontalLineToRelative(3f)
            verticalLineTo(13f)
            horizontalLineTo(16f)
            verticalLineTo(10f)
            horizontalLineTo(13.5f)
            verticalLineTo(7.5f)
            horizontalLineToRelative(-3f)
            verticalLineTo(10f)
            horizontalLineTo(8f)
            verticalLineToRelative(3f)
            horizontalLineToRelative(2.5f)
            verticalLineToRelative(2.5f)
            close()
            moveTo(12f, 22f)
            quadTo(8.53f, 21.13f, 6.26f, 18.01f)
            reflectiveQuadTo(4f, 11.1f)
            verticalLineTo(5f)
            lineTo(12f, 2f)
            lineToRelative(8f, 3f)
            verticalLineToRelative(6.1f)
            quadToRelative(0f, 3.8f, -2.26f, 6.91f)
            reflectiveQuadTo(12f, 22f)
            close()
            moveToRelative(0f, -2.1f)
            quadToRelative(2.6f, -0.82f, 4.3f, -3.3f)
            reflectiveQuadTo(18f, 11.1f)
            verticalLineTo(6.38f)
            lineTo(12f, 4.13f)
            lineTo(6f, 6.38f)
            verticalLineTo(11.1f)
            quadToRelative(0f, 3.03f, 1.7f, 5.5f)
            reflectiveQuadTo(12f, 19.9f)
            close()
            moveTo(12f, 12f)
            close()
          }
        }
        .build()
    return _health_and_safety!!
  }

private var _health_and_safety: ImageVector? = null
